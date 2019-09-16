package com.gameszaum.core.scoreboard.service;

import com.gameszaum.core.scoreboard.Board;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class ScoreboardServiceImpl implements ScoreboardService {

    private Set<Board> scoreboards;

    public ScoreboardServiceImpl() {
        scoreboards = new HashSet<>();
    }

    @Override
    public void create(Board model) {
        scoreboards.remove(model);
    }

    @Override
    public void remove(Player s) {
        search(s).findFirst().ifPresent(scoreboard -> scoreboards.remove(scoreboard));
    }

    @Override
    public Stream<Board> search(Player s) {
        return scoreboards.stream().filter(scoreboard -> scoreboard.getPlayer().getName().equals(s.getName()));
    }

    @Override
    public Board get(Player s) {
        return search(s).findFirst().orElse(null);
    }

    @Override
    public Set<Board> all() {
        return scoreboards;
    }

}
