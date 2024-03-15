package org.repliedk.session;

import cn.nukkit.Player;
import cn.nukkit.Server;
import lombok.Getter;
import lombok.Setter;

import org.repliedk.session.modules.InvitesSession;
import org.repliedk.session.modules.TimerSession;
import org.repliedk.team.Team;
import org.repliedk.team.TeamFactory;
import org.repliedk.utils.MainUtil;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public class PlayerSession {

    public String chat, currentClaim;

    public boolean leader, coleader, claimMode;

    public String name;

    public int kills, deaths, balance;

    public Team team;

    private Map<String, InvitesSession> invites = new HashMap<>();

    private Map<String, TimerSession> timers = new HashMap<>();

    public PlayerSession() {
        team = null;
        leader = false;
        coleader = false;
        setChat(MainUtil.PublicChat);
    }

    public boolean hasTeam() {
        return getTeam() != null;
    }

    public Player getPlayer() {
        return Server.getInstance().getPlayerExact(name);
    }

    public boolean isOnline() {
        return getPlayer() != null;
    }

    public void sendMessage(String message) {
        if (isOnline()) {
            getPlayer().sendMessage(message.replace("&", "§"));
        }
    }

    public void addInvite(Team team, int time) {
        invites.put(team.getName(), new InvitesSession(team, time));
    }

    public void removeInvite(String key) {
        invites.remove(key);
    }

    public InvitesSession getInvite(String key) {
        return invites.get(key);
    }

    public void onUpdate() {
        for (InvitesSession invite : getInvites().values()) {
            if (TeamFactory.get(invite.getName()) == null) {
                removeInvite(invite.getName());
                return;
            }
            if (hasTeam()) {
                removeInvite(invite.getName());
                return;
            }
            if (invite.getTime() < 1) {
                sendMessage("You invite " + invite.getTeam().getName() + " has been expired!");
                removeInvite(invite.getName());
                return;
            }
            invite.onUpdate();
        }
    }

    public void updateTimer() {
        for (TimerSession timers : timers.values()) {
            if (timers.getTime() < 0.1) {
                removeTimer(timers.getName());
                return;
            }
            timers.onUpdate();
        }
    }

    public void addTimer(String name, int time) {
        timers.put(name, new TimerSession(name, time, false));
    }

    public void addTimer(String name, int time, boolean paused) {
        timers.put(name, new TimerSession(name, time, paused));
    }

    public void removeTimer(String key) {
        timers.remove(key);
    }

    public TimerSession getTimer(String key) {
        for (TimerSession timers : timers.values()) {
            if (timers.getName().equalsIgnoreCase(key)) {
                return timers;
            }
        }
        return null;
    }

    public boolean hasTimer(String key) {
        return getTimer(key) != null;
    }

    public float getTime(String key) {
        if (getTimer(key) == null) {
            return 0.f;
        }
        return getTimer(key).getTime();
    }

}