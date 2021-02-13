package com.gameszaum.core.bungee.command.asserts;

import net.md_5.bungee.api.plugin.Plugin;

public interface BungeeCommandAsserts {

    BungeeCommandAsserts runAsync();

    BungeeCommandAsserts assertPlayer();

    BungeeCommandAsserts assertPermision(String perm);

    void register(Plugin plugin, String... alias);

}
