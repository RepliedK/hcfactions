package org.repliedk.team.argument.sub.admin;

import org.repliedk.arguments.SubArgument;
import org.repliedk.claim.Claim;
import org.repliedk.claim.ClaimManager;
import org.repliedk.claim.cache.ClaimCache;
import org.repliedk.session.PlayerSession;
import org.repliedk.session.SessionFactory;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;

public class TeamClaimForArgument extends SubArgument {

    private final ClaimManager claimManager;
    private final ClaimCache claimCache;

    public TeamClaimForArgument() {
        super("claimfor");

        this.claimManager = ClaimManager.getInstance();
        this.claimCache = ClaimManager.getClaimCache();
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(TextFormat.RED + "This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;
        PlayerSession playerSession = SessionFactory.get(player.getName());

        if (!playerSession.isClaimMode() || !claimCache.hasPosition(player, 1) || !claimCache.hasPosition(player, 2)) {
            player.getInventory().addItem(ClaimManager.getClaimWand());
            playerSession.setClaimMode(true);
            player.sendMessage(TextFormat.GREEN + "Use the claim-wand to mark two positions for your claim.");
        } else {
            if (args.length < 2) {
                player.sendMessage(TextFormat.RED + "Usage: /claimfor <name> <type>");
                return true;
            }

            String name = args[0];
            String type = args[1];

            Claim newClaim = new Claim(claimCache.getPlayerPosition(player, 1), claimCache.getPlayerPosition(player, 2), name, type);
            
            claimManager.addClaim(newClaim); // Add the claim to the pending claims
            claimCache.removeFromPlayerCache(player, 0);
            claimCache.removeFromPlayerCache(player, 1);
            playerSession.setClaimMode(false);
            player.sendMessage("You have accepted the claim request and the land is pending approval.");
        }
        return true;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[0];
    }
}
