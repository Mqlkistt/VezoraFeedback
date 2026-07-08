package dev.malkist.vezorafeedback.gui;

import dev.malkist.vezorafeedback.VezoraFeedback;
import dev.malkist.vezorafeedback.models.BugReport;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AdminBugReportGUI extends BaseGUI {

    private final VezoraFeedback plugin;

    private final Map<Integer,Integer> reportSlots = new HashMap<>();

    public AdminBugReportGUI(VezoraFeedback plugin){

        this.plugin = plugin;

        inventory = Bukkit.createInventory(this,54,"§8Admin Bug Reports");

        load();

    }

    private void load(){

        ItemStack border = createItem(Material.GRAY_STAINED_GLASS_PANE," ");

        for(int i=0;i<54;i++)
            inventory.setItem(i,border);

        int[] slots={
                10,11,12,13,14,15,16,
                19,20,21,22,23,24,25,
                28,29,30,31,32,33,34
        };

        int index=0;

        for(BugReport report : plugin.getBugReportManager().getBugReports()){

            if(index>=slots.length)
                break;

            int slot=slots[index];

            reportSlots.put(slot,report.getId());

            inventory.setItem(slot,createBug(report));

            index++;

        }

        inventory.setItem(49,
                createItem(
                        Material.ARROW,
                        "§c§lBack"
                ));

    }

    private ItemStack createBug(BugReport report){

        ItemStack item=new ItemStack(Material.REDSTONE);

        ItemMeta meta=item.getItemMeta();

        meta.setDisplayName("§cBug #"+report.getId());

        meta.setLore(Arrays.asList(

                "",

                "§7Player:",
                " §f"+report.getPlayer(),

                "",

                "§7Tanggal:",
                " §f"+report.getDate(),

                "",

                "§eKlik"

        ));

        item.setItemMeta(meta);

        return item;

    }

    private ItemStack createItem(Material material,String name,String... lore){

        ItemStack item=new ItemStack(material);

        ItemMeta meta=item.getItemMeta();

        meta.setDisplayName(name);

        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;

    }

    public Integer getReportId(int slot){

        return reportSlots.get(slot);

    }

    public void open(Player player){

        player.openInventory(inventory);

    }

}