package com.gameszaum.core.plugin;

import com.gameszaum.core.Services;
import com.gameszaum.core.command.loader.CommandRegister;
import com.gameszaum.core.command.registry.TestCommands;
import com.gameszaum.core.event.registry.TimeSecondEvent;
import com.gameszaum.core.scoreboard.service.ScoreboardService;
import com.gameszaum.core.scoreboard.service.ScoreboardServiceImpl;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
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
        Services.add(ScoreboardService.class, new ScoreboardServiceImpl());
    }

    @Override
    public void enable() {
        TestCommands.setup();

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

    @Override
    public <T extends CommandExecutor> void registerCommand(T command, String... aliases) {
        CommandRegister.registerCommand(this, command, aliases);
    }
}
