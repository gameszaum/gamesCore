package com.gameszaum.core.other.classes;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class ListenerLoader {

    private Plugin plugin;

    public ListenerLoader(Plugin plugin) {
        this.plugin = plugin;
    }

    public void load(String pkg) {
        ClassGetter.getClassesForPackage(plugin, pkg).stream().filter(aClass -> Listener.class.isAssignableFrom(aClass) && aClass != Listener.class).forEach(aClass -> {
            try {
                Listener listener = (Listener) aClass.newInstance();

                Bukkit.getPluginManager().registerEvents(listener, plugin);
                System.out.println("[GamesCore] Listener '" + aClass.getSimpleName() + "' registered sucessfully.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
