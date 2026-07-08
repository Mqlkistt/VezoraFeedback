package dev.malkist.vezorafeedback.listeners;

import dev.malkist.vezorafeedback.VezoraFeedback;
import dev.malkist.vezorafeedback.enums.ChatInputType;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private final VezoraFeedback plugin;

    public ChatListener(VezoraFeedback plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        if (!plugin.getChatInputManager().isWaiting(event.getPlayer()))
            return;

        event.setCancelled(true);

        if (event.getMessage().equalsIgnoreCase("cancel")) {

            plugin.getChatInputManager().remove(event.getPlayer());

            event.getPlayer().sendMessage("§cInput dibatalkan.");

            return;

        }

        ChatInputType type = plugin.getChatInputManager().getType(event.getPlayer());

        switch (type) {

            case SUGGESTION -> {

                plugin.getSuggestionManager().createSuggestion(

                        event.getPlayer().getUniqueId(),
                        event.getPlayer().getName(),
                        event.getMessage()

                );

                event.getPlayer().playSound(event.getPlayer().getLocation(),
                        Sound.ENTITY_PLAYER_LEVELUP,
                        1F,
                        1F);

                event.getPlayer().sendMessage("§a✔ Suggestion berhasil dikirim.");

            }

            case BUG_REPORT -> {

                plugin.getBugReportManager().createBugReport(

                        event.getPlayer().getUniqueId(),

                        event.getPlayer().getName(),

                        event.getMessage()

                );

                event.getPlayer().playSound(

                        event.getPlayer().getLocation(),

                        Sound.ENTITY_PLAYER_LEVELUP,

                        1F,

                        1F

                );

                event.getPlayer().sendMessage("§a✔ Bug Report berhasil dikirim.");

            }
            case STAFF_REVIEW -> {

                plugin.getStaffReviewManager().createReview(

                        event.getPlayer().getUniqueId(),

                        event.getPlayer().getName(),

                        plugin.getChatInputManager().getSelectedStaff(event.getPlayer()),

                        plugin.getChatInputManager().getSelectedRating(event.getPlayer()),

                        event.getMessage()

                );

                plugin.getChatInputManager().clearReview(event.getPlayer());

                event.getPlayer().playSound(
                        event.getPlayer().getLocation(),
                        Sound.ENTITY_PLAYER_LEVELUP,
                        1F,
                        1F
                );

                event.getPlayer().sendMessage("§a✔ Staff Review berhasil dikirim.");

            }

        }

        plugin.getChatInputManager().remove(event.getPlayer());

    }

}