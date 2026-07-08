package dev.malkist.vezorafeedback.managers;

import dev.malkist.vezorafeedback.VezoraFeedback;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {

    private final VezoraFeedback plugin;

    private File staffFile;
    private File suggestionsFile;
    private File bugReportsFile;
    private File staffReviewsFile;

    private FileConfiguration staffConfig;
    private FileConfiguration suggestionsConfig;
    private FileConfiguration bugReportsConfig;
    private FileConfiguration staffReviewsConfig;

    public FileManager(VezoraFeedback plugin) {
        this.plugin = plugin;

        createFiles();
        loadFiles();
    }

    private void createFiles() {

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        createResource("staff.yml");

        staffFile = new File(plugin.getDataFolder(), "staff.yml");
        suggestionsFile = new File(plugin.getDataFolder(), "suggestions.yml");
        bugReportsFile = new File(plugin.getDataFolder(), "bugreports.yml");
        staffReviewsFile = new File(plugin.getDataFolder(), "staffreviews.yml");

        try {

            if (!suggestionsFile.exists())
                suggestionsFile.createNewFile();

            if (!bugReportsFile.exists())
                bugReportsFile.createNewFile();

            if (!staffReviewsFile.exists())
                staffReviewsFile.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void createResource(String fileName) {

        File file = new File(plugin.getDataFolder(), fileName);

        if (!file.exists()) {
            plugin.saveResource(fileName, false);
        }

    }

    public void loadFiles() {

        staffConfig = YamlConfiguration.loadConfiguration(staffFile);
        suggestionsConfig = YamlConfiguration.loadConfiguration(suggestionsFile);
        bugReportsConfig = YamlConfiguration.loadConfiguration(bugReportsFile);
        staffReviewsConfig = YamlConfiguration.loadConfiguration(staffReviewsFile);

    }

    public void reloadFiles() {
        loadFiles();
    }

    public void saveAll() {

        saveStaff();
        saveSuggestions();
        saveBugReports();
        saveStaffReviews();

    }

    public void saveStaff() {
        save(staffConfig, staffFile);
    }

    public void saveSuggestions() {
        save(suggestionsConfig, suggestionsFile);
    }

    public void saveBugReports() {
        save(bugReportsConfig, bugReportsFile);
    }

    public void saveStaffReviews() {
        save(staffReviewsConfig, staffReviewsFile);
    }

    private void save(FileConfiguration config, File file) {

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public FileConfiguration getStaffConfig() {
        return staffConfig;
    }

    public FileConfiguration getSuggestionsConfig() {
        return suggestionsConfig;
    }

    public FileConfiguration getBugReportsConfig() {
        return bugReportsConfig;
    }

    public FileConfiguration getStaffReviewsConfig() {
        return staffReviewsConfig;
    }

}