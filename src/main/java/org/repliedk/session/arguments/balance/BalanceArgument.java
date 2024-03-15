package org.repliedk.session.arguments.balance;

import cn.nukkit.command.CommandSender;
import org.repliedk.arguments.Argument;
import org.repliedk.session.SessionFactory;
import org.repliedk.session.arguments.balance.sub.*;

public class BalanceArgument extends Argument {

    public BalanceArgument() {
        super("balance", "balance arguments");

        addSubArgument(new BalanceTopArgument());
        addSubArgument(new BalanceHelpArgument());
        addSubArgument(new BalanceGiveArgument());
        addSubArgument(new BalanceClearArgument());
        addSubArgument(new BalanceTakeArgument());
    }

    @Override
    public void executeArgument(CommandSender sender) {
        if (sender.isPlayer()) {

            if (sender.isOp()) {
                message(sender, "&aYou usage /balance help (for more)");
            }

            message(sender, "&eYour account balance stands at &d$" + SessionFactory.get(sender.getName()).getBalance() + "&e.");
        } else {
            message(sender, getUsage());
            message(sender, "check x");
        }
    }

    @Override
    public String[] getAliases() {
        return new String[]{"money"};
    }

    @Override
    public String getUsage() {
        return "&cUsage: /balance help";
    }
}
