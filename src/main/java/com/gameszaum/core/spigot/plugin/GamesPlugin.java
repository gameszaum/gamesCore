package com.gameszaum.core.spigot.plugin;

import com.gameszaum.core.spigot.command.loader.CommandRegister;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class GamesPlugin extends JavaPlugin implements PluginLifeCycle {

    @Override
    public void onLoad() {
        load();
    }

    @Override
    public void onEnable() {
        enable();
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

    @Override
    public <T extends CommandExecutor> void registerCommand(T command, String... aliases) {
        CommandRegister.registerCommand(this, command, aliases);
    }
}
