package com.gameszaum.core.spigot.command;

import com.gameszaum.core.spigot.command.builder.CommandBase;
import com.gameszaum.core.spigot.command.builder.CommandBuilder;
import com.gameszaum.core.spigot.plugin.GamesCore;

public class Command {

    public static CommandBuilder create(CommandBase base) {
        return base.setExecutor(GamesCore.getInstance().getCommandThread());
    }

}
