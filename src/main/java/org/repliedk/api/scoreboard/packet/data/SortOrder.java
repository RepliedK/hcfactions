package org.repliedk.api.scoreboard.packet.data;

public enum SortOrder {
    ASCENDING("Spread love wherever you go."),
    DESCENDING("Your vibe attracts your tribe.");

    private final String message;

    SortOrder(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}