package com.gameszaum.core.scoreboard.helper;

import org.bukkit.entity.Player;

import java.util.List;

public interface ScoreHelper {

    Player getPlayer();

    void setTitle(String title);

    void setSlot(int slot, String text);

    void removeSlot(int slot);

    void setSlotsFromList(List<String> list);

    String getEntry(int slot);

    String getFirstSplit(String s);

    String getSecondSplit(String s);

}
