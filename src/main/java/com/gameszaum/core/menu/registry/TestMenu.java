package com.gameszaum.core.menu.registry;

import com.gameszaum.core.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TestMenu {

    public static void setupMenu(Player player) {
        Menu.create((menuHelper, menuHandler) -> {
            menuHelper.create("", 54);
            menuHelper.getInventory().setItem(1, new ItemStack(Material.REDSTONE));
        });
    }
}
