package com.gameszaum.core.bungee.command;

import com.gameszaum.core.bungee.Bungee;
import com.gameszaum.core.bungee.command.base.BungeeCommandBase;
import com.gameszaum.core.bungee.command.builder.BungeeCommandBuilder;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BungeeCommand {

    private static Set<BungeeCommandBase> commands;
    private static ProxyServer proxy;

    static {
        commands = new HashSet<>();
        proxy = Bungee.getInstance().getProxy();
    }

    public static BungeeCommandBase create(BungeeCommandBuilder builder) {
        commands.add((BungeeCommandBase) builder);

        return (BungeeCommandBase) builder;
    }

    public static void delete(String alias0) {
        proxy.getPluginManager().getCommands().stream().map(Map.Entry::getValue).
                filter(command -> command.getName().equalsIgnoreCase(alias0)).findFirst().
                ifPresent(command -> proxy.getPluginManager().unregisterCommand(command));
        commands.stream().filter(command -> command.getAlias()[0].equalsIgnoreCase(alias0)).
                findFirst().ifPresent(command -> commands.remove(command));
    }

    public static BungeeCommandBase getCommandBase(String alias0) {
        return commands.stream().filter(command -> command.getAlias()[0].equalsIgnoreCase(alias0)).findFirst().orElse(null);
    }

    public static Command getCommandClass(String alias0) {
        return proxy.getPluginManager().getCommands().stream().map(Map.Entry::getValue).
                filter(command -> command.getName().equalsIgnoreCase(alias0)).findFirst().
                orElse(null);
    }

    public static Set<BungeeCommandBase> getCommands() {
        return commands;
    }
}
