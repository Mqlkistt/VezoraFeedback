package dev.malkist.vezorafeedback.gui;

import dev.malkist.vezorafeedback.VezoraFeedback;
import dev.malkist.vezorafeedback.models.Suggestion;
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

public class SuggestionDetailGUI extends BaseGUI {

    private final VezoraFeedback plugin;

    private final Suggestion suggestion;

    public SuggestionDetailGUI(VezoraFeedback plugin, Suggestion suggestion) {

        this.plugin = plugin;
        this.suggestion = suggestion;

        inventory = Bukkit.createInventory(this, 45, "§8Suggestion #" + suggestion.getId());

        load();

    }

    private void load() {

        ItemStack border = createItem(Material.GRAY_STAINED_GLASS_PANE, " ");

        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, border);
        }

        inventory.setItem(13, createInfo());

        inventory.setItem(31, createItem(
                Material.ARROW,
                "§c§lBack",
                "§7Kembali ke Community Suggestions."
        ));

    }

    private ItemStack createInfo() {

        ItemStack item = new ItemStack(Material.WRITABLE_BOOK);

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§6Suggestion #" + suggestion.getId());

        List<String> lore = new ArrayList<>();

        lore.add("");

        lore.add("§ePlayer");
        lore.add(" §f" + suggestion.getPlayer());

        lore.add("");

        lore.add("§eDate");
        lore.add(" §f" + suggestion.getDate());

        lore.add("");

        lore.add("§eStatus");
        lore.add(" §f" + suggestion.getStatus().name());

        lore.add("");

        lore.add("§eMessage");

        for (String line : wrap(suggestion.getMessage(), 35)) {
            lore.add(" §7" + line);
        }

        meta.setLore(lore);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        return item;

    }

    private List<String> wrap(String text, int length) {

        List<String> lines = new ArrayList<>();

        while (text.length() > length) {

            lines.add(text.substring(0, length));

            text = text.substring(length);

        }

        lines.add(text);

        return lines;

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