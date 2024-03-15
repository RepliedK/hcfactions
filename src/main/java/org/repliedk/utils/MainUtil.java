package org.repliedk.utils;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.data.CommandDataVersions;
import cn.nukkit.network.protocol.AvailableCommandsPacket;
import lombok.Getter;
import org.repliedk.arguments.Argument;

import java.util.HashMap;
import java.util.Map;

@Getter
public class MainUtil {

    public static String PublicChat = "public", TeamChat = "team";

    public static String DEFAULT_PERMISSION = "default.permission";

    public static boolean isNumeric(Object type) {
        if (type instanceof Number || type instanceof Integer) {
            return true;
        }
        if (type instanceof String) {
            String str = (String) type;

            try {
                Integer.parseInt(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return false;
    }

    public static void reloadCommandData() {

        for (Player player : Server.getInstance().getOnlinePlayers().values()) {

            AvailableCommandsPacket pk = new AvailableCommandsPacket();
            Map<String, CommandDataVersions> data = new HashMap<>();

            for (Command command : player.getServer().getCommandMap().getCommands().values()) {

                if (command instanceof Argument) {

                    if (!command.testPermissionSilent(player) || !command.isRegistered()) {
                        continue;
                    }

                    data.put(command.getName(), command.generateCustomCommandData(player));
                }
            }

            if (!data.isEmpty()) {
                pk.commands = data;
                player.dataPacket(pk);
            }
        }
    }

}