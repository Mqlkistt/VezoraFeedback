package dev.malkist.vezorafeedback.listeners;

import dev.malkist.vezorafeedback.VezoraFeedback;
import dev.malkist.vezorafeedback.enums.ChatInputType;
import dev.malkist.vezorafeedback.gui.CommunitySuggestionGUI;
import dev.malkist.vezorafeedback.gui.MainGUI;
import dev.malkist.vezorafeedback.gui.SuggestionDetailGUI;
import dev.malkist.vezorafeedback.models.Suggestion;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import dev.malkist.vezorafeedback.gui.StaffReviewGUI;
import dev.malkist.vezorafeedback.gui.RatingGUI;
import dev.malkist.vezorafeedback.gui.StaffRatingsGUI;
import dev.malkist.vezorafeedback.gui.AdminGUI;
import dev.malkist.vezorafeedback.gui.AdminSuggestionGUI;
import dev.malkist.vezorafeedback.gui.AdminSuggestionDetailGUI;
import dev.malkist.vezorafeedback.enums.SuggestionStatus;
import dev.malkist.vezorafeedback.gui.AdminBugReportGUI;
import dev.malkist.vezorafeedback.gui.AdminBugDetailGUI;
import dev.malkist.vezorafeedback.models.BugReport;
import dev.malkist.vezorafeedback.gui.AdminStaffReviewGUI;
import dev.malkist.vezorafeedback.gui.AdminStaffReviewDetailGUI;
import dev.malkist.vezorafeedback.models.StaffReview;

public class InventoryListener implements Listener {

    private final VezoraFeedback plugin;

    public InventoryListener(VezoraFeedback plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (!(event.getWhoClicked() instanceof Player player))
            return;

        if (event.getCurrentItem() == null)
            return;

        if (!event.getCurrentItem().hasItemMeta())
            return;

        if (event.getInventory().getHolder() instanceof MainGUI gui) {

            event.setCancelled(true);

            handleMainGUI(player, event);

            return;
        }
        if(event.getInventory().getHolder() instanceof RatingGUI gui){

            event.setCancelled(true);

            handleRatingGUI(player,gui,event);

            return;

        }

        if (event.getInventory().getHolder() instanceof CommunitySuggestionGUI gui) {

            event.setCancelled(true);

            handleCommunityGUI(player, gui, event);

            return;
        }

        if (event.getInventory().getHolder() instanceof SuggestionDetailGUI gui) {

            event.setCancelled(true);

            handleSuggestionDetail(player, event);

        }
        if(event.getInventory().getHolder() instanceof StaffReviewGUI gui){

            event.setCancelled(true);

            handleStaffReviewGUI(player,event);

            return;

        }
        if(event.getInventory().getHolder() instanceof StaffRatingsGUI gui){

            event.setCancelled(true);

            handleStaffRatingsGUI(player,event);

            return;

        }
        if(event.getInventory().getHolder() instanceof AdminGUI){

            event.setCancelled(true);

            handleAdminGUI(player,event);

            return;

        }

        if(event.getInventory().getHolder() instanceof AdminSuggestionGUI gui){

            event.setCancelled(true);

            handleAdminSuggestionGUI(player,gui,event);

            return;

        }
        if(event.getInventory().getHolder() instanceof AdminSuggestionDetailGUI gui){

            event.setCancelled(true);

            handleAdminSuggestionDetail(player,gui,event);

            return;

        }
        if (event.getInventory().getHolder() instanceof AdminBugReportGUI gui) {

            event.setCancelled(true);

            handleAdminBugReportGUI(player, gui, event);

            return;

        }

        if (event.getInventory().getHolder() instanceof AdminBugDetailGUI gui) {

            event.setCancelled(true);

            handleAdminBugDetailGUI(player, gui, event);

            return;

        }

        if (event.getInventory().getHolder() instanceof AdminStaffReviewGUI gui) {

            event.setCancelled(true);

            handleAdminStaffReviewGUI(player, gui, event);

            return;

        }

        if (event.getInventory().getHolder() instanceof AdminStaffReviewDetailGUI gui) {

            event.setCancelled(true);

            handleAdminStaffReviewDetailGUI(player, gui, event);

            return;

        }

    }

    private void handleMainGUI(Player player, InventoryClickEvent event) {

        String name = ChatColor.stripColor(
                event.getCurrentItem().getItemMeta().getDisplayName()
        );

        switch (name) {

            case "Suggestion" -> {

                player.closeInventory();

                plugin.getChatInputManager().setInput(player, ChatInputType.SUGGESTION);

                player.playSound(player.getLocation(),
                        Sound.UI_BUTTON_CLICK,
                        1F,
                        1F);

                player.sendMessage("");
                player.sendMessage("§6§lSuggestion");
                player.sendMessage("§7Silakan ketik suggestion kamu.");
                player.sendMessage("§7Ketik §ccancel §7untuk membatalkan.");
                player.sendMessage("");

            }

            case "Bug Report" -> {

                player.closeInventory();

                plugin.getChatInputManager().setInput(
                        player,
                        ChatInputType.BUG_REPORT
                );

                player.playSound(
                        player.getLocation(),
                        Sound.UI_BUTTON_CLICK,
                        1F,
                        1F
                );

                player.sendMessage("");
                player.sendMessage("§c§lBug Report");
                player.sendMessage("§7Silakan ketik bug report.");
                player.sendMessage("§7Ketik §ccancel §7untuk membatalkan.");
                player.sendMessage("");

            }

            case "Community Suggestions" -> {

                player.playSound(
                        player.getLocation(),
                        Sound.UI_BUTTON_CLICK,
                        1F,
                        1F
                );

                new CommunitySuggestionGUI(plugin).open(player);

            }

            case "Staff Review" -> {

                // Cek minimal playtime
                if (!plugin.getStaffReviewManager().hasMinimumPlaytime(player)) {

                    long played = plugin.getStaffReviewManager().getPlaytimeHours(player);

                    player.closeInventory();

                    player.sendMessage("");
                    player.sendMessage("§c§lStaff Review");
                    player.sendMessage("§7Kamu harus bermain minimal §e7 jam");
                    player.sendMessage("§7untuk memberikan review kepada staff.");
                    player.sendMessage("");
                    player.sendMessage("§7Playtime kamu:");
                    player.sendMessage(" §e" + played + " / 7 jam");
                    player.sendMessage("");

                    return;
                }

                // Cek cooldown
                if (plugin.getStaffReviewManager().hasCooldown(player.getUniqueId())) {

                    long minutes = plugin.getStaffReviewManager().getRemainingMinutes(player.getUniqueId());

                    long hours = minutes / 60;
                    long mins = minutes % 60;

                    player.closeInventory();

                    player.sendMessage("");
                    player.sendMessage("§c§lStaff Review");
                    player.sendMessage("§7Kamu masih dalam masa cooldown.");
                    player.sendMessage("§7Sisa waktu:");
                    player.sendMessage(" §e" + hours + " jam " + mins + " menit");
                    player.sendMessage("");

                    return;
                }

                player.playSound(
                        player.getLocation(),
                        Sound.UI_BUTTON_CLICK,
                        1F,
                        1F
                );

                new StaffReviewGUI(plugin).open(player);

            }

            case "Staff Ratings" -> {

                player.playSound(
                        player.getLocation(),
                        Sound.UI_BUTTON_CLICK,
                        1F,
                        1F
                );

                new StaffRatingsGUI(plugin).open(player);

            }

            case "Close" -> player.closeInventory();


        }


    }

    private void handleCommunityGUI(Player player,
                                    CommunitySuggestionGUI gui,
                                    InventoryClickEvent event) {

        if (event.getCurrentItem() == null)
            return;

        String name = ChatColor.stripColor(
                event.getCurrentItem().getItemMeta().getDisplayName()
        );

        // Back ke MainGUI
        if (name.equalsIgnoreCase("Back")) {

            player.playSound(player.getLocation(),
                    Sound.UI_BUTTON_CLICK,
                    1F,
                    1F);

            new MainGUI(plugin).open(player);

            return;

        }

        Integer id = gui.getSuggestionId(event.getSlot());

        if (id == null)
            return;

        Suggestion suggestion = plugin.getSuggestionManager().getSuggestion(id);

        if (suggestion == null)
            return;

        player.playSound(player.getLocation(),
                Sound.UI_BUTTON_CLICK,
                1F,
                1F);

        new SuggestionDetailGUI(plugin, suggestion).open(player);

    }

    private void handleSuggestionDetail(Player player,
                                        InventoryClickEvent event) {

        if (event.getCurrentItem() == null)
            return;

        String name = ChatColor.stripColor(
                event.getCurrentItem().getItemMeta().getDisplayName()
        );

        if (!name.equalsIgnoreCase("Back"))
            return;

        player.playSound(player.getLocation(),
                Sound.UI_BUTTON_CLICK,
                1F,
                1F);

        new CommunitySuggestionGUI(plugin).open(player);

    }
    private void handleStaffReviewGUI(Player player,
                                      InventoryClickEvent event){

        if(event.getCurrentItem()==null)
            return;

        String name = ChatColor.stripColor(
                event.getCurrentItem().getItemMeta().getDisplayName()
        );

        if(name.equalsIgnoreCase("Back")){

            new MainGUI(plugin).open(player);

            return;

        }

        for(String staff : plugin.getStaffManager().getStaffNames()){

            String display = ChatColor.stripColor(
                    plugin.getStaffManager().getDisplayName(staff)
            );

            if(display.equalsIgnoreCase(name)){

                plugin.getChatInputManager().setSelectedStaff(player,staff);

                player.playSound(
                        player.getLocation(),
                        Sound.UI_BUTTON_CLICK,
                        1F,
                        1F
                );

                new RatingGUI(plugin,staff).open(player);

                return;

            }

        }

    }
    private void handleRatingGUI(Player player,
                                 RatingGUI gui,
                                 InventoryClickEvent event){

        if(event.getCurrentItem()==null)
            return;

        String name = ChatColor.stripColor(
                event.getCurrentItem().getItemMeta().getDisplayName()
        );

        // Back
        if(name.equalsIgnoreCase("Back")){

            new StaffReviewGUI(plugin).open(player);

            return;

        }

        int rating = 0;

        if(name.equals("⭐"))
            rating = 1;

        else if(name.equals("⭐⭐"))
            rating = 2;

        else if(name.equals("⭐⭐⭐"))
            rating = 3;

        else if(name.equals("⭐⭐⭐⭐"))
            rating = 4;

        else if(name.equals("⭐⭐⭐⭐⭐"))
            rating = 5;

        if(rating==0)
            return;

        plugin.getChatInputManager().setSelectedRating(player,rating);

        plugin.getChatInputManager().setInput(player, ChatInputType.STAFF_REVIEW);

        player.closeInventory();

        player.playSound(player.getLocation(),
                Sound.UI_BUTTON_CLICK,
                1F,
                1F);

        player.sendMessage("");
        player.sendMessage("§e━━━━━━━━━━━━━━━━━━━━");
        player.sendMessage("§6Silakan tulis review kamu.");
        player.sendMessage("§7Ketik §ccancel §7untuk membatalkan.");
        player.sendMessage("§e━━━━━━━━━━━━━━━━━━━━");
        player.sendMessage("");

    }
    private void handleStaffRatingsGUI(Player player,
                                       InventoryClickEvent event){

        if(event.getCurrentItem()==null)
            return;

        String name = ChatColor.stripColor(
                event.getCurrentItem().getItemMeta().getDisplayName()
        );

        if(name.equalsIgnoreCase("Back")){

            new MainGUI(plugin).open(player);

        }

    }
    private void handleAdminGUI(Player player,
                                InventoryClickEvent event){

        if(event.getCurrentItem()==null)
            return;

        String name = ChatColor.stripColor(
                event.getCurrentItem().getItemMeta().getDisplayName()
        );

        switch(name) {

            case "Suggestions" -> {

                player.playSound(
                        player.getLocation(),
                        Sound.UI_BUTTON_CLICK,
                        1F,
                        1F
                );

                new AdminSuggestionGUI(plugin).open(player);

            }

            case "Staff Reviews" -> {

                player.playSound(
                        player.getLocation(),
                        Sound.UI_BUTTON_CLICK,
                        1F,
                        1F
                );

                new AdminStaffReviewGUI(plugin).open(player);

            }

            case "Bug Reports" -> {

                player.playSound(
                        player.getLocation(),
                        Sound.UI_BUTTON_CLICK,
                        1F,
                        1F
                );

                new AdminBugReportGUI(plugin).open(player);

            }

            case "Close" -> player.closeInventory();

        }


    }

    private void handleAdminSuggestionGUI(Player player,
                                          AdminSuggestionGUI gui,
                                          InventoryClickEvent event){

        if(event.getCurrentItem()==null)
            return;

        String name = ChatColor.stripColor(
                event.getCurrentItem().getItemMeta().getDisplayName()
        );

        if(name.equalsIgnoreCase("Back")){

            new AdminGUI(plugin).open(player);

            return;

        }

        Integer id = gui.getSuggestionId(event.getSlot());

        if(id==null)
            return;

        Suggestion suggestion = plugin.getSuggestionManager().getSuggestion(id);

        if(suggestion==null)
            return;

        player.playSound(
                player.getLocation(),
                Sound.UI_BUTTON_CLICK,
                1F,
                1F
        );

        new AdminSuggestionDetailGUI(plugin,suggestion).open(player);

    }
    private void handleAdminSuggestionDetail(Player player,
                                             AdminSuggestionDetailGUI gui,
                                             InventoryClickEvent event){

        if(event.getCurrentItem()==null)
            return;

        String name = ChatColor.stripColor(
                event.getCurrentItem().getItemMeta().getDisplayName()
        );

        int id = gui.getSuggestion().getId();

        switch(name){

            case "Accept" -> {

                plugin.getSuggestionManager().setStatus(
                        id,
                        SuggestionStatus.ACCEPTED
                );

                player.sendMessage("§a✔ Suggestion berhasil diaccept.");

                new AdminSuggestionDetailGUI(
                        plugin,
                        plugin.getSuggestionManager().getSuggestion(id)
                ).open(player);

            }

            case "Under Review" -> {

                plugin.getSuggestionManager().setStatus(
                        id,
                        SuggestionStatus.UNDER_REVIEW
                );

                player.sendMessage("§e✔ Status menjadi Under Review.");

                new AdminSuggestionDetailGUI(
                        plugin,
                        plugin.getSuggestionManager().getSuggestion(id)
                ).open(player);

            }

            case "Reject" -> {

                plugin.getSuggestionManager().setStatus(
                        id,
                        SuggestionStatus.REJECTED
                );

                player.sendMessage("§c✔ Suggestion berhasil direject.");

                new AdminSuggestionDetailGUI(
                        plugin,
                        plugin.getSuggestionManager().getSuggestion(id)
                ).open(player);

            }

            case "Delete" -> {

                plugin.getSuggestionManager().deleteSuggestion(id);

                player.sendMessage("§cSuggestion berhasil dihapus.");

                new AdminSuggestionGUI(plugin).open(player);

            }

            case "Back" -> {

                new AdminSuggestionGUI(plugin).open(player);

            }

        }

    }
    private void handleAdminBugReportGUI(Player player,
                                         AdminBugReportGUI gui,
                                         InventoryClickEvent event) {

        if (event.getCurrentItem() == null)
            return;

        String name = ChatColor.stripColor(
                event.getCurrentItem().getItemMeta().getDisplayName()
        );

        if (name.equalsIgnoreCase("Back")) {

            new AdminGUI(plugin).open(player);

            return;

        }

        Integer id = gui.getReportId(event.getSlot());

        if (id == null)
            return;

        BugReport report = plugin.getBugReportManager().getBugReport(id);

        if (report == null)
            return;

        player.playSound(
                player.getLocation(),
                Sound.UI_BUTTON_CLICK,
                1F,
                1F
        );

        new AdminBugDetailGUI(plugin, report).open(player);

    }
    private void handleAdminBugDetailGUI(Player player,
                                         AdminBugDetailGUI gui,
                                         InventoryClickEvent event) {

        if (event.getCurrentItem() == null)
            return;

        String name = ChatColor.stripColor(
                event.getCurrentItem().getItemMeta().getDisplayName()
        );

        int id = gui.getReport().getId();

        switch (name) {

            case "Delete" -> {

                plugin.getBugReportManager().deleteBugReport(id);

                player.sendMessage("§a✔ Bug Report berhasil dihapus.");

                new AdminBugReportGUI(plugin).open(player);

            }

            case "Back" -> {

                new AdminBugReportGUI(plugin).open(player);

            }

        }

    }
    private void handleAdminStaffReviewGUI(Player player,
                                           AdminStaffReviewGUI gui,
                                           InventoryClickEvent event){

        if(event.getCurrentItem()==null)
            return;

        String name = ChatColor.stripColor(
                event.getCurrentItem().getItemMeta().getDisplayName()
        );

        if(name.equalsIgnoreCase("Back")){

            new AdminGUI(plugin).open(player);

            return;

        }

        Integer id = gui.getReviewId(event.getSlot());

        if(id==null)
            return;

        StaffReview review = plugin.getStaffReviewManager().getReview(id);

        if(review==null)
            return;

        player.playSound(
                player.getLocation(),
                Sound.UI_BUTTON_CLICK,
                1F,
                1F
        );

        new AdminStaffReviewDetailGUI(plugin,review).open(player);

    }
    private void handleAdminStaffReviewDetailGUI(Player player,
                                                 AdminStaffReviewDetailGUI gui,
                                                 InventoryClickEvent event){

        if(event.getCurrentItem()==null)
            return;

        String name = ChatColor.stripColor(
                event.getCurrentItem().getItemMeta().getDisplayName()
        );

        int id = gui.getReview().getId();

        switch(name){

            case "Delete" -> {

                plugin.getStaffReviewManager().deleteReview(id);

                player.sendMessage("§a✔ Staff Review berhasil dihapus.");

                new AdminStaffReviewGUI(plugin).open(player);

            }

            case "Back" -> {

                new AdminStaffReviewGUI(plugin).open(player);

            }

        }

    }

}