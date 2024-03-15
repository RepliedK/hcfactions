package org.repliedk.session.arguments.repair;

import cn.nukkit.command.CommandSender;
import org.repliedk.arguments.Argument;
import org.repliedk.session.arguments.repair.sub.RepairAllArgument;
import org.repliedk.session.arguments.repair.sub.RepairHandArgument;

public class RepairArgument extends Argument {

    public RepairArgument() {
        super("repair", "Repair arguments with love and care");

        addSubArgument(new RepairHandArgument());
        addSubArgument(new RepairAllArgument());
    }

    @Override
    public String[] getAliases() {
        return new String[]{"fix"};
    }

    @Override
    public void executeArgument(CommandSender sender) {
        message(sender, getUsage());
    }

    @Override
    public String getUsage() {
        return "&cUsage: Spread love and use /fix all|hand to repair";
    }

}
