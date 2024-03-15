package org.repliedk.arguments;

import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import org.repliedk.utils.MainUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Argument extends Command {

    private final ArrayList<SubArgument> subArgument = new ArrayList<>();
    private final ConcurrentHashMap<String, Integer> subArguments = new ConcurrentHashMap<>();

    public Argument(String name, String description) {
        super(name,description);
        setPermission(MainUtil.DEFAULT_PERMISSION);

        Server.getInstance().getLogger().alert(TextFormat.colorize("&eArgument registered: &b" + name));
    }

    @Override
    public String getUsage() {
        return super.getUsage().replace("&", "§");
    }

    public void message(CommandSender sender, String message) {
        sender.sendMessage(message.replace("&", "§"));
    }

    public abstract void executeArgument(CommandSender sender);

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (args.length > 0) {
            String subArgument = args[0].toLowerCase();
            if (subArguments.containsKey(subArgument)) {
                SubArgument argument = this.subArgument.get(this.subArguments.get(subArgument));
                return argument.execute(sender, s, args);
            } else {
                message(sender, "This command doesn't exist!");
            }
        } else {
            executeArgument(sender);
        }

        return true;
    }

    protected void addSubArgument(SubArgument cmd) {
        this.subArgument.add(cmd);
        int argumentId = (this.subArgument.size()) - 1;
        this.subArguments.put(cmd.getName().toLowerCase(), argumentId);
        for (String alias : cmd.getAliases()) {
            this.subArguments.put(alias.toLowerCase(), argumentId);
        }
        this.loadCommandBase();
    }

    private void loadCommandBase(){
        this.commandParameters.clear();
        for(SubArgument argument : this.subArgument) {
            LinkedList<CommandParameter> parameters = new LinkedList<>();
            parameters.add(CommandParameter.newEnum(argument.getName(), new String[]{argument.getName()}));
            parameters.addAll(Arrays.asList(argument.getParameters()));
            this.commandParameters.put(argument.getName(),parameters.toArray(new CommandParameter[0]));
        }
    }

}