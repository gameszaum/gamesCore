package com.gameszaum.core.spigot.listener;

import com.gameszaum.core.other.util.ClassGetter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class ListenerLoader {

    private JavaPlugin plugin;

    public ListenerLoader(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void load(String pkg, String prefix) {
        ClassGetter.getClassesForPackage(plugin, pkg).stream().filter(aClass -> Listener.class.isAssignableFrom(aClass) && aClass != Listener.class).forEach(aClass -> {
            try {
                Listener listener = (Listener) aClass.newInstance();

                Bukkit.getPluginManager().registerEvents(listener, plugin);
                System.out.println("[" + prefix + "] Listener '" + aClass.getSimpleName() + "' registered sucessfully.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
