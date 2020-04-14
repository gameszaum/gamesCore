package com.gameszaum.core.spigot.command.builder;

import com.gameszaum.core.spigot.command.helper.CommandHelper;
import org.bukkit.command.CommandSender;

public interface CommandBuilder {

    void handler(CommandSender commandSender, CommandHelper helper, String... args) throws Exception;

    void setCommand(String... alias);

    CommandBuilder onlyPlayer();

    CommandBuilder onlyPermission(String perm);

    CommandBuilder runAsync();

}