package org.repliedk.claim;

import cn.nukkit.level.Position;
import cn.nukkit.math.AxisAlignedBB;
import cn.nukkit.math.SimpleAxisAlignedBB;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Claim {

    private Position position1;
    private Position position2;
    private String name;
    private String typeName;

    public AxisAlignedBB getAlignedBB() {
        return new SimpleAxisAlignedBB(position1, position2);
    }

    public static Claim fromPositions(Position pos1, Position pos2, String name, String typeName) {
        return new Claim(pos1, pos2, name, typeName);
    }
    
}