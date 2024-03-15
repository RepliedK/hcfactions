package org.repliedk.team.argument.sub.leader;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import org.repliedk.arguments.SubArgument;
import org.repliedk.session.PlayerSession;
import org.repliedk.session.SessionFactory;

public class TeamDisbandArgument extends SubArgument {

    public TeamDisbandArgument() {
        super("disband");
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        if (!sender.isPlayer()) {
            return true;
        }

        PlayerSession player = SessionFactory.get(sender.getName());

        assert player != null;
        if (!player.hasTeam()) {
            message(sender, "&eYou don't have a team!");
            return true;
        }

        if (!player.getTeam().isLeader(player)) {
            message(sender, "&eYou are not the leader of the team!");
            return true;
        }

        sender.getServer().broadcastMessage(TextFormat.colorize("&eTeam &9" + player.getTeam().getName() + " &ehas been disbanded by &f" + player.getName()));
        player.getTeam().disbandTeam();

        return false;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[0];
    }
}