package com.gameszaum.core.bungee.command.helper;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public interface BungeeCommandHelper {

    ProxiedPlayer getProxiedPlayer(CommandSender sender);

    String getMsg(int start, String... strings);

}
