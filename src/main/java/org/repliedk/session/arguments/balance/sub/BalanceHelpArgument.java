package org.repliedk.session.arguments.balance.sub;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import org.repliedk.arguments.SubArgument;

public class BalanceHelpArgument extends SubArgument {

    public BalanceHelpArgument() {
        super("help");
    }

    @Override
    public String[] getAliases() {
        return new String[]{"info"};
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        message(sender, "&l&9Beautiful Messages&r:");
        message(sender, " ");
        message(sender, "&b- &e/balance (View your balance)");
        message(sender, "&b- &e/balance top (View top balances)");
        if (sender.isOp()) {
            message(sender, "&b- &e/balance take <player> <amount> (Take money from a player)");
            message(sender, "&b- &e/balance give <player> <amount> (Give money from a player)");
            message(sender, "&b- &e/balance clear <player> (Clear a player's balance)");
        }
        message(sender, " ");

        return false;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[0];
    }
}