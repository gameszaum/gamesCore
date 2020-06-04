package com.gameszaum.core.bungee.command.helper.impl;

import com.gameszaum.core.bungee.command.helper.BungeeCommandHelper;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeCommandHelperImpl implements BungeeCommandHelper {

    @Override
    public ProxiedPlayer getProxiedPlayer(CommandSender sender) {
        return (ProxiedPlayer) sender;
    }

    @Override
    public String getMsg(int start, String... strings) {
        StringBuilder sb = new StringBuilder();

        for (int i = start; i < strings.length; i++) {
            sb.append(strings[i]).append(" ");
        }
        return sb.toString().trim();
    }
}
