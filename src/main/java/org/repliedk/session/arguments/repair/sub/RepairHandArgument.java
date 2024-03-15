package org.repliedk.session.arguments.repair.sub;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemArmor;
import cn.nukkit.item.ItemDurable;
import cn.nukkit.item.ItemTool;
import org.repliedk.arguments.SubArgument;

public class RepairHandArgument extends SubArgument {

    public RepairHandArgument() {
        super("hand");
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!sender.isPlayer()) {
            message(sender, "&cOnly players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        Item item = player.getInventory().getItemInHand();

        if (item.isNull()) {
            message(sender, "&cYou are not holding an item in your hand.");
            return true;
        }

        if (!(item instanceof ItemTool) && !(item instanceof ItemArmor) && !(item instanceof ItemDurable)) {
            message(sender, "&cYou can't repair this item.");
            return true;
        }

        if (isRepaired(item)) {
            message(sender, "&cThis item is already fully repaired!");
            return true;
        }

        item.setDamage(0);
        player.getInventory().setItemInHand(item);
        message(sender, "&aYou have fully repaired the item in your hand.");

        return true;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[0];
    }

    public boolean isRepaired(Item item) {
        return item.getDamage() < 1;
    }
}