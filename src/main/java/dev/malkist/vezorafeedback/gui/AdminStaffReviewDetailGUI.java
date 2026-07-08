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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminStaffReviewDetailGUI extends BaseGUI {

    private final VezoraFeedback plugin;
    private final StaffReview review;

    public AdminStaffReviewDetailGUI(VezoraFeedback plugin, StaffReview review){

        this.plugin = plugin;
        this.review = review;

        inventory = Bukkit.createInventory(this,45,"§8Review #" + review.getId());

        load();

    }

    private void load(){

        ItemStack border = createItem(Material.GRAY_STAINED_GLASS_PANE," ");

        for(int i=0;i<45;i++)
            inventory.setItem(i,border);

        inventory.setItem(13,createInfo());

        inventory.setItem(
                36,
                createItem(
                        Material.ARROW,
                        "§c§lBack"
                )
        );

        inventory.setItem(
                40,
                createItem(
                        Material.BARRIER,
                        "§4§lDelete"
                )
        );

    }

    private ItemStack createInfo(){

        ItemStack item = new ItemStack(Material.NETHER_STAR);

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§6Review #" + review.getId());

        List<String> lore = new ArrayList<>();

        lore.add("");

        lore.add("§eStaff");
        lore.add(" §f" + review.getStaff());

        lore.add("");

        lore.add("§eReviewer");
        lore.add(" §f" + review.getReviewer());

        lore.add("");

        lore.add("§eRating");
        lore.add(" §6" + "⭐".repeat(review.getRating()));

        lore.add("");

        lore.add("§eDate");
        lore.add(" §f" + review.getDate());

        lore.add("");

        lore.add("§eComment");

        for(String line : wrap(review.getComment(),35)){
            lore.add(" §7" + line);
        }

        meta.setLore(lore);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        return item;

    }

    private List<String> wrap(String text,int length){

        List<String> list = new ArrayList<>();

        while(text.length()>length){

            list.add(text.substring(0,length));

            text=text.substring(length);

        }

        list.add(text);

        return list;

    }

    private ItemStack createItem(Material material,String name,String... lore){

        ItemStack item = new ItemStack(material);

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);

        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;

    }

    public StaffReview getReview(){

        return review;

    }

    public void open(Player player){

        player.openInventory(inventory);

    }

}