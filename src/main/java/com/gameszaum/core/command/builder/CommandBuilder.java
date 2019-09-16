package com.gameszaum.core.command.builder;

import com.gameszaum.core.command.helper.CommandHelper;
import org.bukkit.command.CommandSender;

public interface CommandBuilder<T extends CommandSender> {

    void handler(T commandSender, CommandHelper helper, String... args) throws Exception;

    void setCommand(String... alias);

    CommandBuilder runAsync();

}
