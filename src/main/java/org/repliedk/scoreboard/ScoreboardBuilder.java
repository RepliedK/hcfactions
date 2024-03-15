package org.repliedk.scoreboard;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.utils.TextFormat;
import org.repliedk.Main;
import org.repliedk.api.scoreboard.Scoreboard;
import org.repliedk.session.PlayerSession;
import org.repliedk.session.SessionFactory;

import java.util.ArrayList;

public class ScoreboardBuilder implements Runnable {

    public Scoreboard scoreboard = new Scoreboard();

    public ScoreboardBuilder(){
        (new Thread(this)).start();
    }

    @Override
    public void run() {
        while(Main.getMain().isEnabled()){
            for (Player player : Server.getInstance().getOnlinePlayers().values()) {
                if (!player.isOnline() || !player.spawned) {
                    continue;
                }
                PlayerSession session = SessionFactory.get(player.getName());
                if (session == null) {
                    continue;
                }
                this.build(session);
            }
        }
    }

    public void build(PlayerSession session) {
        ArrayList<String> lines = new ArrayList<>();

        scoreboard.setDisplayName(TextFormat.colorize("&aBeautiful Messages"));
        scoreboard.removeLines();

        lines.add("&r&7--------------------- ");
        lines.add("You Are Amazing");
        lines.add("You Are Loved");
        lines.add("Shine Bright Like a Diamond");
        lines.add("&7&7--------------------- ");

        for(int i = 0; i < lines.size(); i++){
            scoreboard.setLine(i + 1, lines.get(i));
        }

        scoreboard.show(session.getPlayer());
    }

}