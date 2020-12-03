package com.gameszaum.core.spigot.scoreboard.exception;

public class ScoreLimiteException extends Exception {

    public ScoreLimiteException() {
        super("This line has reached the character limit. (32 characters)");
    }

}
