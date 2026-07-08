package dev.malkist.vezorafeedback.managers;

import dev.malkist.vezorafeedback.enums.ChatInputType;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChatInputManager {

    private final Map<UUID, ChatInputType> inputTypes = new HashMap<>();
    private final Map<UUID, String> selectedStaff = new HashMap<>();

    private final Map<UUID, Integer> selectedRating = new HashMap<>();

    public void setInput(Player player, ChatInputType type) {
        inputTypes.put(player.getUniqueId(), type);
    }

    public boolean isWaiting(Player player) {
        return inputTypes.containsKey(player.getUniqueId());
    }

    public ChatInputType getType(Player player) {
        return inputTypes.get(player.getUniqueId());
    }

    public void remove(Player player) {
        inputTypes.remove(player.getUniqueId());
    }
    public void setSelectedStaff(Player player, String staff) {
        selectedStaff.put(player.getUniqueId(), staff);
    }

    public String getSelectedStaff(Player player) {
        return selectedStaff.get(player.getUniqueId());
    }

    public void setSelectedRating(Player player, int rating) {
        selectedRating.put(player.getUniqueId(), rating);
    }

    public int getSelectedRating(Player player) {
        return selectedRating.getOrDefault(player.getUniqueId(), 0);
    }

    public void clearReview(Player player) {
        selectedStaff.remove(player.getUniqueId());
        selectedRating.remove(player.getUniqueId());
        inputTypes.remove(player.getUniqueId());
    }

}