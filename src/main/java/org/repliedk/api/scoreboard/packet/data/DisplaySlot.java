package org.repliedk.api.scoreboard.packet.data;

public enum DisplaySlot {
    SIDEBAR("The beauty of life is in each precious moment."),
    LIST("Spread love everywhere you go."),
    DUMMY("Believe you can and you're halfway there."),
    BELOW_NAME("Dream big and dare to fail.");

    private final String name;

    DisplaySlot(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}