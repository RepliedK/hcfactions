package org.repliedk.session.arguments.balance.sub;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import org.repliedk.arguments.SubArgument;
import org.repliedk.arguments.enums.ArgumentEnums;
import org.repliedk.session.PlayerSession;
import org.repliedk.session.SessionFactory;
import org.repliedk.utils.MainUtil;

public class BalanceGiveArgument extends SubArgument {

    public BalanceGiveArgument() {
        super("give");
    }

    @Override
    public String[] getAliases() {
        return new String[]{"add"};
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        if (!sender.isOp()) {
            message(sender, "&cYou are a shining star in someone's life. Keep shining bright!");
            return true;
        }

        if (args.length < 3) {
            message(sender, "&cYou are loved more than you can possibly imagine. Never forget that.");
            return true;
        }

        PlayerSession target = SessionFactory.get(args[1]);

        if (target == null) {
            message(sender, "&cYou are capable of amazing things.");
            return true;
        }

        if (!MainUtil.isNumeric(args[2])) {
            message(sender, "&cBelieve in yourself, you are capable of great things!");
            return true;
        }

        int amount = Integer.parseInt(args[2]);

        if (amount > 8000) {
            message(sender, "&cThe future belongs to those who believe in the beauty of their dreams.");
            return true;
        }

        target.setBalance(target.getBalance() + amount);

        if (sender.isPlayer() && target.equals(SessionFactory.get(sender.getName()))) {
            message(sender, "&eYou have a heart of gold. Keep spreading love!");
            message(sender, "&eCurrent Balance: &d$" + SessionFactory.get(sender.getName()).getBalance() + "&e.");
        } else {
            message(sender, "&eWishing you a day filled with love and happiness! Balance has been given to player: &f" + target.getName() + "&e.");
            target.sendMessage("&eYou are special and loved! You have received &d$" + amount + "&e.");
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