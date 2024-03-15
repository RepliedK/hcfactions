package org.repliedk.team.argument.sub;

import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import org.repliedk.arguments.SubArgument;
import org.repliedk.arguments.enums.ArgumentEnums;
import org.repliedk.session.PlayerSession;
import org.repliedk.session.SessionFactory;
import org.repliedk.team.Team;
import org.repliedk.team.TeamFactory;
import org.repliedk.team.TeamUtils;

import java.util.stream.Collectors;

public class TeamInfoArgument extends SubArgument {

    public TeamInfoArgument() {
        super("info");
    }

    @Override
    public String[] getAliases() {
        return new String[] {"who",};
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        PlayerSession player = null;

        if (args.length < 2) {
            if (sender.isPlayer()) {
                player = SessionFactory.get(sender.getName());

                assert player != null;
                if (!player.hasTeam()) {
                    message(sender, "&cYou can't have a team, Usage: /team who <team>");
                    return true;
                }

                sendInformation(sender, player.getTeam());
                return true;
            } else {
                message(sender, "&cUsage: /team who <team>");
                return true;
            }
        }

        String name = args[1];

        if (Server.getInstance().getPlayer(name) != null) {
            PlayerSession target = SessionFactory.get(Server.getInstance().getPlayer(name).getName());

            assert target != null;

            if (!target.hasTeam()) {
                message(sender, "&cThis player &e" + target.getName() + " &cdoesn't have a team!");
                return true;
            }

            sendInformation(sender, target.getTeam());
        } else {

            Team team = TeamFactory.get(name);
            if (team == null) {
                message(sender, "&cThis team doesn't exist!");
                return true;
            }

            sendInformation(sender, team);
        }

        return false;
    }

    public void sendInformation(CommandSender sender, Team team) {

        String coleaders = !team.getColeaders().isEmpty() ? team.getColeaders().stream().map(this::convertMember).collect(Collectors.joining(", ")) : "&fno-bodies";

        String members = !team.getMembers().isEmpty() ? team.getMembers().stream().map(this::convertMember).collect(Collectors.joining(", ")) : "&fno-bodies";

        message(sender, "&7&m--------------------------");
        message(sender, "&9" + team.getName() + " &7[" + team.getOnlinePlayers().size() + "/" + TeamUtils.maxMembers + "] &3- &eHQ: " + team.homeToString());
        message(sender, "&eLeader: " + convertMember(team.getLeader()));
        message(sender, "&eColeaders: " + coleaders);
        message(sender, "&eMembers: " + members);
        message(sender, "&eBalance: &9$" + team.getBalance() + " &7| &ePoints &c" + team.getPoints());

        String type = null;
        if (team.isRegeneration()) {
            type = "&eRegeneration Time: &9" + team.getRegenerationTime();
        }
        if (team.isFreeze()) {
            type = "&eFreeze Time: &9" + team.getFreezeTime();
        }
        if (type != null) {
            message(sender, type);
        }

        String raideable = team.getDtr() < 1 ? "Yes" : "None";

        message(sender, "&eDTR: &9" + team.getDtr() + "/" + team.getMaxDtr() + " &c| &eRaideable: &f" + raideable);
        message(sender, "&7&m--------------------------");
    }

    public String convertMember(PlayerSession member) {
        if (member == null) {
            return "&fno-body";
        }
        String text = member.getName() + "&e[&c" + member.getKills() + "&e]";
        return member.isOnline() ? "&a" + text : "&7" + member.getName() + text;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[] {
                CommandParameter.newEnum("team", ArgumentEnums.TEAM_LIST)
        };
    }
}
