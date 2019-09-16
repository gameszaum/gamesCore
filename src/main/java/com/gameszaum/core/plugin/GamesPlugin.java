package com.gameszaum.core.plugin;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class GamesPlugin extends JavaPlugin implements PluginLifeCycle {

    @Override
    public void onEnable() {
        enable();
    }

    @Override
    public void onLoad() {
        load();
    }

    @Override
    public void onDisable() {
        disable();
    }

    @Override
    public void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }
}
