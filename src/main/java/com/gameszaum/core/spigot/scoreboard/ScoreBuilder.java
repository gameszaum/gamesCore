package com.gameszaum.core.spigot.scoreboard;

import com.gameszaum.core.spigot.Services;
import com.gameszaum.core.spigot.scoreboard.data.ScoreData;
import com.gameszaum.core.spigot.scoreboard.exception.ScoreLimiteException;
import com.gameszaum.core.spigot.scoreboard.helper.ScoreHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.List;

public class ScoreBuilder implements ScoreHelper {

    private Scoreboard scoreboard;
    private ScoreData scoreData;
    private Objective sidebar;
    private Player player;

    public ScoreBuilder(Player player) {
        this.player = player;
        this.scoreData = Services.get(ScoreData.class);

        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        sidebar = scoreboard.registerNewObjective("sidebar", "dummy");
        sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);

        // Create Teams

        for (int i = 1; i <= 15; i++) {
            Team team = scoreboard.registerNewTeam("SLOT_" + i);
            team.addEntry(getEntry(i));
        }

        // Setting scoreboard.

        player.setScoreboard(scoreboard);
        scoreData.create(this);
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void setTitle(String title) {
        title = ChatColor.translateAlternateColorCodes('&', title);
        sidebar.setDisplayName(title.length() > 32 ? title.substring(0, 32) : title);
    }

    @Override
    public void setSlot(int slot, String text) {
        Team team = scoreboard.getTeam("SLOT_" + slot);
        String entry = getEntry(slot);

        if (!scoreboard.getEntries().contains(entry)) {
            sidebar.getScore(entry).setScore(slot);
        }
        text = ChatColor.translateAlternateColorCodes('&', text);

        if (text.length() <= 32) {
            String pre = getFirstSplit(text);
            String suf = getFirstSplit(ChatColor.getLastColors(pre) + getSecondSplit(text));
            team.setPrefix(pre);
            team.setSuffix(suf);
        } else {
            new ScoreLimiteException().printStackTrace();
        }
    }

    @Override
    public void removeSlot(int slot) {
        String entry = getEntry(slot);

        if (scoreboard.getEntries().contains(entry)) {
            scoreboard.resetScores(entry);
        }
    }

    @Override
    public void setSlotsFromList(List<String> list) {
        while (list.size() > 15) {
            list.remove(list.size() - 1);
        }
        int slot = list.size();

        if (slot < 15) {
            for (int i = (slot + 1); i <= 15; i++) {
                removeSlot(i);
            }
        }
        for (String line : list) {
            setSlot(slot, line);
            slot--;
        }
    }

    @Override
    public void reset() {
        scoreboard.getTeams().stream().filter(team -> team.getName().startsWith("SLOT_")).forEach(Team::unregister);
        scoreboard.clearSlot(DisplaySlot.SIDEBAR);
    }

    @Override
    public String getEntry(int slot) {
        return ChatColor.values()[slot].toString();
    }

    @Override
    public String getFirstSplit(String s) {
        return s.length() > 16 ? s.substring(0, 16) : s;
    }

    @Override
    public String getSecondSplit(String s) {
        if (s.length() > 32) {
            s = s.substring(0, 32);
        }
        return s.length() > 16 ? s.substring(16) : "";
    }
}
