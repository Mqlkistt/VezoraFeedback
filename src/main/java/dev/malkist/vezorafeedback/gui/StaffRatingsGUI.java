package dev.malkist.vezorafeedback.gui;

import dev.malkist.vezorafeedback.VezoraFeedback;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class StaffRatingsGUI extends BaseGUI {

    private final VezoraFeedback plugin;

    public StaffRatingsGUI(VezoraFeedback plugin) {

        this.plugin = plugin;

        inventory = Bukkit.createInventory(this, 54, "§8Staff Ratings");

        load();

    }

    private void load() {

        ItemStack border = createItem(Material.GRAY_STAINED_GLASS_PANE, " ");

        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, border);
        }

        int[] slots = {
                10,11,12,13,14,15,16,
                19,20,21,22,23,24,25,
                28,29,30,31,32,33,34
        };

        int index = 0;

        for(String staff : plugin.getStaffManager().getStaffNames()){

            if(index >= slots.length)
                break;

            inventory.setItem(
                    slots[index],
                    createHead(staff)
            );

            index++;

        }

        inventory.setItem(
                49,
                createItem(
                        Material.ARROW,
                        "§c§lBack",
                        "§7Kembali ke menu."
                )
        );

    }

    private ItemStack createHead(String staff){

        ItemStack head = new ItemStack(Material.PLAYER_HEAD);

        SkullMeta meta = (SkullMeta) head.getItemMeta();

        OfflinePlayer player = Bukkit.getOfflinePlayer(staff);

        meta.setOwningPlayer(player);

        double avg = plugin.getStaffReviewManager().getAverageRating(staff);

        int reviews = plugin.getStaffReviewManager().getReviewCount(staff);

        meta.setDisplayName(plugin.getStaffManager().getDisplayName(staff));

        meta.setLore(Arrays.asList(
                "",
                "§eAverage Rating",
                " §f" + String.format("%.1f", avg) + " §7/ 5.0",
                "",
                "§eTotal Reviews",
                " §f" + reviews,
                "",
                "§7Klik untuk melihat review."
        ));

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        head.setItemMeta(meta);

        return head;

    }

    private ItemStack createItem(Material material,String name,String... lore){

        ItemStack item = new ItemStack(material);

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);

        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;

    }

    public void open(Player player){

        player.openInventory(inventory);

    }

}