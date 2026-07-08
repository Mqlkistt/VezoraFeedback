package dev.malkist.vezorafeedback.managers;

import dev.malkist.vezorafeedback.VezoraFeedback;
import dev.malkist.vezorafeedback.enums.SuggestionStatus;
import dev.malkist.vezorafeedback.models.Suggestion;
import org.bukkit.configuration.ConfigurationSection;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class SuggestionManager {

    private final VezoraFeedback plugin;

    public SuggestionManager(VezoraFeedback plugin) {
        this.plugin = plugin;
    }

    public int getNextId() {

        int id = plugin.getFileManager().getSuggestionsConfig().getInt("last-id", 0);

        id++;

        plugin.getFileManager().getSuggestionsConfig().set("last-id", id);
        plugin.getFileManager().saveSuggestions();

        return id;

    }

    public Suggestion createSuggestion(UUID uuid,
                                       String player,
                                       String message) {

        int id = getNextId();

        String date = LocalDate.now().toString();
        String time = LocalTime.now().withNano(0).toString();

        Suggestion suggestion = new Suggestion(
                id,
                uuid,
                player,
                message,
                date,
                SuggestionStatus.PENDING
        );

        saveSuggestion(suggestion, time);

        return suggestion;

    }

    private void saveSuggestion(Suggestion suggestion, String time) {

        String path = "suggestions." + suggestion.getId();

        plugin.getFileManager().getSuggestionsConfig().set(path + ".player", suggestion.getPlayer());
        plugin.getFileManager().getSuggestionsConfig().set(path + ".uuid", suggestion.getUuid().toString());
        plugin.getFileManager().getSuggestionsConfig().set(path + ".message", suggestion.getMessage());
        plugin.getFileManager().getSuggestionsConfig().set(path + ".status", suggestion.getStatus().name());
        plugin.getFileManager().getSuggestionsConfig().set(path + ".date", suggestion.getDate());
        plugin.getFileManager().getSuggestionsConfig().set(path + ".time", time);

        plugin.getFileManager().saveSuggestions();

    }

    public Suggestion getSuggestion(int id) {

        String path = "suggestions." + id;

        if (!plugin.getFileManager().getSuggestionsConfig().contains(path))
            return null;

        return new Suggestion(

                id,

                UUID.fromString(
                        plugin.getFileManager().getSuggestionsConfig().getString(path + ".uuid")
                ),

                plugin.getFileManager().getSuggestionsConfig().getString(path + ".player"),

                plugin.getFileManager().getSuggestionsConfig().getString(path + ".message"),

                plugin.getFileManager().getSuggestionsConfig().getString(path + ".date"),

                SuggestionStatus.valueOf(
                        plugin.getFileManager().getSuggestionsConfig().getString(path + ".status")
                )

        );

    }

    public List<Suggestion> getSuggestions() {

        List<Suggestion> list = new ArrayList<>();

        ConfigurationSection section =
                plugin.getFileManager().getSuggestionsConfig().getConfigurationSection("suggestions");

        if (section == null)
            return list;

        for (String id : section.getKeys(false)) {

            Suggestion suggestion = getSuggestion(Integer.parseInt(id));

            if (suggestion != null)
                list.add(suggestion);

        }

        list.sort(Comparator.comparingInt(Suggestion::getId).reversed());

        return list;

    }
    public void setStatus(int id, SuggestionStatus status){

        String path = "suggestions." + id;

        if(!plugin.getFileManager().getSuggestionsConfig().contains(path))
            return;

        plugin.getFileManager().getSuggestionsConfig().set(
                path + ".status",
                status.name()
        );

        plugin.getFileManager().saveSuggestions();

    }

    public void deleteSuggestion(int id){

        plugin.getFileManager().getSuggestionsConfig().set(
                "suggestions." + id,
                null
        );

        plugin.getFileManager().saveSuggestions();

    }

}