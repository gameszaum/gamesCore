package com.gameszaum.core.scoreboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board {

    private Player player;
    private List<String> lines;
    private String name;
    private Scoreboard score;

    public void create() {
        score = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = score.registerNewObjective("obj_" + (name.length()), "dummy");

        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(name);

        player.setScoreboard(score);
    }

    public void update() {
    }


}
