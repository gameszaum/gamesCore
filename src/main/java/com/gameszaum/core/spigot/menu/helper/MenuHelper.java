package com.gameszaum.core.spigot.menu.helper;

import com.gameszaum.core.spigot.menu.action.ItemAction;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface MenuHelper {

    ItemStack createItem(Material material, int amount, String name, String lore, short durability, byte data);

    ItemStack createItem(Material material, int amount, String name, String lore, short durability);

    ItemStack[] getContents();

    void showMenu(Player player);

    Inventory getMenu();

    void build();

    int rows();

    String getTitle();

    void setTitle(String title);

    ItemStack getItem(int slot);

    void removeItem(int slot);

    void fillRange(int s, int e, ItemStack item);

    void fill(ItemStack item);

    void setItem(int slot, ItemStack itemStack);

    void addItem(ItemStack itemStack);

    void setContents(ItemStack[] itemStacks);

    int nextOpenSlot();

    void runWhenEmpty(boolean state);

    void removeGlobalAction();

    void setGlobalAction(ItemAction itemAction);

    void setAction(int slot, ItemAction action);

    boolean hasAction(int slot);

}
