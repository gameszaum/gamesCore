package com.gameszaum.core.spigot.menu;

import com.gameszaum.core.spigot.menu.action.ItemAction;
import com.gameszaum.core.spigot.menu.helper.MenuHelper;
import com.gameszaum.core.spigot.plugin.GamesCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Menu implements MenuHelper {

    private String title;
    private int rows = 3;
    private HashMap<Integer, ItemStack> content = new HashMap<>();
    private HashMap<Integer, ItemAction> commands = new HashMap<>();
    private Inventory inventory;
    private JavaPlugin plugin;
    private ItemAction gaction;
    private boolean runempty = true;

    public Menu(String title, int rows) {
        if (rows < 1 || rows > 6) throw new IndexOutOfBoundsException("Menu can only have between 1 and 6 rows.");
        this.title = title;
        this.rows = rows;
        this.plugin = GamesCore.getInstance();

        setListener(plugin);
    }

    private void setListener(JavaPlugin pl) {
        pl.getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onInvClick(InventoryClickEvent event) {
                Player player = (Player) event.getWhoClicked();
                Inventory inv = event.getInventory();
                ItemStack item = event.getCurrentItem();
                int slot = event.getRawSlot();
                InventoryAction a = event.getAction();

                if (item == null || item.getType() == Material.AIR) {
                    if (!runempty) return;
                }

                if (inv.getName().equals(title)) {
                    if (inv.equals(inventory)) {
                        if (slot <= (rows*9)-1) {
                            event.setCancelled(true);
                            if (hasAction(slot)) commands.get(slot).run(player, inv, item, slot, a);
                            if (gaction != null) gaction.run(player, inv, item, slot, a);
                        }
                    }
                }
            }
        }, pl);
    }

    @Deprecated
    public boolean hasAction(int slot) {
        return commands.containsKey(slot);
    }

    @Deprecated
    public void setAction(int slot, ItemAction action) {
        commands.put(slot, action);
    }

    public void setGlobalAction(ItemAction action) {
        this.gaction = action;
    }

    public void removeGlobalAction() {
        this.gaction = null;
    }

    @Deprecated
    public void removeAction(int slot) {
        if (commands.containsKey(slot)) {
            commands.remove(slot);
        }
    }

    public void runWhenEmpty(boolean state) {
        this.runempty = state;
    }

    public int nextOpenSlot() {
        int h = 0;
        for (Integer i : content.keySet()) {
            if (i > h) h = i;
        }
        for (int i = 0; i <= h; i++) {
            if (!content.containsKey(i)) return i;
        }
        return h+1;
    }

    public void setContents(ItemStack[] contents) throws ArrayIndexOutOfBoundsException {
        if (contents.length > rows*9) throw new ArrayIndexOutOfBoundsException("setContents() : Contents are larger than inventory.");
        content.clear();
        for (int i = 0; i < contents.length; i++) {
            if (contents[i] != null && contents[i].getType() != Material.AIR) content.put(i, contents[i]);
        }
    }

    public void addItem(ItemStack item) {
        if (nextOpenSlot() > (rows*9)-1) {
            plugin.getLogger().info("addItem() : Inventory is full.");
            return;
        }
        setItem(nextOpenSlot(), item);
    }

    public void setItem(int slot, ItemStack item) throws IndexOutOfBoundsException {
        if (slot < 0 || slot > (rows*9)-1) throw new IndexOutOfBoundsException("setItem() : Slot is outside inventory.");
        if (item == null || item.getType() == Material.AIR) {
            removeItem(slot);
            return;
        }
        content.put(slot, item);
    }

    public void fill(ItemStack item) {
        for (int i = 0; i < rows*9; i++) content.put(i, item);
    }

    public void fillRange(int s, int e, ItemStack item) throws IndexOutOfBoundsException {
        if (e <= s) throw new IndexOutOfBoundsException("fillRange() : Ending index must be less than starting index.");
        if (s < 0 || s > (rows*9)-1) throw new IndexOutOfBoundsException("fillRange() : Starting index is outside inventory.");
        if (e < 0 || e > (rows*9)-1) throw new IndexOutOfBoundsException("fillRange() : Ending index is outside inventory.");
        for (int i = s; i <= e; i++) content.put(i, item);
    }

    public void removeItem(int slot) {
        if (content.containsKey(slot)) content.remove(slot);
    }

    public ItemStack getItem(int slot) {
        if (content.containsKey(slot)) return content.get(slot);
        return null;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public int rows() {
        return this.rows;
    }

    public void build() {
        this.inventory = Bukkit.createInventory(null, rows*9, this.title);
        inventory.clear();
        for (Map.Entry<Integer, ItemStack> entry : content.entrySet()) inventory.setItem(entry.getKey(), entry.getValue());
    }

    public Inventory getMenu() {
        build();
        return inventory;
    }

    public void showMenu(Player player) {
        player.openInventory(getMenu());
    }

    public ItemStack[] getContents() {
        return getMenu().getContents();
    }

    public ItemStack createItem(Material material, int amount, String name, String lore, short durability) throws IndexOutOfBoundsException {
        if (amount < 1 || amount > 64) {
            throw new IndexOutOfBoundsException("Amount should be between 1 and 64.");
        }
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();

        if (name != null && name != "") {
            meta.setDisplayName(name);
        }
        if (lore != null && lore != "") {
            String[] lines = lore.split(Pattern.quote("^$"));
            List<String> newlore = new ArrayList<String>();
            for (String s : lines) {
                newlore.add(s);
            }
            meta.setLore(newlore);
        }
        item.setDurability(durability);
        item.setItemMeta(meta);

        return item;
    }
    public ItemStack createItem(Material material, int amount, String name, String lore, short durability, byte data) throws IndexOutOfBoundsException {
        if (amount < 1 || amount > 64) {
            throw new IndexOutOfBoundsException("Amount should be between 1 and 64.");
        }
        ItemStack item = new ItemStack(material, amount, data);
        ItemMeta meta = item.getItemMeta();

        if (name != null && name != "") {
            meta.setDisplayName(name);
        }
        if (lore != null && lore != "") {
            String[] lines = lore.split(Pattern.quote("^$"));
            List<String> newlore = new ArrayList<String>();
            for (String s : lines) {
                newlore.add(s);
            }
            meta.setLore(newlore);
        }
        item.setDurability(durability);
        item.setItemMeta(meta);

        return item;
    }

}