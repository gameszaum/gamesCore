package com.gameszaum.core.spigot.scoreboard.exception;

public class ScoreLimiteLine extends Exception {

    public ScoreLimiteLine() {
        super("This line has reached the character limit. (32 characters)");
    }

}
