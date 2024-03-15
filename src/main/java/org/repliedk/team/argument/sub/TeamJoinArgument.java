package org.repliedk.team.argument.sub;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import org.repliedk.arguments.SubArgument;
import org.repliedk.arguments.enums.ArgumentEnums;
import org.repliedk.session.PlayerSession;
import org.repliedk.session.SessionFactory;
import org.repliedk.team.Team;
import org.repliedk.team.TeamFactory;

public class TeamJoinArgument extends SubArgument {

    public TeamJoinArgument() {
        super("join");
    }

    @Override
    public String[] getAliases() {
        return new String[] {"accept"};
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        if (!sender.isPlayer()) {
            message(sender, "&cYou are not a player!");
            return true;
        }

        PlayerSession player = SessionFactory.get(sender.getName());

        assert player != null;
        if (player.hasTeam()) {
            message(sender, "&cYou are already in a team!");
            return true;
        }

        if (args.length < 2) {
            message(sender, "&cUsage: /team join <team>");
            return true;
        }

        Team team = TeamFactory.get(args[1]);

        if (team == null || player.getInvite(team.getName()) == null) {
            message(sender, "&cYou do not have this invite!");
            return true;
        }

        player.getInvite(team.getName()).getTeam().addMember(player);
        player.getInvite(team.getName()).getTeam().sendAnnounce("&a" + player.getName() + "&a has joined!");
        player.removeInvite(team.getName());

        return false;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[] {
                CommandParameter.newEnum("team", ArgumentEnums.TEAM_LIST)
        };
    }
}