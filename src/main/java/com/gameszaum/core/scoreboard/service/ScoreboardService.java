package com.gameszaum.core.scoreboard.service;

import com.gameszaum.core.scoreboard.Board;
import com.gameszaum.core.services.Model;
import org.bukkit.entity.Player;

import java.util.Set;

public interface ScoreboardService extends Model<Player, Board> {

    Set<Board> all();

}
