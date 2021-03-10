package com.gameszaum.core.spigot.command;

import com.gameszaum.core.spigot.command.builder.CommandBase;
import com.gameszaum.core.spigot.command.builder.CommandBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Command {

    public static CommandBuilder create(CommandBase base) {
        return base.setExecutor(new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>()));
    }

}
