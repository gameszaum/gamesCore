package com.gameszaum.core.spigot.scoreboard.exception;

public class ScoreLimitLineException extends Exception {

    public ScoreLimitLineException() {
        super("This line has reached the character limit. (32 characters)");
    }

}
