package org.repliedk.team;

import cn.nukkit.Server;
import cn.nukkit.level.Position;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import lombok.Getter;
import org.repliedk.Main;
import org.repliedk.session.PlayerSession;
import org.repliedk.utils.MainUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class TeamFactory {

    private Main main;
    private Map<String, Team> teams = new HashMap<>();

    private int teamsLoaded = 0;
    private final int teamsPerLoad = 5;

    public TeamFactory(Main main) {
        this.main = main;
        onEnable();

        main.getLogger().info(TextFormat.colorize("&aTeams loaded: &e" + teams.size()));
    }

    public void addTeam(String name, PlayerSession player) {
        Team team = new Team(name);
        teams.put(name.toLowerCase(), team);

        team.addMember(player);
        team.setLeader(player);
        team.setDtr(2);

        MainUtil.reloadCommandData();
    }
    public Team getTeam(String name) {
        for (Team teams : getTeams().values()) {
            if (name.equalsIgnoreCase(teams.getName())) {
                return teams;
            }
        }
        return null;
    }

    public static Team get(String name) {
        for (Team teams : Main.getMain().getTeamFactory().getTeams().values()) {
            if (name.equalsIgnoreCase(teams.getName())) {
                return teams;
            }
        }
        return null;
    }

    public void onEnable() {
        for (String file : getFiles()) {
            Config config = new Config(file, Config.YAML);
            Team team = new Team(config.getString("name"));
            team.setHome(convert(config.getString("home")));

            team.setBalance(config.getInt("balance", 0));
            team.setPoints(config.getInt("points", 0));
            team.setStrikes(config.getInt("strikes", 0));
            team.setKills(config.getInt("kills", 0));
            team.setDtr(config.getInt("dtr", 0));

            team.checkLoad();

            teams.put(team.getName().toLowerCase(), team);
        }
    }

    public Position convert(String homeString) {
        if (homeString == null || homeString.equals("null")) {
            return null;
        }

        String[] homeParts = homeString.split(":");
        int x = Integer.parseInt(homeParts[0]);
        int y = Integer.parseInt(homeParts[1]);
        int z = Integer.parseInt(homeParts[2]);

        return new Position(x, y, z, Server.getInstance().getLevelByName(homeParts[3]));

    }

    public void onDisable() {
        for (Team team : teams.values()) {
            Config c = new Config(main.getDataFolder() + "/data/teams/" + team.getName() + ".yml", 2);
            c.set("name", team.getName());
            c.set("balance", team.getBalance());
            c.set("points", team.getPoints());
            c.set("strikes", team.getStrikes());
            c.set("kills", team.getKills());
            c.set("dtr", team.getDtr());
            String home = team.getHome() != null ? team.getHome().getFloorX() + ":" + team.getHome().getFloorY() + ":" + team.getHome().getFloorZ() + ":" + team.getHome().getLevel().getName() : null;
            c.set("home", home);
            c.save();
        }
        main.getLogger().info(TextFormat.colorize("&aTeams saved: &e" + teams.size()));
    }

    public static void deleteTeam(Team team) {
        if (team != null) {
            String teamFile = Main.getMain().getDataFolder() + "/data/teams/" + team.getName() + ".yml";
            if (new File(teamFile).exists()) {
                new File(teamFile).delete();
            }
            Main.getMain().getTeamFactory().teams.remove(team.getName().toLowerCase(), team);
        }

        MainUtil.reloadCommandData();
    }

    public boolean hasExist(String name) {
        return get(name) != null;
    }

    public List<String> getFiles() {
        List<String> list = new ArrayList<>();
        File[] files = new File(main.getDataFolder() + "/data/teams").listFiles();
        if (files != null) {
            for (File file : files) {
                list.add(file.getAbsolutePath());
            }
        }

        return list;
    }
}