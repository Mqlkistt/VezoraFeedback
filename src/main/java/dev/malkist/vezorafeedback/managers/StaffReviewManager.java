package dev.malkist.vezorafeedback.managers;

import dev.malkist.vezorafeedback.VezoraFeedback;
import dev.malkist.vezorafeedback.models.StaffReview;
import org.bukkit.configuration.ConfigurationSection;
import java.time.LocalDateTime;
import java.time.Duration;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.time.LocalDate;
import java.util.*;

public class StaffReviewManager {

    private final VezoraFeedback plugin;

    public StaffReviewManager(VezoraFeedback plugin) {
        this.plugin = plugin;
    }

    public int getNextId() {

        int id = plugin.getFileManager()
                .getStaffReviewsConfig()
                .getInt("last-id", 0);

        id++;

        plugin.getFileManager()
                .getStaffReviewsConfig()
                .set("last-id", id);

        plugin.getFileManager().saveStaffReviews();

        return id;

    }

    public void createReview(UUID reviewerUUID,
                             String reviewer,
                             String staff,
                             int rating,
                             String comment) {

        int id = getNextId();

        String path = "reviews." + id;

        plugin.getFileManager().getStaffReviewsConfig().set(path + ".reviewer", reviewer);
        plugin.getFileManager().getStaffReviewsConfig().set(path + ".reviewer-uuid", reviewerUUID.toString());
        plugin.getFileManager().getStaffReviewsConfig().set(path + ".staff", staff);
        plugin.getFileManager().getStaffReviewsConfig().set(path + ".rating", rating);
        plugin.getFileManager().getStaffReviewsConfig().set(path + ".comment", comment);
        plugin.getFileManager().getStaffReviewsConfig().set(path + ".date", LocalDate.now().toString());

        setCooldown(reviewerUUID);

        plugin.getFileManager().saveStaffReviews();

    }

    public List<StaffReview> getReviews() {

        List<StaffReview> reviews = new ArrayList<>();

        ConfigurationSection section =
                plugin.getFileManager()
                        .getStaffReviewsConfig()
                        .getConfigurationSection("reviews");

        if (section == null)
            return reviews;

        for (String id : section.getKeys(false)) {

            String path = "reviews." + id;

            reviews.add(new StaffReview(

                    Integer.parseInt(id),

                    UUID.fromString(
                            plugin.getFileManager().getStaffReviewsConfig().getString(path + ".reviewer-uuid")
                    ),

                    plugin.getFileManager().getStaffReviewsConfig().getString(path + ".reviewer"),

                    plugin.getFileManager().getStaffReviewsConfig().getString(path + ".staff"),

                    plugin.getFileManager().getStaffReviewsConfig().getInt(path + ".rating"),

                    plugin.getFileManager().getStaffReviewsConfig().getString(path + ".comment"),

                    plugin.getFileManager().getStaffReviewsConfig().getString(path + ".date")

            ));

        }

        reviews.sort(Comparator.comparingInt(StaffReview::getId).reversed());

        return reviews;

    }
    public List<StaffReview> getReviews(String staff){

        List<StaffReview> list = new ArrayList<>();

        for(StaffReview review : getReviews()){

            if(review.getStaff().equalsIgnoreCase(staff))
                list.add(review);

        }

        return list;

    }

    public double getAverageRating(String staff){

        List<StaffReview> list = getReviews(staff);

        if(list.isEmpty())
            return 0;

        double total = 0;

        for(StaffReview review : list)
            total += review.getRating();

        return total / list.size();

    }
    public int getReviewCount(String staff){

        return getReviews(staff).size();

    }
    public StaffReview getReview(int id){

        String path = "reviews." + id;

        if(!plugin.getFileManager().getStaffReviewsConfig().contains(path))
            return null;

        return new StaffReview(

                id,

                UUID.fromString(
                        plugin.getFileManager().getStaffReviewsConfig().getString(path + ".reviewer-uuid")
                ),

                plugin.getFileManager().getStaffReviewsConfig().getString(path + ".reviewer"),

                plugin.getFileManager().getStaffReviewsConfig().getString(path + ".staff"),

                plugin.getFileManager().getStaffReviewsConfig().getInt(path + ".rating"),

                plugin.getFileManager().getStaffReviewsConfig().getString(path + ".comment"),

                plugin.getFileManager().getStaffReviewsConfig().getString(path + ".date")

        );

    }

    public void deleteReview(int id){

        plugin.getFileManager().getStaffReviewsConfig().set(
                "reviews." + id,
                null
        );

        plugin.getFileManager().saveStaffReviews();

    }
    public boolean hasCooldown(UUID uuid){

        String path = "cooldowns." + uuid;

        String value = plugin.getFileManager()
                .getStaffReviewsConfig()
                .getString(path);

        if(value == null)
            return false;

        LocalDateTime last = LocalDateTime.parse(value);

        return last.plusHours(24).isAfter(LocalDateTime.now());

    }

    public long getRemainingMinutes(UUID uuid){

        String value = plugin.getFileManager()
                .getStaffReviewsConfig()
                .getString("cooldowns." + uuid);

        if(value == null)
            return 0;

        LocalDateTime last = LocalDateTime.parse(value);

        LocalDateTime expire = last.plusHours(24);

        return Duration.between(
                LocalDateTime.now(),
                expire
        ).toMinutes();

    }

    public void setCooldown(UUID uuid){

        plugin.getFileManager()
                .getStaffReviewsConfig()
                .set(
                        "cooldowns." + uuid,
                        LocalDateTime.now().toString()
                );

    }
    public boolean hasMinimumPlaytime(Player player){

        long ticks = player.getStatistic(Statistic.PLAY_ONE_MINUTE);

        long hours = ticks / 20 / 60 / 60;

        return hours >= 7;

    }

    public long getPlaytimeHours(Player player){

        long ticks = player.getStatistic(Statistic.PLAY_ONE_MINUTE);

        return ticks / 20 / 60 / 60;

    }

}