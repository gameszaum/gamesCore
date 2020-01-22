package com.gameszaum.core.exception.scoreboard;

public class ScoreLimiteLine extends Exception {

    public ScoreLimiteLine() {
        super("This line has reached the character limit. (32 characters)");
    }

}
