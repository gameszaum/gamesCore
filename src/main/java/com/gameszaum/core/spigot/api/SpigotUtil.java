package com.gameszaum.core.spigot.api;

import com.gameszaum.core.other.util.Util;
import com.gameszaum.core.spigot.api.title.Title;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Copyright (C) gameszaum, all rights reserved, unauthorized
 * utilization or copy of this file, is strictly prohibited and
 * liable to civil and criminal penalties, the project 'gamesCore'
 * is private and the re-sale without contact with me (gameszaum) is not allowed.
 */
public class SpigotUtil extends Util {

    public static List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = Lists.newArrayList();

        for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }

    public static void clearPlayer(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.updateInventory();

        player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));

        player.setFoodLevel(20);
        player.setHealth(20);

        player.setGameMode(GameMode.SURVIVAL);
    }

    public static void sendMessageTime(String msg, int time, List<Integer> times) {
        for (int i : times) {
            if (time == i) {
                Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(msg));
            }
        }
    }

    public static void sendTitleTime(String title, String subtitle, int time, List<Integer> times) {
        for (int i : times) {
            if (time == i) {
                Bukkit.getOnlinePlayers().forEach(player -> Title.sendTitle(player, title, subtitle));
            }
        }
    }

}
