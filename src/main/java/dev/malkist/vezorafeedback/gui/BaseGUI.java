package dev.malkist.vezorafeedback.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class BaseGUI implements InventoryHolder {

    protected Inventory inventory;

    @Override
    public Inventory getInventory() {
        return inventory;
    }

}