package org.repliedk.session;

import cn.nukkit.Server;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import lombok.Getter;
import org.repliedk.Main;
import org.repliedk.session.modules.TimerSession;
import org.repliedk.team.Team;
import org.repliedk.team.TeamFactory;
import org.repliedk.utils.MainUtil;

import java.io.File;
import java.util.*;

@Getter
public class SessionFactory {

    private Main main;
    private Map<String, PlayerSession> sessions = new HashMap<>();

    private int teamsLoaded = 0;
    private final int sessionPerLoad = 5;

    public SessionFactory(Main main) {
        this.main = main;
        onEnable();

        main.getLogger().info(TextFormat.colorize("&aSessions loaded: &e" + sessions.size()));
    }

    public void addSession(String name) {
        PlayerSession session = new PlayerSession();
        sessions.put(name.toLowerCase(), session);

        session.setName(name);
        session.setKills(0);
        session.setDeaths(0);
        session.setBalance(0);

        MainUtil.reloadCommandData();
    }

    public PlayerSession getSession(String name) {
        for (PlayerSession session : getSessions().values()) {
            if (name.equalsIgnoreCase(session.getName())) {
                return session;
            }
        }
        return null;
    }

    public static PlayerSession get(String name) {
        for (PlayerSession session : Main.getMain().getSessionFactory().getSessions().values()) {
            if (name.equalsIgnoreCase(session.getName())) {
                return session;
            }
        }
        return null;
    }

    public void onEnable() {
        for (String file : getFiles()) {
            Config config = new Config(file, Config.YAML);
            PlayerSession session = new PlayerSession();
            loadSession(session, config);
        }
    }

    public void loadSession(PlayerSession session, Config config) {
        Server.getInstance().getLogger().info("Checking session");
        session.setName(config.getString("name"));
        session.setBalance(config.getInt("balance"));
        session.setDeaths(config.getInt("deaths"));
        session.setKills(config.getInt("kills"));

        Team team = TeamFactory.get(config.getString("team"));
        if (team != null) {
            session.setTeam(team);
            session.getTeam().addMember(session);

            if (config.getBoolean("leader")) {
                team.setLeader(session);
            } else {
                if (config.getBoolean("coleader")) {
                    team.addColeader(session);
                }
            }
        }

        if (config.exists("timers")) {
            ConfigSection timersConfig = config.getSection("timers");
            for (String key : timersConfig.getKeys(false)) {
                ConfigSection section = timersConfig.getSection(key);
                session.addTimer(key, section.getInt("time", 10), config.getBoolean("paused"));
            }
        }

        sessions.put(session.getName().toLowerCase(), session);
    }

    public void onDisable() {
        for (PlayerSession session : sessions.values()) {
            Config config = new Config(main.getDataFolder() + "/data/sessions/" + session.getName() + ".yml", 2);

            Map<String, Object> info = sessionInfo(session);
            for (Map.Entry<String, Object> entry : info.entrySet()) {
                config.set(entry.getKey(), entry.getValue());
            }

            config.save();
        }
        main.getLogger().info(TextFormat.colorize("&aSessions saved: &e" + sessions.size()));
    }

    public void deleteTeam(PlayerSession session) {
        if (session != null) {
            String teamFile = main.getDataFolder() + "/data/sessions/" + session.getName() + ".yml";
            if (new File(teamFile).exists()) {
                new File(teamFile).delete();
            }
            sessions.remove(session.getName());
        }
    }

    public boolean hasExist(String name) {
        return get(name) != null;
    }

    public List<String> getFiles() {
        List<String> list = new ArrayList<>();
        File[] files = new File(main.getDataFolder() + "/data/sessions").listFiles();
        if (files != null) {
            for (File file : files) {
                list.add(file.getAbsolutePath());
            }
        }

        return list;
    }

    public Map<String, Object> sessionInfo(PlayerSession session) {
        Map<String, Object> info = new LinkedHashMap<>();
        info.put("name", session.getName());
        info.put("balance", session.getBalance());
        info.put("kills", session.getKills());
        info.put("deaths", session.getDeaths());
        info.put("team", session.hasTeam() ? session.getTeam().getName() : null);
        info.put("leader", session.hasTeam() ? session.getTeam().isLeader(session) : false);
        info.put("coleader", session.hasTeam() ? session.getTeam().isColeader(session) : false);

        Map<String, Map<String, Object>> timersData = new HashMap<>();
        for (Map.Entry<String, TimerSession> entry : session.getTimers().entrySet()) {
            String key = entry.getKey();

            Map<String, Object> timerData = new HashMap<>();

            timerData.put("time", (int) entry.getValue().getTime());
            timerData.put("paused", entry.getValue().isPaused());
            timersData.put(key, timerData);
        }

        info.put("timers", timersData);

        return info;
    }
}