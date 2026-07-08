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

public class AdminSuggestionDetailGUI extends BaseGUI {

    private final VezoraFeedback plugin;
    private final Suggestion suggestion;

    public AdminSuggestionDetailGUI(VezoraFeedback plugin, Suggestion suggestion) {

        this.plugin = plugin;
        this.suggestion = suggestion;

        inventory = Bukkit.createInventory(this, 45, "§8Suggestion #" + suggestion.getId());

        load();

    }

    private void load() {

        ItemStack border = createItem(Material.GRAY_STAINED_GLASS_PANE, " ");

        for(int i=0;i<45;i++)
            inventory.setItem(i,border);

        inventory.setItem(13,createInfo());

        inventory.setItem(29,createItem(
                Material.LIME_DYE,
                "§a§lAccept"
        ));

        inventory.setItem(31,createItem(
                Material.YELLOW_DYE,
                "§e§lUnder Review"
        ));

        inventory.setItem(33,createItem(
                Material.RED_DYE,
                "§c§lReject"
        ));

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

        ItemStack item=new ItemStack(Material.WRITABLE_BOOK);

        ItemMeta meta=item.getItemMeta();

        meta.setDisplayName("§6Suggestion #"+suggestion.getId());

        List<String> lore=new ArrayList<>();

        lore.add("");
        lore.add("§ePlayer");
        lore.add(" §f"+suggestion.getPlayer());

        lore.add("");

        lore.add("§eDate");
        lore.add(" §f"+suggestion.getDate());

        lore.add("");

        lore.add("§eStatus");
        lore.add(" §f"+suggestion.getStatus());

        lore.add("");

        lore.add("§eMessage");

        lore.add(" §7"+suggestion.getMessage());

        meta.setLore(lore);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

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

    public Suggestion getSuggestion(){
        return suggestion;
    }

    public void open(Player player){
        player.openInventory(inventory);
    }

}