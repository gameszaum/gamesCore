package com.gameszaum.core.bungee;

import com.gameszaum.core.bungee.plugin.GamesBungee;

/*
   Github repository: https://github.com/gameszaum/gamesCore
 */
public final class Bungee extends GamesBungee {

    private static GamesBungee instance;

    @Override
    public void load() {
        instance = this;
    }

    @Override
    public void enable() {
        System.out.println("[GamesCore] " + getDescription().getVersion() + " - BungeeCord plugin enabled.");
    }

    @Override
    public void disable() {
    }
}
