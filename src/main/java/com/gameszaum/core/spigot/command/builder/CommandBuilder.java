package com.gameszaum.core.spigot.command.builder;

import com.gameszaum.core.spigot.command.helper.CommandHelper;
import com.gameszaum.core.spigot.plugin.GamesPlugin;
import org.bukkit.command.CommandSender;

import java.util.concurrent.ThreadPoolExecutor;

public interface CommandBuilder {

    void handler(CommandSender commandSender, CommandHelper helper, String... args) throws Exception;

    void register(GamesPlugin plugin, String... alias);

    CommandBuilder player();

    CommandBuilder async();

    CommandBuilder executor(ThreadPoolExecutor executor);

    CommandBuilder permission(String perm);

}
