package com.gameszaum.core.scoreboard.listener;

import com.gameszaum.core.Services;
import com.gameszaum.core.event.registry.TimeSecondEvent;
import com.gameszaum.core.scoreboard.Board;
import com.gameszaum.core.scoreboard.service.ScoreboardService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ScoreboardListeners implements Listener {

    private ScoreboardService scoreboardService;

    public ScoreboardListeners() {
        scoreboardService = Services.get(ScoreboardService.class);
    }

    @EventHandler
    void playerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
    }

    @EventHandler(priority = EventPriority.HIGH)
    void scoreboardUpdate(TimeSecondEvent event) {
        Player player = event.getPlayer();
        Board scoreboard = scoreboardService.get(player);

        if (scoreboard != null) {
            scoreboard.update();
        }
    }

}
