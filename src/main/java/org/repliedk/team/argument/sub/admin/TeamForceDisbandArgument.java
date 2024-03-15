package org.repliedk.team.argument.sub.admin;

import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import org.repliedk.arguments.SubArgument;
import org.repliedk.arguments.enums.ArgumentEnums;
import org.repliedk.team.Team;
import org.repliedk.team.TeamFactory;

public class TeamForceDisbandArgument extends SubArgument {

    public TeamForceDisbandArgument() {
        super("forcedisband");
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        if (args.length < 2) {
            message(sender, "&cUsage: /team forcedisband <team>");
            return true;
        }

        Team team = TeamFactory.get(args[1]);

        if (team == null) {
            message(sender, "&cThis team does not exist!");
            return true;
        }

        Server.getInstance().broadcastMessage(team.getName() + " has been disbanded by " + sender.getName());
        team.disbandTeam();

        return false;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[]{
                CommandParameter.newEnum("team", ArgumentEnums.TEAM_LIST)
        };
    }


}