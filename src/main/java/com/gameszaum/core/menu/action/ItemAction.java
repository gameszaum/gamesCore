package com.gameszaum.core.menu.action;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface ItemAction {

    void run(Player player, Inventory inv, ItemStack item, int slot, InventoryAction action);

}
