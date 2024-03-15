package org.repliedk.team;

import cn.nukkit.level.Position;
import cn.nukkit.utils.TextFormat;
import lombok.Getter;
import lombok.Setter;
import org.repliedk.Main;
import org.repliedk.session.PlayerSession;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Team {

    private static final int FREEZE_TIME_DURATION = 600;
    private static final int REGENERATION_TIME_DURATION = 180;
    private static final int MAX_DTR_BASE = 1;

    private Team focus;
    private String name;
    private int points = 0, balance = 0, strikes = 0, kills = 0, dtr = 2;
    private int freezeTime = 0, regenerationTime = 0;
    private PlayerSession leader;
    private List<PlayerSession> members = new ArrayList<>(), coleaders = new ArrayList<>();
    private Position home;
    private boolean freeze = false, regeneration = false;

    public Team(String name) {
        this.name = name;
    }

    public void sendAnnounce(String announce) {
        for (PlayerSession session : getOnlinePlayers()) {
            session.sendMessage(announce);
        }
    }

    public String homeToString() {
        return (home == null) ? "&fNone" : "&r&f(" + home.getFloorX() + ", " + home.getFloorY() + ", " + home.getFloorZ() + ")";
    }

    public void checkLoad() {
        if (getDtr() < getMaxDtr()) {
            setFreezeTeam();
        }
    }

    public void setFreezeTeam() {
        setFreezeTime(FREEZE_TIME_DURATION);
        setFreeze(true);
        if (isRegeneration()) {
            setRegeneration(false);
        }
    }

    public int getMaxDtr() {
        int membersDtr = getMembers().size();
        return MAX_DTR_BASE + membersDtr;
    }

    public void onUpdate() {
        if (getDtr() >= getMaxDtr()) return;

        if (isFreeze()) {
            handleFreeze();
        } else if (isRegeneration()) {
            handleRegeneration();
        }
    }

    private void handleFreeze() {
        if (getFreezeTime() <= 0) {
            sendAnnounce("&aYour team is regenerating 1 DTR for 3 minutes!");
            setRegenerationTime(REGENERATION_TIME_DURATION);
            setRegeneration(true);
            setFreeze(false);
            return;
        }
        setFreezeTime(getFreezeTime() - 1);
    }

    private void handleRegeneration() {
        if (getRegenerationTime() <= 0) {
            regenerateDtr();
        }
        setRegenerationTime(getRegenerationTime() - 1);
    }

    private void regenerateDtr() {
        setDtr(getDtr() + 1);

        if (getDtr() >= getMaxDtr()) {
            sendAnnounce("&aYour team has completed regeneration of DTR!");
            setRegeneration(false);
            setRegenerationTime(0);
            return;
        }

        sendAnnounce("&aYour team has regenerated 1 DTR!");
        setRegenerationTime(REGENERATION_TIME_DURATION);
    }

    public List<PlayerSession> getOnlinePlayers() {
        return getMembers().stream().filter(PlayerSession::isOnline).toList();
    }

    public void setLeader(PlayerSession leader) {
        this.leader = leader;
        Main.getMain().getLogger().info(TextFormat.colorize("&eLeader member " + leader + " to team " + getName()));
    }

    public boolean isColeader(PlayerSession member) {
        return coleaders.contains(member);
    }

    public boolean isLeader(PlayerSession member) {
        return leader.equals(member);
    }

    public void addMember(PlayerSession member) {
        member.setTeam(this);
        members.add(member);
        Main.getMain().getLogger().info(TextFormat.colorize("&eJoin member " + member.getName() + " to team " + getName()));
    }

    public void removeMember(PlayerSession member) {
        if (members.contains(member)) {
            if (isColeader(member)) {
                removeColeader(member);
            }
            members.remove(member);
            Main.getMain().getLogger().info(TextFormat.colorize("&eLeave member " + member.getName() + " from team " + getName()));
            member.setTeam(null);
        }
    }

    public void removeColeader(PlayerSession member) {
        if (coleaders.contains(member)) {
            coleaders.remove(member);
            Main.getMain().getLogger().info(TextFormat.colorize("&eDemote member " + member.getName() + " from team " + getName()));
        }
    }

    public void addColeader(PlayerSession member) {
        if (coleaders.contains(member)) return;

        if (coleaders.size() >= 2) {
            Main.getMain().getLogger().info(TextFormat.colorize("&cCoLeaders max reached for team " + getName()));
            return;
        }

        coleaders.add(member);
        Main.getMain().getLogger().info(TextFormat.colorize("&eJoin member " + member.getName() + " as coleader of team " + getName()));
    }

    public void disbandTeam() {
        setLeader(null);

        List<PlayerSession> membersCopy = new ArrayList<>(getMembers());

        for (PlayerSession member : membersCopy) {
            sendAnnounce("&c" + member.getName() + " has left the team!");
            removeMember(member);
        }

        TeamFactory.deleteTeam(this);
    }

    public void removeDtr(){
        if (getDtr() > -4) dtr--;
    }
}
