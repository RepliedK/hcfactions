package org.repliedk.team.argument.sub.coleader;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import org.repliedk.arguments.SubArgument;
import org.repliedk.session.PlayerSession;
import org.repliedk.session.SessionFactory;

public class TeamSetHQArgument extends SubArgument {

    public TeamSetHQArgument() {
        super("sethome");
    }

    @Override
    public String[] getAliases() {
        return new String[] {"sethq"};
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

        if (!player.getTeam().isLeader(player) && !player.getTeam().isColeader(player)) {
            message(sender, "&cNeed leader or coleader!");
            return true;
        }

        //need command in your claim

        if (!sender.getLocation().getLevelName().equalsIgnoreCase("world")) {
            message(sender, "&cUsage this command in over world!");
            return true;
        }

        player.getTeam().setHome(sender.getPosition());
        message(sender, "&cYou have updated the home!");
        player.getTeam().sendAnnounce("&f" + player.getName() + "&e has updated the headquarters.");

        return false;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[0];
    }
}