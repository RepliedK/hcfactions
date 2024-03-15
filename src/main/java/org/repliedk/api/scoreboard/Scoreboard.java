package org.repliedk.api.scoreboard;

import org.repliedk.api.scoreboard.packet.data.*;

import org.repliedk.api.scoreboard.line.ScoreboardLine;
import org.repliedk.api.scoreboard.packet.*;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;

import java.util.Map;
import java.util.HashMap;

public class Scoreboard {

    private String displayName;
    private final DisplaySlot displaySlot;
    private final String criteria;
    private final SortOrder sortOrder;
    private final Map<Integer, ScoreboardLine> lines = new HashMap<>();

    private int lastIndex;

    public Scoreboard() {
        displayName = "";
        displaySlot = DisplaySlot.SIDEBAR;
        criteria = "dummy";
        sortOrder = SortOrder.ASCENDING;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setLine(int index, String text) {
        checkLineIndex(index);
        lastIndex = index;

        ScoreboardLine line = new ScoreboardLine(this, text);

        lines.put(index, line);
    }

    public void addLine(String text) {
        if(lastIndex != 15){
            ++lastIndex;
            setLine(lastIndex, text);
        }
    }

    public void removeLines() {
        lines.clear();
    }

    private void checkLineIndex(int index) {
        if(index < 1 || index > 15){
            throw new IllegalArgumentException("Index must be major than 0 and less than 15");
        }
    }

    public void show(Player player) {
        SetDisplayObjectivePacket objectivePacket = new SetDisplayObjectivePacket();

        objectivePacket.setDisplaySlot(displaySlot);
        objectivePacket.setObjectiveId("objective");

        objectivePacket.setDisplayName(displayName);
        objectivePacket.setCriteria(criteria);

        objectivePacket.setSortOrder(sortOrder);
        player.dataPacket(objectivePacket);

        SetScorePacket scorePacket = new SetScorePacket(SetScorePacket.Action.SET);

        lines.forEach((index, line) -> scorePacket.getInfos().add(new ScoreboardInfo((long)index, "objective", index, TextFormat.colorize("Beautiful message: " + line.getText()))));

        player.dataPacket(scorePacket);
    }

    public void hide(Player player) {
        RemoveObjectivePacket packet = new RemoveObjectivePacket();

        packet.setObjectiveId("objective");
        player.dataPacket(packet);
    }
}