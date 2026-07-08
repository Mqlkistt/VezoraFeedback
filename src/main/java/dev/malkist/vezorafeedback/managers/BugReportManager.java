package dev.malkist.vezorafeedback.managers;

import dev.malkist.vezorafeedback.VezoraFeedback;
import dev.malkist.vezorafeedback.models.BugReport;
import org.bukkit.configuration.ConfigurationSection;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class BugReportManager {

    private final VezoraFeedback plugin;

    public BugReportManager(VezoraFeedback plugin) {
        this.plugin = plugin;
    }

    public int getNextId() {

        int id = plugin.getFileManager().getBugReportsConfig().getInt("last-id",0);

        id++;

        plugin.getFileManager().getBugReportsConfig().set("last-id",id);

        plugin.getFileManager().saveBugReports();

        return id;

    }

    public void createBugReport(UUID uuid,String player,String message){

        int id = getNextId();

        String path = "reports."+id;

        plugin.getFileManager().getBugReportsConfig().set(path+".player",player);
        plugin.getFileManager().getBugReportsConfig().set(path+".uuid",uuid.toString());
        plugin.getFileManager().getBugReportsConfig().set(path+".message",message);
        plugin.getFileManager().getBugReportsConfig().set(path+".date", LocalDate.now().toString());

        plugin.getFileManager().saveBugReports();

    }

    public List<BugReport> getBugReports(){

        List<BugReport> list = new ArrayList<>();

        ConfigurationSection section =
                plugin.getFileManager().getBugReportsConfig().getConfigurationSection("reports");

        if(section==null)
            return list;

        for(String id : section.getKeys(false)){

            String path = "reports."+id;

            list.add(new BugReport(

                    Integer.parseInt(id),

                    UUID.fromString(
                            plugin.getFileManager().getBugReportsConfig().getString(path+".uuid")
                    ),

                    plugin.getFileManager().getBugReportsConfig().getString(path+".player"),

                    plugin.getFileManager().getBugReportsConfig().getString(path+".message"),

                    plugin.getFileManager().getBugReportsConfig().getString(path+".date")

            ));

        }

        list.sort(Comparator.comparingInt(BugReport::getId).reversed());

        return list;

    }
    public BugReport getBugReport(int id){

        String path = "reports." + id;

        if(!plugin.getFileManager().getBugReportsConfig().contains(path))
            return null;

        return new BugReport(

                id,

                UUID.fromString(
                        plugin.getFileManager().getBugReportsConfig().getString(path + ".uuid")
                ),

                plugin.getFileManager().getBugReportsConfig().getString(path + ".player"),

                plugin.getFileManager().getBugReportsConfig().getString(path + ".message"),

                plugin.getFileManager().getBugReportsConfig().getString(path + ".date")

        );

    }

    public void deleteBugReport(int id){

        plugin.getFileManager().getBugReportsConfig().set(
                "reports." + id,
                null
        );

        plugin.getFileManager().saveBugReports();

    }

}