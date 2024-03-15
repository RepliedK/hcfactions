package org.repliedk.team.argument.sub;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import org.repliedk.Main;
import org.repliedk.arguments.SubArgument;
import org.repliedk.team.Team;
import org.repliedk.utils.MainUtil;

import java.util.Map;

public class TeamListArgument extends SubArgument {

    public TeamListArgument() {
        super("list");
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        Map<String, Team> teamsMap = Main.getMain().getTeamFactory().getTeams();

        if (teamsMap.isEmpty()) {
            message(sender, "&cNo teams have been created!");
            return true;
        }

        int page;
        int teamsPerPage = 8;

        if (args.length < 2) {
            page = 1;
        } else {
            if (!MainUtil.isNumeric(args[1])) {
                message(sender, "&cPlease input a number!");
                return true;
            }

            page = Integer.parseInt(args[1]);
        }

        int totalPages = (int) Math.ceil((double) teamsMap.size() / teamsPerPage);

        if (page < 1 || page > totalPages) {
            message(sender, "&cThis page does not exist. Page range: 1-" + totalPages);
            return true;
        }

        int startIndex = (page - 1) * teamsPerPage;
        int endIndex = Math.min(startIndex + teamsPerPage, teamsMap.size());

        message(sender, "&6--- Team List (Page " + page + ") ---");
        int count = 0;
        for (Map.Entry<String, Team> entry : teamsMap.entrySet()) {
            count++;
            if (count > startIndex && count <= endIndex) {
                Team team = entry.getValue();
                message(sender, "&e" + team.getName() + " &7- &aLeader: " + team.getLeader().getName() +
                        " &7- &bMembers: " + team.getMembers().size());
            }
        }

        return true;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[] {
                CommandParameter.newEnum("page", CommandParameter.ARG_TYPE_INT)
        };
    }
}