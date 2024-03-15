package org.repliedk.utils;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.level.GlobalBlockPalette;
import cn.nukkit.level.Level;
import cn.nukkit.network.protocol.UpdateBlockPacket;

import java.util.concurrent.CompletableFuture;

public class PillarBuilder {

    public static Level level;

    public PillarBuilder(Level level1) {
        level = level1;
    }

    public static CompletableFuture<Integer> highestBlockAtAsync(int x, int z, int upperBound, int lowerBound) {
        CompletableFuture<Integer> future = new CompletableFuture<>();

        CompletableFuture.runAsync(() -> {
            int highestY = findHighestBlock(x, z, upperBound, lowerBound);
            future.complete(highestY);
        });

        return future;
    }

    private static int findHighestBlock(int x, int z, int upperBound, int lowerBound) {
        int midY;
        while (lowerBound <= upperBound) {
            midY = lowerBound + (upperBound - lowerBound) / 2;
            int blockId = level.getBlockIdAt(x, midY, z);
            if (blockId == Block.AIR) {
                upperBound = midY - 1;
            } else {
                lowerBound = midY + 1;
            }
        }
        return lowerBound;
    }

    public static void buildPillar(Player player, int x, int y, int z, int firstBlockId, int secondBlockId) {
        CompletableFuture<Integer> futureHeight = highestBlockAtAsync(x, z, 128, 0);

        futureHeight.thenAccept(highestY -> {
            if (highestY > y) {
                UpdateBlockPacket packet = new UpdateBlockPacket();
                packet.x = x;
                packet.z = z;
                packet.blockRuntimeId = GlobalBlockPalette.getOrCreateRuntimeId(firstBlockId);
                for (int i = y; i < highestY; i++) {
                    packet.y = i;
                    player.dataPacket(packet);
                }
            }
        });
    }

    public static void clearPillar(Player player, int x, int y, int z) {
        CompletableFuture<Integer> futureHeight = highestBlockAtAsync(x, z, 128, 0);

        futureHeight.thenAccept(highestY -> {
            UpdateBlockPacket packet = new UpdateBlockPacket();
            packet.x = x;
            packet.z = z;
            packet.blockRuntimeId = GlobalBlockPalette.getOrCreateRuntimeId(Block.AIR);
            for (int i = y; i < highestY; i++) {
                packet.y = i;
                player.dataPacket(packet);
            }
        });
    }

}
