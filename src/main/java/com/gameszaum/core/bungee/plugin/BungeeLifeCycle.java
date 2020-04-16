package com.gameszaum.core.bungee.plugin;

import net.md_5.bungee.api.plugin.Listener;

public interface BungeeLifeCycle {

    void load();

    void enable();

    void disable();

    void registerListener(Listener... listener);

}
