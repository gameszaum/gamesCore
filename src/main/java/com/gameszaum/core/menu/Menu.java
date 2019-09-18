package com.gameszaum.core.menu;

import com.gameszaum.core.menu.event.MenuHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public abstract class Menu implements Listener, MenuHandler {

    public static MenuBuilder create(MenuBuilder menuBuilder) {
        return menuBuilder;
    }

    @EventHandler
    void click(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (event.getCurrentItem() == null || event.getCurrentItem().getTypeId() == 0) return;

        if (event.getCurrentItem() != null) {
            handleEvent();
        }
    }
}
