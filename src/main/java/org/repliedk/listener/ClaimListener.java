package org.repliedk.listener;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.level.Position;
import cn.nukkit.utils.TextFormat;
import org.repliedk.claim.Claim;
import org.repliedk.claim.ClaimManager;
import org.repliedk.claim.cache.ClaimCache;
import org.repliedk.session.PlayerSession;
import org.repliedk.session.SessionFactory;
import org.repliedk.utils.PillarBuilder;

public class ClaimListener implements Listener {

    private final ClaimManager claimManager;
    private final ClaimCache claimCache;

    public ClaimListener() {
        this.claimManager = ClaimManager.getInstance();
        this.claimCache = ClaimManager.getClaimCache();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        PlayerSession playerSession = SessionFactory.get(player.getName());
        Position pos = event.getBlock().getLocation();
        Claim claim = claimManager.getClaimAt(pos);

        if (claim != null && !claim.getName().equalsIgnoreCase(playerSession.getTeam().getName())) {
            event.setCancelled(true);
            player.sendMessage(TextFormat.RED + "This block is protected by a claim.");
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        PlayerSession playerSession = SessionFactory.get(player.getName());
        Position pos = event.getBlock().getLocation();
        Claim claim = claimManager.getClaimAt(pos);

        if (claim != null && !claim.getName().equalsIgnoreCase(playerSession.getTeam().getName())) {
            event.setCancelled(true);
            player.sendMessage(TextFormat.RED + "This action is protected by a claim.");
        } else {
            handleClaimInteraction(player, pos);
        }
    }

    private void handleClaimInteraction(Player player, Position pos) {
        if (player.getInventory().getItemInHand().equals(ClaimManager.getClaimWand())){
            return;
        }
        if (!claimCache.hasPosition(player, 1)) {
            markPositionAndNotifyPlayer(player, pos, 1, "First position marked.", TextFormat.GREEN);
        } else if (!claimCache.hasPosition(player, 2)) {
            markPositionAndNotifyPlayer(player, pos, 2, "Second position marked.", TextFormat.GREEN);
        } else {
            removePositionAndNotifyPlayer(player, 2, "Second position removed.", TextFormat.RED);
            markPositionAndNotifyPlayer(player, pos, 2, "Second position marked.", TextFormat.GREEN);
        }
    }

    private void markPositionAndNotifyPlayer(Player player, Position pos, int index, String message, TextFormat color) {
        claimCache.addToPlayerCachePosition(player, pos, index);
        buildPillar(player, index);
        player.sendMessage(color + message);
    }

    private void removePositionAndNotifyPlayer(Player player, int index, String message, TextFormat color) {
        claimCache.removeFromPlayerCache(player, index);
        clearPillar(player, index);
        player.sendMessage(color + message);
    }

    private void clearPillar(Player player, int index){
        Position position = claimCache.getPlayerPosition(player, index);
        PillarBuilder.clearPillar(player, position.getFloorX(), position.getFloorY(), position.getFloorZ());
    }

    private void buildPillar(Player player, int index) {
        Position firstPos = claimCache.getPlayerPosition(player, index);
        PillarBuilder.buildPillar(player, firstPos.getFloorX(), firstPos.getFloorY(), firstPos.getFloorZ(), Block.STONE, Block.DIRT);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Position from = event.getFrom();
        Position to = event.getTo();
        PlayerSession playerSession = SessionFactory.get(player.getName());

        handleClaimEnterAndLeave(player, from, to, playerSession);
        updatePlayerClaimState(to, playerSession);
    }

    private void handleClaimEnterAndLeave(Player player, Position from, Position to, PlayerSession playerSession) {
        Claim fromClaim = claimManager.getClaimAt(from);
        Claim toClaim = claimManager.getClaimAt(to);

        if (fromClaim != null && !fromClaim.equals(toClaim)) {
            player.sendMessage(TextFormat.YELLOW + "Leaving claim: " + fromClaim.getName());
        }

        if (toClaim != null && !toClaim.equals(fromClaim)) {
            player.sendMessage(TextFormat.YELLOW + "Entering claim: " + toClaim.getName());
        } else if (toClaim == null && !playerSession.getCurrentClaim().equals("Warzone")) {
            player.sendMessage(TextFormat.YELLOW + "Entering claim: Warzone");
        }
    }

    private void updatePlayerClaimState(Position position, PlayerSession playerSession) {
        Claim currentClaim = claimManager.getClaimAt(position);
        playerSession.setCurrentClaim(currentClaim != null ? currentClaim.getName() : "Warzone");
    }

}