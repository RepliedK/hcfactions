// Beautiful Messages in English
package org.repliedk.session.arguments.repair.sub;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemArmor;
import cn.nukkit.item.ItemDurable;
import cn.nukkit.utils.TextFormat;
import org.repliedk.arguments.SubArgument;

public class RepairAllArgument extends SubArgument {

    public RepairAllArgument() {
        super("all");
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            message(sender, "&cOnly players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 2) {
            repairInventory(player);
            message(sender, "&aYour inventory has been fully repaired.");
            return true;
        }

        Player target = Server.getInstance().getPlayer(args[1]);

        if (target == null) {
            message(sender, "&cPlayer not found.");
            return true;
        }

        repairInventory(target);

        if (target.getName().equals(sender.getName())) {
            message(sender, "&aYour inventory has been fully repaired.");
            return true;
        }

        message(sender, "&aYou have fully repaired the armor of " + target.getName() + ".");
        target.sendMessage(TextFormat.colorize("&a" + player.getName() + " has fully repaired your armor."));

        return true;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[] {
                CommandParameter.newType("player", true, CommandParamType.TARGET)
        };
    }

    public static void repairInventory(Player player) {
        PlayerInventory inventory = player.getInventory();
        for (int slot = 0; slot < inventory.getSize(); slot++) {
            Item item = inventory.getItem(slot);
            if (item instanceof ItemDurable && item.getDamage() > 0) {
                item.setDamage(0);
                inventory.setItem(slot, item);
            }
        }

        if (inventory.getHelmet() != null) {
            if (inventory.getHelmet() instanceof ItemArmor && inventory.getHelmet().getDamage() > 0) {
                Item item = inventory.getHelmet();
                item.setDamage(0);
                inventory.setHelmet(item);
            }
        }

        if (inventory.getChestplate() != null) {
            if (inventory.getChestplate() instanceof ItemArmor && inventory.getChestplate().getDamage() > 0) {
                Item item = inventory.getChestplate();
                item.setDamage(0);
                inventory.setChestplate(item);
            }
        }

        if (inventory.getLeggings() != null) {
            if (inventory.getLeggings() instanceof ItemArmor && inventory.getLeggings().getDamage() > 0) {
                Item item = inventory.getLeggings();
                item.setDamage(0);
                inventory.setLeggings(item);
            }
        }

        if (inventory.getBoots() != null) {
            if (inventory.getBoots() instanceof ItemArmor && inventory.getBoots().getDamage() > 0) {
                Item item = inventory.getBoots();
                item.setDamage(0);
                inventory.setBoots(item);
            }
        }


    }

}