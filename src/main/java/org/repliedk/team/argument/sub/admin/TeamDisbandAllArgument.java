package org.repliedk.team.argument.sub.admin;

import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import org.repliedk.Main;
import org.repliedk.arguments.SubArgument;
import org.repliedk.team.Team;

public class TeamDisbandAllArgument extends SubArgument {

    public TeamDisbandAllArgument() {
        super("disbandall");
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        if (Main.getMain().getTeamFactory().getTeams().isEmpty()) {
            message(sender, "&cNone exist teams!");
            return true;
        }

        for (Team teams : Main.getMain().getTeamFactory().getTeams().values()) {
            teams.disbandTeam();
        }

        Server.getInstance().broadcastMessage("All teams have been disbanded by " + sender.getName() + " with love and kindness.");

        return false;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[0];
    }
}