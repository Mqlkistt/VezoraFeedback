package dev.malkist.vezorafeedback.gui;

import dev.malkist.vezorafeedback.VezoraFeedback;
import dev.malkist.vezorafeedback.models.StaffReview;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AdminStaffReviewGUI extends BaseGUI {

    private final VezoraFeedback plugin;

    private final Map<Integer,Integer> reviewSlots = new HashMap<>();

    public AdminStaffReviewGUI(VezoraFeedback plugin){

        this.plugin = plugin;

        inventory = Bukkit.createInventory(this,54,"§8Admin Staff Reviews");

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

        for(StaffReview review : plugin.getStaffReviewManager().getReviews()){

            if(index>=slots.length)
                break;

            int slot = slots[index];

            reviewSlots.put(slot,review.getId());

            inventory.setItem(slot,createReview(review));

            index++;

        }

        inventory.setItem(49,
                createItem(
                        Material.ARROW,
                        "§c§lBack"
                ));

    }

    private ItemStack createReview(StaffReview review){

        ItemStack item = new ItemStack(Material.NETHER_STAR);

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§6Review #" + review.getId());

        meta.setLore(Arrays.asList(

                "",

                "§7Staff:",
                " §f" + review.getStaff(),

                "",

                "§7Reviewer:",
                " §f" + review.getReviewer(),

                "",

                "§7Rating:",
                " §e" + "⭐".repeat(review.getRating()),

                "",

                "§e▶ Klik"

        ));

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        return item;

    }

    private ItemStack createItem(Material material,String name,String... lore){

        ItemStack item = new ItemStack(material);

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);

        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;

    }

    public Integer getReviewId(int slot){

        return reviewSlots.get(slot);

    }

    public void open(Player player){

        player.openInventory(inventory);

    }

}