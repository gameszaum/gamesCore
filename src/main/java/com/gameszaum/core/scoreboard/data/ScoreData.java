package com.gameszaum.core.scoreboard.data;

import com.gameszaum.core.scoreboard.helper.ScoreHelper;
import com.gameszaum.core.services.Model;

import java.util.Set;

public interface ScoreData extends Model<String, ScoreHelper> {

    Set<ScoreHelper> all();

}
