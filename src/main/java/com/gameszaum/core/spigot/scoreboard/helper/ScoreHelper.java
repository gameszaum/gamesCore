package com.gameszaum.core.spigot.scoreboard.helper;

import org.bukkit.entity.Player;

import java.util.List;

public interface ScoreHelper {

    void setTitle(String title);

    void setSlot(int slot, String text);

    void removeSlot(int slot);

    void setSlotsFromList(List<String> list);

    void reset();

    String getEntry(int slot);

    String getFirstSplit(String s);

    String getSecondSplit(String s);

    Player getPlayer();

}
