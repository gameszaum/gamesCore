package com.gameszaum.core.plugin;

import com.gameszaum.core.Services;
import com.gameszaum.core.event.registry.TimeSecondEvent;
import com.gameszaum.core.scoreboard.service.ScoreboardService;
import com.gameszaum.core.scoreboard.service.ScoreboardServiceImpl;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/*
   Github repository: https://github.com/gameszaum/gamesCore
 */
public final class GamesCore extends GamesPlugin {

    private static JavaPlugin instance;

    @Override
    public void load() {
        /* Services */

        instance = this;

        Services.create(this);
        Services.add(ScoreboardService.class, new ScoreboardServiceImpl());
    }

    @Override
    public void enable() {

        /* TimeSecondEvent call */

        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(o -> new TimeSecondEvent(o).call());
            }
        }.runTaskTimerAsynchronously(this, 0L, 20L);
    }

    @Override
    public void disable() {
    }

    public static JavaPlugin getInstance() {
        return instance;
    }
}
