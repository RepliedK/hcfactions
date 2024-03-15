// Beautiful English messages
package org.repliedk.team.argument.sub;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import org.repliedk.Main;
import org.repliedk.arguments.SubArgument;
import org.repliedk.session.PlayerSession;
import org.repliedk.session.SessionFactory;
import org.repliedk.team.Team;

import java.util.Comparator;
import java.util.List;

public class TeamTopArgument extends SubArgument {

    public TeamTopArgument() {
        super("top");
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        PlayerSession session = null;

        if (sender.isPlayer() && SessionFactory.get(sender.getName()) != null) {
            session = SessionFactory.get(sender.getName());
        }

        message(sender, "&l&9TEAM's TOP&r:");
        message(sender, "&7---------------------------");

        for (int i = 1; i <= 10; i++) {
            Team teams = getPos(i);
            if (teams != null) {

                String color;

                color = "&f";

                if (session != null && session.getTeam().equals(teams)) {
                    color = "&d";
                }

                message(sender, "&o&i#" + i + " &r" + color + teams.getName() + ": &b- &e" + teams.getPoints());
            } else {
                message(sender, "&o&i#" + i + " &r&cNone");
            }
        }

        if (session != null && session.hasTeam() && getPosTeam(session.getTeam()) > 10) {
            message(sender, "&eYour team position: &9#" + getPosTeam(session.getTeam()));
        }

        message(sender, "&7---------------------------");


        return false;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[0];
    }

    public int getPosTeam(Team team) {
        List<Team> teamPos = Main.getMain().getTeamFactory().getTeams().values().stream()
                .filter(teams -> teams.getPoints() > -1000)
                .sorted(Comparator.comparingInt(Team::getPoints).reversed())
                .toList();

        return teamPos.indexOf(team) + 1;
    }

    public Team getPos(int pos) {
        List<Team> teamPos = Main.getMain().getTeamFactory().getTeams().values().stream()
                .filter(team -> team.getPoints() > -1000)
                .sorted(Comparator.comparingInt(Team::getPoints).reversed())
                .toList();

        return (pos >= 1 && pos <= teamPos.size()) ? teamPos.get(pos - 1) : null;
    }
}