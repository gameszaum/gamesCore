package com.gameszaum.core.command;

import com.gameszaum.core.command.builder.CommandBase;
import com.gameszaum.core.command.builder.CommandBuilder;

public class Command {

    public static CommandBuilder create(CommandBase base) {
        return base;
    }

}
