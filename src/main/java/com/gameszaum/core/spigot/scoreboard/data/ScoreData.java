package com.gameszaum.core.spigot.scoreboard.data;

import com.gameszaum.core.other.services.Model;
import com.gameszaum.core.spigot.scoreboard.helper.ScoreHelper;

import java.util.Set;

public interface ScoreData extends Model<String, ScoreHelper> {

    Set<ScoreHelper> all();

}
