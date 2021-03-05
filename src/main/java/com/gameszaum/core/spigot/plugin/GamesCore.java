package com.gameszaum.core.spigot.plugin;

import com.gameszaum.core.spigot.Services;
import com.gameszaum.core.spigot.event.registry.TimeSecondEvent;
import com.gameszaum.core.spigot.permission.PermissionService;
import com.gameszaum.core.spigot.permission.service.PermissionServiceImpl;
import com.gameszaum.core.spigot.scoreboard.data.ScoreData;
import com.gameszaum.core.spigot.scoreboard.data.impl.ScoreDataImpl;
import org.bukkit.Bukkit;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*
   Github repository: https://github.com/gameszaum/gamesCore
 */
public final class GamesCore extends GamesPlugin {

    private ThreadPoolExecutor commandThread;
    private static GamesCore instance;

    @Override
    public void load() {
        Services.create(this);
        Services.add(ScoreData.class, new ScoreDataImpl());
        Services.add(PermissionService.class, new PermissionServiceImpl());
    }

    @Override
    public void enable() {
        instance = this;
        commandThread = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, () -> Bukkit.getOnlinePlayers().forEach(o -> new TimeSecondEvent(o).call()), 0L, 20L);
        System.out.println("[GamesCore] " + getDescription().getVersion() + " - Spigot plugin enabled.");
    }

    @Override
    public void disable() {
        commandThread.shutdown();
    }

    public ThreadPoolExecutor getCommandThread() {
        return commandThread;
    }

    public static GamesCore getInstance() {
        return instance;
    }
}
