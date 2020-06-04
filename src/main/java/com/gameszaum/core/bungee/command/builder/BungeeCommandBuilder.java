package com.gameszaum.core.bungee.command.builder;

import com.gameszaum.core.bungee.command.helper.BungeeCommandHelper;
import net.md_5.bungee.api.CommandSender;

public interface BungeeCommandBuilder {

    void handle(CommandSender sender, BungeeCommandHelper helper, String... args);

}
