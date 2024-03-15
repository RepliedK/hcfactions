package org.repliedk.team.argument.sub;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import org.repliedk.Main;
import org.repliedk.arguments.SubArgument;
import org.repliedk.session.PlayerSession;
import org.repliedk.session.SessionFactory;
import org.repliedk.team.Team;
import org.repliedk.team.TeamFactory;

import java.util.regex.Pattern;

public class TeamCreateArgument extends SubArgument {

    public TeamCreateArgument() {
        super("create");
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        if (!sender.isPlayer()) {
            message(sender, "&cYou are not a player!");
            return true;
        }

        PlayerSession player = SessionFactory.get(sender.getName());

        assert player != null;
        if (player.hasTeam()) {
            message(sender, "&cYou are already in a team!");
            return true;
        }

        if (args.length < 2) {
            message(sender, "&cUsage: /team create <name>");
            return true;
        }

        String name = args[1];

        if (isNotAlphanumeric(name)) {
            message(sender, "&cThe team name is invalid!");
            return true;
        }

        Team check = TeamFactory.get(name);

        if (check != null) {
            message(sender, "&cTeam &e" + check.getName() + "&c already exists!");
            return true;
        }

        if (name.length() < 5) {
            message(sender, "&cMinimum name is 5 characters!");
            return true;
        }

        if (name.length() > 10) {
            message(sender, "&cMaximum name is 10 characters!");
            return true;
        }

        TeamFactory teamFactory = Main.getMain().getTeamFactory();

        teamFactory.addTeam(name, player);
        sender.getServer().broadcastMessage(TextFormat.colorize("&eTeam &9" + name + "&e has been created by &f" + player.getName()));

        return false;
    }

    public static boolean isNotAlphanumeric(String input) {
        return Pattern.compile("[^a-zA-Z0-9]").matcher(input).find();
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[] {
            CommandParameter.newEnum("name", CommandParameter.ARG_TYPE_STRING)
        };
    }
}