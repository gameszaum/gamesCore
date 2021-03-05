package com.gameszaum.core.spigot.command.builder;

import com.gameszaum.core.spigot.command.helper.CommandHelperImpl;
import com.gameszaum.core.spigot.plugin.GamesPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

public abstract class CommandBase implements CommandExecutor, CommandBuilder {

    private boolean async, onlyPlayer;
    private String perm;
    private String[] alias;
    private ThreadPoolExecutor executor;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (onlyPlayer && !(commandSender instanceof Player)) {
            commandSender.sendMessage("§cOnly for players.");
            return false;
        }
        if (perm != null && !(commandSender.hasPermission(perm))) {
            commandSender.sendMessage("§cYou do not have permission to execute this command.");
            return false;
        }

        if (async) {
            CompletableFuture.runAsync(() -> {
                try {
                    handler(commandSender, new CommandHelperImpl(), args);
                } catch (Exception e) {
                    commandSender.sendMessage("§cAn error has occurred to execute this command §e/" + alias[0] + "§c.");
                }
            }, executor);
        } else {
            try {
                handler(commandSender, new CommandHelperImpl(), args);
            } catch (Exception e) {
                commandSender.sendMessage("§cAn error has occurred to execute this command §e/" + alias[0] + "§c.");
            }
        }
        return false;
    }

    @Override
    public void setCommand(GamesPlugin plugin, String... alias) {
        this.alias = alias;
        plugin.registerCommand(this, alias);
    }

    @Override
    public CommandBuilder runAsync() {
        this.async = true;
        return this;
    }

    @Override
    public CommandBuilder onlyPermission(String perm) {
        this.perm = perm;
        return this;
    }

    @Override
    public CommandBuilder onlyPlayer() {
        this.onlyPlayer = true;
        return this;
    }

    public CommandBase setExecutor(ThreadPoolExecutor executor){
        this.executor = executor;
        return this;
    }
}