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

public class AdminGUI extends BaseGUI {

    private final VezoraFeedback plugin;

    public AdminGUI(VezoraFeedback plugin) {

        this.plugin = plugin;

        inventory = Bukkit.createInventory(this, 45, "§8Feedback Admin");

        load();

    }

    private void load() {

        ItemStack border = createItem(Material.GRAY_STAINED_GLASS_PANE, " ");

        for(int i=0;i<45;i++)
            inventory.setItem(i,border);

        inventory.setItem(20, createItem(
                Material.WRITABLE_BOOK,
                "§6§lSuggestions",
                "",
                "§7Total:",
                " §f" + plugin.getSuggestionManager().getSuggestions().size(),
                "",
                "§e▶ Klik"
        ));

        inventory.setItem(22, createItem(
                Material.REDSTONE,
                "§c§lBug Reports",
                "",
                "§7Total:",
                " §f" + plugin.getBugReportManager().getBugReports().size(),
                "",
                "§e▶ Klik"
        ));

        inventory.setItem(24, createItem(
                Material.PLAYER_HEAD,
                "§b§lStaff Reviews",
                "",
                "§7Total:",
                " §f" + plugin.getStaffReviewManager().getReviews().size(),
                "",
                "§e▶ Klik"
        ));

        inventory.setItem(40, createItem(
                Material.BARRIER,
                "§c§lClose"
        ));

    }

    private ItemStack createItem(Material material,String name,String... lore){

        ItemStack item = new ItemStack(material);

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);

        meta.setLore(Arrays.asList(lore));

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        return item;

    }

    public void open(Player player){

        player.openInventory(inventory);

    }

}