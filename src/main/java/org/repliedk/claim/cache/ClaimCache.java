package org.repliedk.claim.cache;

import java.util.HashMap;

import cn.nukkit.Player;
import cn.nukkit.level.Position;

public class ClaimCache {

    private HashMap<String, Position> position1 = new HashMap<>();
    private HashMap<String, Position> position2 = new HashMap<>();

    public void addToPlayerCachePosition(Player player, Position position, int posInteger) {
        if (posInteger == 1) {
            position1.put(player.getName(), position);
        } else {
            position2.put(player.getName(), position);
        }
    }

    public Position getPlayerPosition(Player player, int posInteger) {
        if (posInteger == 1) {
            return position1.get(player.getName());
        } else {
            return position2.get(player.getName());
        }
    }

    public void removeFromPlayerCache(Player player, int posInteger) {
        if (posInteger == 1) {
            position1.remove(player.getName());
        } else {
            position2.remove(player.getName());
        }
    }

    public boolean hasPosition(Player player, int posInteger) {
        if (posInteger == 1) {
            return position1.containsKey(player.getName());
        } else {
            return position2.containsKey(player.getName());
        }
    }

    public void clearCache() {
        position1.clear();
        position2.clear();
    }
    
}