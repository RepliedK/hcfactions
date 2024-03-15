package org.repliedk.arguments.enums;

import cn.nukkit.command.data.CommandEnum;
import org.repliedk.Main;

public class ArgumentEnums {
    public static CommandEnum TEAM_LIST = new CommandEnum("teams", () -> Main.getMain().getTeamFactory().getTeams().keySet());

    public static CommandEnum SESSION_LIST = new CommandEnum("sessions", () -> Main.getMain().getSessionFactory().getSessions().keySet());

}