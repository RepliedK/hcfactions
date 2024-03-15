package org.repliedk.session.arguments.balance.sub;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import org.repliedk.arguments.SubArgument;
import org.repliedk.arguments.enums.ArgumentEnums;
import org.repliedk.session.PlayerSession;
import org.repliedk.session.SessionFactory;
import org.repliedk.utils.MainUtil;

public class BalanceTakeArgument extends SubArgument {

    public BalanceTakeArgument() {
        super("take");
    }

    @Override
    public String[] getAliases() {
        return new String[]{"remove"};
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        if (!sender.isOp()) {
            message(sender, "&cYou don't have permission to use this command.");
            return true;
        }

        if (args.length < 3) {
            message(sender, "&cUsage: /balance remove <player> <amount>");
            return true;
        }

        PlayerSession target = SessionFactory.get(args[1]);

        if (target == null) {
            message(sender, "&cSession &e" + args[1] + " &cno have found!");
            return true;
        }

        if (!MainUtil.isNumeric(args[2])) {
            message(sender, "&cPlease put, int number for amount!");
            return true;
        }

        int amount = Integer.parseInt(args[2]);

        int check = amount - target.getBalance();

        if (check < 0) {
            message(sender, "&cPlayer does not have enough balance.");
            message(sender, "&cPlayer's balance: &e$" + target.getBalance() + "&c.");
            return true;
        }

        target.setBalance(target.getBalance() - amount);

        if (sender.isPlayer() && target.equals(SessionFactory.get(sender.getName()))) {
            message(sender, "&eYou have removed &d$" + amount + "&e from your balance.");
            message(sender, "&eYour Current Balance: &d$" + SessionFactory.get(sender.getName()).getBalance() + "&e.");
        } else {
            message(sender, "&eRemoved &d$" + amount + "&e from player: &f" + target.getName() + "&e.");
            target.sendMessage("&eYou have lost &d$" + amount + "&e. Contact staff for details.");
            target.sendMessage("&eCurrent balance: &d$" + target.getBalance());
        }

        return false;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[] {
                CommandParameter.newEnum("player", ArgumentEnums.SESSION_LIST),
                CommandParameter.newEnum("amount", CommandParameter.ARG_TYPE_INT)
        };
    }
}