package dev.malkist.vezorafeedback.gui;

import dev.malkist.vezorafeedback.VezoraFeedback;
import dev.malkist.vezorafeedback.models.BugReport;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminBugDetailGUI extends BaseGUI {

    private final VezoraFeedback plugin;
    private final BugReport report;

    public AdminBugDetailGUI(VezoraFeedback plugin, BugReport report) {

        this.plugin = plugin;
        this.report = report;

        inventory = Bukkit.createInventory(this,45,"§8Bug #" + report.getId());

        load();

    }

    private void load(){

        ItemStack border = createItem(Material.GRAY_STAINED_GLASS_PANE," ");

        for(int i=0;i<45;i++)
            inventory.setItem(i,border);

        inventory.setItem(13,createInfo());

        inventory.setItem(40,createItem(
                Material.BARRIER,
                "§4§lDelete"
        ));

        inventory.setItem(36,createItem(
                Material.ARROW,
                "§c§lBack"
        ));

    }

    private ItemStack createInfo(){

        ItemStack item = new ItemStack(Material.FERMENTED_SPIDER_EYE);

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§cBug #" + report.getId());

        List<String> lore = new ArrayList<>();

        lore.add("");

        lore.add("§ePlayer");
        lore.add(" §f" + report.getPlayer());

        lore.add("");

        lore.add("§eTanggal");
        lore.add(" §f" + report.getDate());

        lore.add("");

        lore.add("§eMessage");

        for(String line : wrap(report.getMessage(),35)){
            lore.add(" §7" + line);
        }

        meta.setLore(lore);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        return item;

    }

    private List<String> wrap(String text,int length){

        List<String> lines = new ArrayList<>();

        while(text.length()>length){

            lines.add(text.substring(0,length));

            text=text.substring(length);

        }

        lines.add(text);

        return lines;

    }

    private ItemStack createItem(Material material,String name,String... lore){

        ItemStack item=new ItemStack(material);

        ItemMeta meta=item.getItemMeta();

        meta.setDisplayName(name);

        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;

    }

    public BugReport getReport(){

        return report;

    }

    public void open(Player player){

        player.openInventory(inventory);

    }

}