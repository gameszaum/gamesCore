package com.gameszaum.core.spigot.command;

import com.gameszaum.core.spigot.command.builder.CommandBase;
import com.gameszaum.core.spigot.command.builder.CommandBuilder;

public class Command {

    public static CommandBuilder create(CommandBase base) {
        return base;
    }

}
