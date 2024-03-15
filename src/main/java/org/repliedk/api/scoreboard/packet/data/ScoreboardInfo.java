package org.repliedk.api.scoreboard.packet.data;

public class ScoreboardInfo {

    private final long scoreboardId;
    private final String objectiveId;

    private final int score;
    private final ScoreboardType type;
    private final String name;
    private final long entityId;

    public ScoreboardInfo(long scoreboardId, String objectiveId, int score, String name) {
        this.scoreboardId = scoreboardId;
        this.objectiveId = objectiveId;
        this.score = score;
        this.type = ScoreboardType.FAKE;
        this.name = name;
        this.entityId = -1L;
    }

    public long getScoreboardId() {
        return this.scoreboardId;
    }

    public String getObjectiveId() {
        return this.objectiveId;
    }

    public int getScore() {
        return this.score;
    }

    public ScoreboardType getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public long getEntityId() {
        return this.entityId;
    }

    public enum ScoreboardType {
        INVALID,
        PLAYER,
        ENTITY,
        FAKE;

        ScoreboardType() {}
    }
}
