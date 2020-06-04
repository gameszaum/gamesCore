package com.gameszaum.core.bungee.command.asserts;

public interface BungeeCommandAsserts {

    BungeeCommandAsserts runAsync();

    BungeeCommandAsserts assertPlayer();

    BungeeCommandAsserts assertPermision(String perm);

    void register(String... alias);

}
