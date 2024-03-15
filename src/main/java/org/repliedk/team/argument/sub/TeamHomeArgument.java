package org.repliedk.team.argument.sub;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import org.repliedk.arguments.SubArgument;
import org.repliedk.session.PlayerSession;
import org.repliedk.session.SessionFactory;

public class TeamHomeArgument extends SubArgument {

    public TeamHomeArgument() {
        super("home");
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
            message(sender, "&cNeed team!");
            return true;
        }

        if (player.getTeam().getHome() == null) {
            message(sender, "&cYour team does not have a home!");
            return true;
        }

        player.getPlayer().teleport(player.getTeam().getHome());

        message(sender, "&cYou have teleported to your team's home!");

        return false;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[0];
    }
}