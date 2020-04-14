package com.gameszaum.core.spigot.permission.service;

import com.gameszaum.core.spigot.permission.PermissionService;
import com.gameszaum.core.spigot.plugin.GamesCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PermissionServiceImpl implements PermissionService {

    private Map<Player, Set<String>> map;
    private PermissionAttachment attachment;

    public PermissionServiceImpl() {
        map = new Hashtable<>();
    }

    @Override
    public Map<Player, Set<String>> getPermissions() {
        return map;
    }

    @Override
    public Set<Set<Player>> getPlayersFromPermission(String perm) {
        Set<Player> playerSet = new HashSet<>();

        return map.entrySet().stream().map(entry -> {
            entry.getValue().stream().filter(s -> s.equalsIgnoreCase(perm)).findFirst().ifPresent(s -> playerSet.add(entry.getKey()));

            return playerSet;
        }).collect(Collectors.toSet());
    }

    @Override
    public Set<Set<String>> getPlayerPermissions(Player player) {
        Set<Set<String>> set = new HashSet<>();

        map.forEach((p, s) -> {
            if (p.getName().equalsIgnoreCase(player.getName())) {
                set.add(s);
            }
        });
        return set;
    }

    @Override
    public void addPermission(String perm, Player player) {
        getPlayerPermissions(player).forEach(strings -> strings.add(perm));
    }

    @Override
    public void removePermission(String perm, Player player) {
        getPlayerPermissions(player).forEach(strings -> strings.remove(perm));
    }

    @Override
    public void resetPermissions(Player player) {
        if (attachment == null) {
            attachment = player.addAttachment(GamesCore.getInstance());
        }
        attachment.getPermissions().forEach((s, aBoolean) -> attachment.unsetPermission(s));
    }

    @Override
    public void setAllPermissions(Player player) {
        getPlayerPermissions(player).forEach(strings -> strings.add("*"));
    }

    @Override
    public void load(Player player) {
        if (attachment == null) {
            attachment = player.addAttachment(GamesCore.getInstance());
        }
        getPlayerPermissions(player).forEach(strings -> strings.forEach(s -> {
            if (s.equals("*")) {
                for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
                    plugin.getServer().getPluginManager().getPermissions().forEach(permission -> attachment.setPermission(permission, true));
                }
            } else {
                attachment.setPermission(s, true);
            }
        }));
    }

}
