package dev.malkist.vezorafeedback.gui;

import dev.malkist.vezorafeedback.VezoraFeedback;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class RatingGUI extends BaseGUI {

    private final VezoraFeedback plugin;

    private final String staff;

    public RatingGUI(VezoraFeedback plugin, String staff){

        this.plugin = plugin;
        this.staff = staff;

        inventory = Bukkit.createInventory(this,27,"§8Rate "+staff);

        load();

    }

    private void load(){

        ItemStack border = createItem(Material.GRAY_STAINED_GLASS_PANE," ");

        for(int i=0;i<inventory.getSize();i++)
            inventory.setItem(i,border);

        inventory.setItem(10,star(1));
        inventory.setItem(11,star(2));
        inventory.setItem(12,star(3));
        inventory.setItem(13,star(4));
        inventory.setItem(14,star(5));

        inventory.setItem(22,createItem(
                Material.ARROW,
                "§c§lBack"
        ));

    }

    private ItemStack star(int stars){

        StringBuilder builder = new StringBuilder();

        for(int i=0;i<stars;i++)
            builder.append("⭐");

        ItemStack item = new ItemStack(Material.NETHER_STAR);

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§e"+builder);

        meta.setLore(Arrays.asList(
                "",
                "§7Berikan rating",
                "§7"+stars+" bintang.",
                "",
                "§e▶ Klik"
        ));

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        return item;

    }

    private ItemStack createItem(Material material,String name){

        ItemStack item = new ItemStack(material);

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);

        item.setItemMeta(meta);

        return item;

    }

    public String getStaff(){

        return staff;

    }

    public void open(Player player){

        player.openInventory(inventory);

    }

}