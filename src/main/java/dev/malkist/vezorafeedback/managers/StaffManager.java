package dev.malkist.vezorafeedback.managers;

import dev.malkist.vezorafeedback.VezoraFeedback;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class StaffManager {

    private final VezoraFeedback plugin;

    public StaffManager(VezoraFeedback plugin) {
        this.plugin = plugin;
    }

    public List<String> getStaffNames() {

        List<String> list = new ArrayList<>();

        ConfigurationSection section =
                plugin.getFileManager().getStaffConfig().getConfigurationSection("staff");

        if (section == null)
            return list;

        list.addAll(section.getKeys(false));

        return list;

    }

    public String getDisplayName(String player) {

        return plugin.getFileManager().getStaffConfig().getString(
                "staff." + player + ".display-name",
                player
        );

    }

    public boolean isStaff(String player) {

        return plugin.getFileManager().getStaffConfig().contains(
                "staff." + player
        );

    }

}