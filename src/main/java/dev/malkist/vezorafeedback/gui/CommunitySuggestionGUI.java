package dev.malkist.vezorafeedback.gui;

import dev.malkist.vezorafeedback.VezoraFeedback;
import dev.malkist.vezorafeedback.models.Suggestion;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.entity.Player;

import java.util.*;

public class CommunitySuggestionGUI extends BaseGUI {

    private final VezoraFeedback plugin;

    private final Map<Integer, Integer> suggestionSlots = new HashMap<>();

    public CommunitySuggestionGUI(VezoraFeedback plugin) {

        this.plugin = plugin;

        inventory = Bukkit.createInventory(this, 54, "§8Community Suggestions");

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

        List<Suggestion> suggestions = plugin.getSuggestionManager().getSuggestions();

        int index = 0;

        for (Suggestion suggestion : suggestions) {

            if (index >= slots.length)
                break;

            int slot = slots[index];

            suggestionSlots.put(slot, suggestion.getId());

            inventory.setItem(slot, createSuggestionItem(suggestion));

            index++;

        }

        inventory.setItem(49, createItem(

                Material.ARROW,

                "§c§lBack",

                "§7Kembali ke menu utama."

        ));

    }

    private ItemStack createSuggestionItem(Suggestion suggestion) {

        ItemStack item = new ItemStack(Material.WRITABLE_BOOK);

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§6Suggestion #" + suggestion.getId());

        List<String> lore = new ArrayList<>();

        lore.add("");
        lore.add("§7Player:");
        lore.add(" §f" + suggestion.getPlayer());
        lore.add("");
        lore.add("§7Status:");
        lore.add(" §e" + suggestion.getStatus().name());
        lore.add("");
        lore.add("§7Tanggal:");
        lore.add(" §f" + suggestion.getDate());
        lore.add("");
        lore.add("§eKlik untuk melihat.");

        meta.setLore(lore);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        return item;

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

    public Integer getSuggestionId(int slot) {

        return suggestionSlots.get(slot);

    }

    public void open(Player player) {

        player.openInventory(inventory);

    }

}