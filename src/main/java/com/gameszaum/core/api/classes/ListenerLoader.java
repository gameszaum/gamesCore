package com.gameszaum.core.api.classes;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class ListenerLoader {

    private Plugin plugin;

    public ListenerLoader(Plugin plugin) {
        this.plugin = plugin;
    }

    public void load(String packageName) {
        ClassGetter.getClassesForPackage(plugin, packageName).stream().filter(Listener.class::isAssignableFrom).forEach(aClass -> {
            try {
                Listener listener = (Listener) aClass.newInstance();

                plugin.getServer().getPluginManager().registerEvents(listener, plugin);
                System.out.println("[GamesCore] Listener '" + aClass.getSimpleName() + "' registered sucessfully.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
