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

public class MainGUI extends BaseGUI {

    private final VezoraFeedback plugin;

    public MainGUI(VezoraFeedback plugin) {
        this.plugin = plugin;

        inventory = Bukkit.createInventory(this, 45, "§8✉ Vezora Feedback");

        setup();
    }

    private void setup() {

        ItemStack border = createItem(Material.GRAY_STAINED_GLASS_PANE, " ");

        // Fill border
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, border);
        }

        // Clear bagian tengah agar terlihat lebih bersih
        int[] empty = {
                10,11,12,13,14,15,16,
                19,20,21,22,23,24,25,
                28,29,30,31,32,33,34
        };

        for (int slot : empty) {
            inventory.setItem(slot, null);
        }

        // =========================
        // MENU UTAMA
        // =========================

        inventory.setItem(11, createItem(
                Material.WRITABLE_BOOK,
                "§6§lSuggestion",
                "",
                "§7Punya ide untuk server?",
                "§7Kirimkan saranmu di sini.",
                "",
                "§e▶ Klik untuk membuka"
        ));

        inventory.setItem(13, createItem(
                Material.REDSTONE,
                "§c§lBug Report",
                "",
                "§7Laporkan bug atau exploit",
                "§7yang kamu temukan.",
                "",
                "§e▶ Klik untuk membuka"
        ));

        inventory.setItem(15, createItem(
                Material.PLAYER_HEAD,
                "§b§lStaff Review",
                "",
                "§7Berikan penilaian",
                "§7kepada staff.",
                "",
                "§e▶ Klik untuk membuka"
        ));

        // =========================
        // MENU BAWAH
        // =========================

        inventory.setItem(29, createItem(
                Material.BOOK,
                "§a§lCommunity Suggestions",
                "",
                "§7Lihat semua suggestion",
                "§7yang telah dikirim player.",
                "",
                "§e▶ Klik untuk membuka"
        ));

        inventory.setItem(33, createItem(
                Material.NETHER_STAR,
                "§e§lStaff Ratings",
                "",
                "§7Lihat rating",
                "§7seluruh staff.",
                "",
                "§e▶ Klik untuk membuka"
        ));

        // =========================
        // CLOSE
        // =========================

        inventory.setItem(40, createItem(
                Material.BARRIER,
                "§c§lClose",
                "",
                "§7Tutup menu feedback."
        ));
    }

    private ItemStack createItem(Material material, String name, String... lore) {

        ItemStack item = new ItemStack(material);

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);

        meta.setLore(Arrays.asList(lore));

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        return item;
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }
}