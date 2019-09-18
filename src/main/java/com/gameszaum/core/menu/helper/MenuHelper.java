package com.gameszaum.core.menu.helper;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface MenuHelper {

    void open(Player player);

    void create(String title, int size);

    Inventory getInventory();

}
