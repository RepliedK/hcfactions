package org.repliedk.team.argument.sub.coleader;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import org.repliedk.arguments.SubArgument;
import org.repliedk.session.PlayerSession;
import org.repliedk.session.SessionFactory;
import org.repliedk.utils.MainUtil;

public class TeamWithdrawArgument extends SubArgument {

    public TeamWithdrawArgument() {
        super("withdraw");
    }

    @Override
    public String[] getAliases() {
        return new String[] {"w"};
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!sender.isPlayer()) return true;

        PlayerSession player = SessionFactory.get(sender.getName());

        assert player != null;
        if (!player.hasTeam()) {
            message(sender, "&cYou need team!");
            return true;
        }

        if (!player.getTeam().isLeader(player) && !player.getTeam().isColeader(player)) {
            message(sender, "&cYou need leader or coleader role!");
            return true;
        }

        if (args.length < 2) {
            message(sender, "&cUsage: /team withdraw <amount or all>");
            return true;
        }

        if (args[1].equalsIgnoreCase("all")) {

            if (player.getTeam().getBalance() < 1) {
                message(sender, "&cYour team dont have balance!");
                return true;
            }

            message(sender, "&aYou have withdraw money!");
            player.getTeam().sendAnnounce("&f" + player.getName() + "&e has been withdraw &d$" + player.getTeam().getBalance() + "&e to team!");

            player.setBalance(player.getBalance() + player.getTeam().getBalance());
            player.getTeam().setBalance(0);

        } else {

            if (!MainUtil.isNumeric(args[1])) {
                message(sender, "&cPut int number please!");
                return true;
            }

            int amount = Integer.parseInt(args[1]);

            if (player.getTeam().getBalance() - amount < 1) {
                message(sender, "&cYour team dont have this amount balance!");
                return true;
            }

            message(sender, "&aYou have withdraw money!");
            player.getTeam().sendAnnounce("&f" + player.getName() + "&e has been withdraw &d$" + amount + "&e to team!");
            player.setBalance(player.getBalance() + amount);
            player.getTeam().setBalance(player.getTeam().getBalance() - amount);

        }

        return false;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[0];
    }
}
