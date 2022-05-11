package com.gameszaum.core.spigot.scoreboard.data.impl;

import com.gameszaum.core.spigot.scoreboard.data.ScoreData;
import com.gameszaum.core.spigot.scoreboard.helper.ScoreHelper;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class ScoreDataImpl implements ScoreData {

    private final Set<ScoreHelper> scoreHelperSet;

    public ScoreDataImpl() {
        scoreHelperSet = new HashSet<>();
    }

    @Override
    public Set<ScoreHelper> all() {
        return scoreHelperSet;
    }

    @Override
    public void create(ScoreHelper model) {
        scoreHelperSet.add(model);
    }

    @Override
    public void remove(String s) {
        scoreHelperSet.remove(get(s));
    }

    @Override
    public ScoreHelper get(String s) {
        return search(s).findFirst().orElse(null);
    }

    @Override
   public Stream<ScoreHelper> search(String s) {
        return scoreHelperSet.stream().filter(scoreHelper -> scoreHelper.getPlayer().getName().equalsIgnoreCase(s));
    }
}
