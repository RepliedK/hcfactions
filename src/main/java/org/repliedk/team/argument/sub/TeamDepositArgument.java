package org.repliedk.team.argument.sub;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import org.repliedk.arguments.SubArgument;
import org.repliedk.session.PlayerSession;
import org.repliedk.session.SessionFactory;
import org.repliedk.utils.MainUtil;

public class TeamDepositArgument extends SubArgument {

    public TeamDepositArgument() {
        super("deposit");
    }

    @Override
    public String[] getAliases() {
        return new String[] {"d"};
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!sender.isPlayer()) return true;

        PlayerSession player = SessionFactory.get(sender.getName());

        assert player != null;
        if (!player.hasTeam()) {
            message(sender, "&cYou need a team!");
            return true;
        }

        if (args.length < 2) {
            message(sender, "&cUsage: /team deposit <amount or all>");
            return true;
        }

        if (args[1].equalsIgnoreCase("all")) {

            if (player.getBalance() < 1) {
                message(sender, "&cYou don't have enough balance!");
                return true;
            }

            player.getTeam().setBalance(player.getTeam().getBalance() + player.getBalance());

            message(sender, "&aYou have deposited money!");
            player.getTeam().sendAnnounce("&f" + player.getName() + "&e has deposited &d$" + player.getBalance() + "&e to the team!");
            player.setBalance(0);

        } else {

            if (!MainUtil.isNumeric(args[1])) {
                message(sender, "&cPlease enter a valid integer number!");
                return true;
            }

            int amount = Integer.parseInt(args[1]);

            if (player.getBalance() - amount < 1) {
                message(sender, "&cYou don't have enough balance for this amount!");
                return true;
            }

            message(sender, "&aYou have deposited money!");
            player.getTeam().sendAnnounce("&f" + player.getName() + "&e has deposited &d$" + amount + "&e to the team!");
            player.setBalance(player.getBalance() - amount);
            player.getTeam().setBalance(player.getTeam().getBalance() + amount);

        }

        return false;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[0];
    }
}