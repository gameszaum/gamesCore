package com.gameszaum.core.menu.helper;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MenuHelperImpl implements MenuHelper {

    private Inventory inventory;

    @Override
    public void create(String title, int size) {
        inventory = Bukkit.createInventory(null, size, title);
    }

    @Override
    public void open(Player player) {
        player.openInventory(inventory);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
