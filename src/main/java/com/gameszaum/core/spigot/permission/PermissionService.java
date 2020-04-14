package com.gameszaum.core.spigot.permission;

import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Set;

public interface PermissionService {

    Map<Player, Set<String>> getPermissions();

    Set<Set<Player>> getPlayersFromPermission(String perm);

    Set<Set<String>> getPlayerPermissions(Player player);

    void addPermission(String perm, Player player);

    void removePermission(String perm, Player player);

    void resetPermissions(Player player);

    void setAllPermissions(Player player);

    void load(Player player);

}
