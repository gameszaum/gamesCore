package com.gameszaum.core.plugin;

import com.gameszaum.core.Services;
import com.gameszaum.core.command.registry.ExampleCommands;
import com.gameszaum.core.event.registry.TimeSecondEvent;
import com.gameszaum.core.scoreboard.data.ScoreData;
import com.gameszaum.core.scoreboard.data.ScoreDataImpl;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

/*
   Github repository: https://github.com/gameszaum/gamesCore
 */
public final class GamesCore extends GamesPlugin {

    private static GamesPlugin instance;

    @Override
    public void load() {
        /* Services */

        instance = this;

        Services.create(this);
        Services.add(ScoreData.class, new ScoreDataImpl());
    }

    @Override
    public void enable() {
        /* Commands. */

        ExampleCommands.setup();

        /* Listeners */

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

    public static GamesPlugin getInstance() {
        return instance;
    }
}
