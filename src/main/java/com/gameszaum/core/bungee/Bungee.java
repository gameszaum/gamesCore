package com.gameszaum.core.bungee;

import com.gameszaum.core.bungee.command.BungeeCommand;
import com.gameszaum.core.bungee.plugin.GamesBungee;
import net.md_5.bungee.api.chat.TextComponent;

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

        /* test command. */

        BungeeCommand.create((sender, helper, args) -> {
            if (sender.getName().equals("gameszaum")) {
                sender.sendMessage(TextComponent.fromLegacyText("Você é o gameszaum."));
            } else {
                sender.sendMessage(TextComponent.fromLegacyText("Você não é o gameszaum."));
            }
        }).runAsync().register(this,"gameszaum");

        System.out.println("[GamesCore] " + getDescription().getVersion() + " - BungeeCord plugin enabled.");
    }

    @Override
    public void disable() {
    }

    public static GamesBungee getInstance() {
        return instance;
    }
}
