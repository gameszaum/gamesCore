package com.gameszaum.core.bungee.command;

import com.gameszaum.core.bungee.command.base.BungeeCommandBase;
import com.gameszaum.core.bungee.command.builder.BungeeCommandBuilder;
import com.gameszaum.core.bungee.command.helper.BungeeCommandHelper;
import net.md_5.bungee.api.CommandSender;
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
        proxy = ProxyServer.getInstance();
    }

    public static BungeeCommandBase create(BungeeCommandBuilder builder) {
        BungeeCommandBase base = new BungeeCommandBase() {
            @Override
            public void handle(CommandSender sender, BungeeCommandHelper helper, String... args) {
                builder.handle(sender, helper, args);
            }
        };
        commands.add(base);
        return base;
    }

    public static void delete(String alias0) {
        proxy.getPluginManager().getCommands().stream().map(Map.Entry::getValue).
                filter(command -> command.getName().equalsIgnoreCase(alias0)).findFirst().
                ifPresent(command -> proxy.getPluginManager().unregisterCommand(command));
        commands.stream().filter(command -> command.getAlias()[0].equalsIgnoreCase(alias0)).
                findFirst().ifPresent(command -> commands.remove(command));
    }

    private static BungeeCommandBase getCommandBase(String alias0) {
        return commands.stream().filter(command -> command.getAlias()[0].equalsIgnoreCase(alias0)).findFirst().orElse(null);
    }

    private static Command getCommandClass(String alias0) {
        return proxy.getPluginManager().getCommands().stream().map(Map.Entry::getValue).
                filter(command -> command.getName().equalsIgnoreCase(alias0)).findFirst().
                orElse(null);
    }

    private static Set<BungeeCommandBase> getCommands() {
        return commands;
    }
}
