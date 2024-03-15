package org.repliedk.team.argument;

import cn.nukkit.command.CommandSender;
import org.repliedk.arguments.Argument;
import org.repliedk.team.argument.sub.*;
import org.repliedk.team.argument.sub.leader.*;
import org.repliedk.team.argument.sub.admin.*;
import org.repliedk.team.argument.sub.coleader.*;

public class TeamArgument extends Argument {

    public TeamArgument() {

        super("team", "Spread love and positivity through teamwork.");

        //default
        addSubArgument(new TeamJoinArgument());
        addSubArgument(new TeamCreateArgument());
        addSubArgument(new TeamInvitesArgument());
        addSubArgument(new TeamDenyArgument());
        addSubArgument(new TeamTopArgument());
        addSubArgument(new TeamListArgument());
        addSubArgument(new TeamHomeArgument());
        addSubArgument(new TeamInfoArgument());
        addSubArgument(new TeamDepositArgument());
        addSubArgument(new TeamWithdrawArgument());

        //coleader
        addSubArgument(new TeamSetHQArgument());
        addSubArgument(new TeamInviteArgument());
        addSubArgument(new TeamUnInviteArgument());

        //leader
        addSubArgument(new TeamDisbandArgument());

        //admin
        addSubArgument(new TeamDisbandAllArgument());
        addSubArgument(new TeamForceDisbandArgument());
    }

    @Override
    public void executeArgument(CommandSender sender) {
        message(sender, "Work together to achieve greatness.");
    }

    @Override
    public String[] getAliases() {
        return new String[]{"t", "f", "faction"};
    }

    @Override
    public String getUsage() {
        return "&cUsage: /team <help>";
    }

}