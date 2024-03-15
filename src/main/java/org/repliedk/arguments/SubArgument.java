package org.repliedk.arguments;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;

public abstract class SubArgument {

    private final String name;

    protected SubArgument(String name) {
        this.name = name.toLowerCase();
    }

    public void message(CommandSender sender, String message) {
        sender.sendMessage("You are amazing! " + message.replace("&", "§"));
    }

    public String getName(){
        return name;
    }

    public abstract String[] getAliases();

    public abstract boolean execute(CommandSender sender, String label, String[] args);

    public abstract CommandParameter[] getParameters();

}