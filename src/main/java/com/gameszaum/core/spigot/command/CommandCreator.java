package com.gameszaum.core.spigot.command;

import com.gameszaum.core.spigot.command.builder.CommandBuilder;
import com.gameszaum.core.spigot.command.builder.impl.CommandBuilderImpl;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CommandCreator {

    public static CommandBuilder create(CommandBuilderImpl base) {
        return base.executor(new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>()));
    }

}
