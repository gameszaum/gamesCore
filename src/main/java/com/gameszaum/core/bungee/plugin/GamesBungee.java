package com.gameszaum.core.bungee.plugin;

import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

public abstract class GamesBungee extends Plugin implements BungeeLifeCycle {

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
    public void registerListener(Listener... listener) {
        for (Listener l : listener) {
            getProxy().getPluginManager().registerListener(this, l);
        }
    }
}