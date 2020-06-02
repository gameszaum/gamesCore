package com.gameszaum.core.spigot.plugin;

import com.gameszaum.core.spigot.Services;
import com.gameszaum.core.spigot.command.registry.ExampleCommands;
import com.gameszaum.core.spigot.event.registry.TimeSecondEvent;
import com.gameszaum.core.spigot.permission.PermissionService;
import com.gameszaum.core.spigot.permission.service.PermissionServiceImpl;
import com.gameszaum.core.spigot.scoreboard.data.ScoreData;
import com.gameszaum.core.spigot.scoreboard.data.ScoreDataImpl;
import org.bukkit.Bukkit;

/*
   Github repository: https://github.com/gameszaum/gamesCore
 */
public final class GamesCore extends GamesPlugin {

    private static GamesPlugin instance;

    @Override
    public void load() {
        instance = this;

        /* Services */

        Services.create(this);
        Services.add(ScoreData.class, new ScoreDataImpl());
        Services.add(PermissionService.class, new PermissionServiceImpl());
    }

    @Override
    public void enable() {
        /* Commands. */

        ExampleCommands.setup();

        /* Listeners */

        /* TimeSecondEvent call */

        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, () -> Bukkit.getOnlinePlayers().forEach(o -> new TimeSecondEvent(o).call()), 0L, 20L);

        System.out.println("[GamesCore] " + getDescription().getVersion() + " - Spigot plugin enabled.");
    }

    @Override
    public void disable() {}

    public static GamesPlugin getInstance() {
        return instance;
    }
}
