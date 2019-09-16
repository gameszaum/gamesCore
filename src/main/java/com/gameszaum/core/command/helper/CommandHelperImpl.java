package com.gameszaum.core.command.helper;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHelperImpl implements CommandHelper {

    @Override
    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean isPlayer(CommandSender sender) {
        return sender instanceof Player;
    }

    @Override
    public Player getPlayer(CommandSender sender) {
        return (Player) sender;
    }
}
