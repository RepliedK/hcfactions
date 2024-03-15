
package org.repliedk.claim;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import cn.nukkit.level.Position;
import cn.nukkit.utils.TextFormat;
import lombok.Getter;
import org.repliedk.claim.cache.ClaimCache;

import java.util.ArrayList;
import java.util.List;

public class ClaimManager {

    @Getter
    public static ClaimManager instance = new ClaimManager();

    @Getter
    public static ClaimCache claimCache = new ClaimCache();

    private final List<Claim> claims;

    public ClaimManager() {
        this.claims = new ArrayList<>();
    }

    public void addClaim(Claim claim) {
        this.claims.add(claim);
    }

    public void removeClaim(Claim claim) {
        this.claims.remove(claim);
    }

    public Claim getClaimAt(Position pos) {
        return claims.stream().filter(claim -> claim.getAlignedBB().isVectorInside(pos)).findFirst().orElse(null);
    }

    public static Item getClaimWand() {
        return new Item(ItemID.GOLD_HOE).setCustomName(TextFormat.colorize("&r&l&6Claim Wand"));
    }

    
}
