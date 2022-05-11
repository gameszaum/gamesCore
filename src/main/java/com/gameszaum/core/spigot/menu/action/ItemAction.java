package com.gameszaum.core.spigot.menu.action;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface ItemAction {

    void run(Inventory inv, ItemStack item, int slot, InventoryAction action, ClickType clickType);

}
