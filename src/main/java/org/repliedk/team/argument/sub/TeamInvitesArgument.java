package org.repliedk.team.argument.sub;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import org.repliedk.arguments.SubArgument;
import org.repliedk.session.PlayerSession;
import org.repliedk.session.SessionFactory;


public class TeamInvitesArgument extends SubArgument {

    public TeamInvitesArgument() {
        super("invites");
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
        if (player.hasTeam()) {
            message(sender, "&cYou can't use this command, as you already belong to a team!");
            return true;
        }

        String invites = player.getInvites().isEmpty() ? "&cNONE" : String.join("&f, ", player.getInvites().keySet());

        message(sender, "&l&9TEAM INVITE's&r: &f" + invites);

        return false;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[0];
    }
}