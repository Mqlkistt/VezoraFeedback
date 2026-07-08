package dev.malkist.vezorafeedback;

import dev.malkist.vezorafeedback.commands.FeedbackCommand;
import dev.malkist.vezorafeedback.managers.*;
import org.bukkit.plugin.java.JavaPlugin;
import dev.malkist.vezorafeedback.listeners.InventoryListener;
import dev.malkist.vezorafeedback.listeners.ChatListener;

public final class VezoraFeedback extends JavaPlugin {

    private static VezoraFeedback instance;

    private FileManager fileManager;
    private ConfigManager configManager;
    private StaffManager staffManager;
    private SuggestionManager suggestionManager;
    private BugReportManager bugReportManager;
    private StaffReviewManager staffReviewManager;
    private CooldownManager cooldownManager;
    private ChatInputManager chatInputManager;

    @Override
    public void onEnable() {

        instance = this;

        // Default files
        saveDefaultConfig();
        saveResource("staff.yml", false);

        // Managers
        fileManager = new FileManager(this);
        configManager = new ConfigManager(this);
        staffManager = new StaffManager(this);
        suggestionManager = new SuggestionManager(this);
        bugReportManager = new BugReportManager(this);
        staffReviewManager = new StaffReviewManager(this);
        cooldownManager = new CooldownManager(this);
        chatInputManager = new ChatInputManager();

        // Commands
        if (getCommand("feedback") != null) {
            getCommand("feedback").setExecutor(new FeedbackCommand(this));
        }

        // Register Listeners
        getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);

        getLogger().info("==================================");
        getLogger().info(" VezoraFeedback Enabled!");
        getLogger().info(" Developed by Malkist");
        getLogger().info("==================================");
    }

    @Override
    public void onDisable() {

        if (fileManager != null) {
            fileManager.saveAll();
        }

        getLogger().info("VezoraFeedback Disabled!");
    }

    public static VezoraFeedback getInstance() {
        return instance;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public StaffManager getStaffManager() {
        return staffManager;
    }

    public SuggestionManager getSuggestionManager() {
        return suggestionManager;
    }

    public BugReportManager getBugReportManager() {
        return bugReportManager;
    }

    public StaffReviewManager getStaffReviewManager() {
        return staffReviewManager;
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }
    public ChatInputManager getChatInputManager() {
        return chatInputManager;
    }
}