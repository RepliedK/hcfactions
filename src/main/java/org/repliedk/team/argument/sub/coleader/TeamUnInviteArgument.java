package org.repliedk.team.argument.sub.coleader;

import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import org.repliedk.arguments.SubArgument;
import org.repliedk.session.PlayerSession;
import org.repliedk.session.SessionFactory;

public class TeamUnInviteArgument extends SubArgument {

    public TeamUnInviteArgument() {
        super("uninvite");
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
            message(sender, "&cYou need, coleader or leader role!");
            return true;
        }

        if (args.length < 2) {
            message(sender, "&cUsage: /team uninvite <player>");
            return true;
        }

        if (Server.getInstance().getPlayer(args[1]) == null) {
            message(sender, "&cPlayer no have connected!");
            return true;
        }

        PlayerSession target = SessionFactory.get(Server.getInstance().getPlayer(args[1]).getName());

        assert target != null;
        if (target.getInvite(player.getTeam().getName()) == null) {
            message(sender, "&eEste jugador no tiene una invite de tu team!");
            return true;
        }

        target.sendMessage("&eHan removido la invite que tenias del team: " + player.getName());
        message(sender, "&cYou have removed this invited.");
        player.getTeam().sendAnnounce("&cInvite " + target.getName() + " has been remoev by " + player.getName() + ".");

        return false;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[] {
                CommandParameter.newType("player", true, CommandParamType.TARGET)
        };
    }
}