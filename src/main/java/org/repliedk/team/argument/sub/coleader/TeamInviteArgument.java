package org.repliedk.team.argument.sub.coleader;

import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import org.repliedk.arguments.SubArgument;
import org.repliedk.session.PlayerSession;
import org.repliedk.session.SessionFactory;

public class TeamInviteArgument extends SubArgument {

    public TeamInviteArgument() {
        super("invite");
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        if (!sender.isPlayer()) return true;

        PlayerSession player = SessionFactory.get(sender.getName());

        assert player != null;
        if (!player.hasTeam()) {
            message(sender, "&cYou dont have team!");
            return true;
        }

        if (!player.getTeam().isLeader(player) && !player.getTeam().isColeader(player)) {
            message(sender, "&cYou need leader or coleader!");
            return true;
        }

        if (args.length < 2) {
            message(sender, "&cUsage: /team invite <player>");
            return true;
        }

        if (Server.getInstance().getPlayer(args[1]) == null) {
            message(sender, "&cPlayer doent found!");
            return true;
        }

        PlayerSession target = SessionFactory.get(Server.getInstance().getPlayer(args[1]).getName());

        assert target != null;
        if (target.hasTeam()) {
            message(sender, "&cThis player already have in team!");
            return true;
        }

        if (target.getInvite(player.getTeam().getName()) != null) {
            message(sender, "&cThis player already invite to your team!");
            return true;
        }

        message(sender, "&eYou have invited a player, &d" + target.getName() + "&e.");
        player.getTeam().sendAnnounce("&ePlayer &d" + target.getName() + "&e has been invited by &f" + player.getName() + "&e.");
        target.addInvite(target.getTeam(), 360);
        target.sendMessage("&eYou have been invited to the team &9" + player.getTeam().getName() + "&e by &f" + player.getName() + "&e.");
        target.sendMessage("&b- &e'Usage: /team join " + player.getTeam().getName() + "' to join!");


        return false;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[] {
                CommandParameter.newType("player", true, CommandParamType.TARGET)
        };
    }
}