package online.maestoso.soulforged.material;

import java.awt.*;

public class ToolSmithingMaterial extends SmithingMaterial {
    public ToolSmithingMaterial(double hardness, double edgeholding, int workability, int density, int durability, int heat, int miningLevel, int miningSpeed, Color color) {
        super(hardness, edgeholding, workability, density, durability, heat, 0, miningLevel, miningSpeed, color);
    }

    @Override
    public boolean canIntoArmor() {
        return false;
    }
}
