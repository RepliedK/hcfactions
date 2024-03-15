package org.repliedk;

import cn.nukkit.plugin.PluginBase;
import lombok.Getter;
import lombok.Setter;
import me.iwareq.fakeinventories.FakeInventories;

import org.repliedk.listener.ClaimListener;
import org.repliedk.listener.MainListener;
import org.repliedk.listener.SessionListener;
import org.repliedk.listener.TeamListener;
import org.repliedk.scoreboard.ScoreboardBuilder;
import org.repliedk.session.PlayerSession;
import org.repliedk.session.SessionFactory;
import org.repliedk.session.arguments.balance.BalanceArgument;
import org.repliedk.session.arguments.repair.RepairArgument;
import org.repliedk.team.Team;
import org.repliedk.team.TeamFactory;
import org.repliedk.team.argument.TeamArgument;

@Getter 
@Setter
public class Main extends PluginBase {

    @Getter 
    public static Main main = new Main();

    public TeamFactory teamFactory = new TeamFactory(this);

    public SessionFactory sessionFactory = new SessionFactory(this);

    @Override
    public void onLoad() {
        FakeInventories.load();
        getLogger().info("Loading the plugin with love and excitement.");
    }

    @Override
    public void onEnable() {
        getLogger().info("The plugin is now spreading positivity and joy!");

        //Commands
        getServer().getCommandMap().register("team", new TeamArgument());
        getServer().getCommandMap().register("balance", new BalanceArgument());
        getServer().getCommandMap().register("repair", new RepairArgument());

        //Listeners
        getServer().getPluginManager().registerEvents(new SessionListener(), this);
        getServer().getPluginManager().registerEvents(new TeamListener(), this);
        getServer().getPluginManager().registerEvents(new MainListener(), this);
        getServer().getPluginManager().registerEvents(new ClaimListener(), this);

        //Server Task
        getServer().getScheduler().scheduleRepeatingTask(this, () -> {
            for (PlayerSession session : getSessionFactory().getSessions().values()) {
                session.onUpdate();
            }

            for (Team team : getTeamFactory().getTeams().values()) {
                team.onUpdate();
            }

        }, 20);

        //Scoreboard System
        new ScoreboardBuilder();
    }

    @Override
    public void onDisable() {
        getLogger().info("The plugin is gracefully bidding farewell.");

        if (getTeamFactory() != null) {
            getTeamFactory().onDisable();
        }
        if (getSessionFactory() != null) {
            getSessionFactory().onDisable();
        }
    }

}