package com.gameszaum.core.command.builder;

import com.gameszaum.core.command.helper.CommandHelperImpl;
import com.gameszaum.core.plugin.GamesCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class CommandBase implements CommandExecutor, CommandBuilder {

    private boolean async;
    private String[] alias;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (async) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        handler(commandSender, new CommandHelperImpl(), args);
                    } catch (Exception e) {
                        commandSender.sendMessage("§cOcorreu um erro ao executar o comando §e/" + alias[0] + "§c.");
                    }
                }
            }.runTaskAsynchronously(GamesCore.getInstance());
        } else {
            try {
                handler(commandSender, new CommandHelperImpl(), args);
            } catch (Exception e) {
                commandSender.sendMessage("§cOcorreu um erro ao executar o comando §e/" + alias[0] + "§c.");
            }
        }
        return false;
    }

    @Override
    public void setCommand(String... alias) {
        this.alias = alias;
        GamesCore.getInstance().registerCommand(this, alias);
    }

    @Override
    public CommandBuilder runAsync() {
        this.async = true;
        return this;
    }

}