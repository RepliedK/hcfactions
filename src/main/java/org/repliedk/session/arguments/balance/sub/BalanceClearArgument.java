package org.repliedk.session.arguments.balance.sub;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import org.repliedk.arguments.SubArgument;
import org.repliedk.arguments.enums.ArgumentEnums;
import org.repliedk.session.PlayerSession;
import org.repliedk.session.SessionFactory;

public class BalanceClearArgument extends SubArgument {

    public BalanceClearArgument() {
        super("clear");
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        if (!sender.isOp()) {
            message(sender, "&cYou don't have permission to use this command.");
            return true;
        }

        if (args.length < 2) {
            message(sender, "&cUsage: /balance clear <player>");
            return true;
        }

        PlayerSession target = SessionFactory.get(args[1]);

        if (target == null) {
            message(sender, "&cSession &e" + args[1] + " &cno have found!");
            return true;
        }

        if (sender.isPlayer() && target.equals(SessionFactory.get(sender.getName()))) {
            message(sender, "&dYou have cleared your balance.");
        } else {
            message(sender, "&eBalance cleared to player: &f" + target.getName() + "&e.");
            target.setBalance(0);
            target.sendMessage("&eYour balance has been cleared by staff &e" + sender.getName() + "&e.");
        }

        return false;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[] {
                CommandParameter.newEnum("player", ArgumentEnums.SESSION_LIST)
        };
    }
}