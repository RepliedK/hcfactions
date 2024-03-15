package org.repliedk.api.scoreboard.packet;

import cn.nukkit.network.protocol.DataPacket;

public class RemoveObjectivePacket extends DataPacket {
    public static final byte NETWORK_ID = 106;

    private String objectiveId;

    public byte pid() {
        return NETWORK_ID;
    }

    public void decode() {

    }

    public void encode() {
        reset();
        putString(objectiveId);
    }

    public void setObjectiveId(String objectiveId) {
        this.objectiveId = objectiveId;
    }
}