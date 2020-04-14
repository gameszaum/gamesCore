package com.gameszaum.core.spigot.plugin;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

public interface PluginLifeCycle {

    void load();

    void enable();

    void disable();

    void registerListeners(Listener... listeners);

    <T extends CommandExecutor> void registerCommand(T command, String... aliases);

}
