package org.repliedk.session.modules;

import lombok.Getter;
import lombok.Setter;
import org.repliedk.team.Team;

@Getter @Setter
public class InvitesSession {

    private Team team;
    private int time;
    private String name;

    public InvitesSession(Team team, int time) {
        this.name = "You're invited to join " + team.getName() + " team!";
        this.team = team;
        this.time = time;
    }

    public void onUpdate() {
        time--;
    }

}