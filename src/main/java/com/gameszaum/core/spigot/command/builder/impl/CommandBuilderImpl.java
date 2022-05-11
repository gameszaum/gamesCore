package com.gameszaum.core.spigot.command.builder.impl;

import com.gameszaum.core.spigot.command.builder.CommandBuilder;
import com.gameszaum.core.spigot.command.helper.CommandHelperImpl;
import com.gameszaum.core.spigot.plugin.GamesPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

public abstract class CommandBuilderImpl implements CommandExecutor, com.gameszaum.core.spigot.command.builder.CommandBuilder {

    private boolean async, onlyPlayer;
    private String permission;
    private ThreadPoolExecutor executor;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (onlyPlayer && !(commandSender instanceof Player)) {
            commandSender.sendMessage("§cOnly for players.");
            return false;
        }
        if (permission != null && !(commandSender.hasPermission(permission))) {
            commandSender.sendMessage("§cYou do not have permission to execute this command.");
            return false;
        }

        if (async) {
            CompletableFuture.runAsync(() -> {
                try {
                    handler(commandSender, new CommandHelperImpl(), args);
                } catch (Exception e) {
                    commandSender.sendMessage("§cAn error has occurred to execute this command §e/" + command.getName() + "§c.");
                    e.printStackTrace();
                }
            }, executor);
        } else {
            try {
                handler(commandSender, new CommandHelperImpl(), args);
            } catch (Exception e) {
                commandSender.sendMessage("§cAn error has occurred to execute this command §e/" + command.getName() + "§c.");
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void register(GamesPlugin plugin, String... alias) {
        plugin.registerCommand(this, alias);
    }

    @Override
    public com.gameszaum.core.spigot.command.builder.CommandBuilder async() {
        this.async = true;
        return this;
    }

    @Override
    public CommandBuilder executor(ThreadPoolExecutor executor) {
        this.executor = executor;
        return this;
    }

    @Override
    public com.gameszaum.core.spigot.command.builder.CommandBuilder permission(String perm) {
        this.permission = perm;
        return this;
    }

    @Override
    public com.gameszaum.core.spigot.command.builder.CommandBuilder player() {
        this.onlyPlayer = true;
        return this;
    }
}