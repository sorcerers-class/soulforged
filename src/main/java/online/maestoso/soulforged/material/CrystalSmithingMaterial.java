package online.maestoso.soulforged.material;

import java.awt.*;

public class CrystalSmithingMaterial extends ToolSmithingMaterial {
    public CrystalSmithingMaterial(double hardness, double edgeholding, int workability, int density, int durability, int miningLevel, int miningSpeed, float predicate) {
        super(hardness, edgeholding, workability, density, durability, 0, miningLevel, miningSpeed, predicate);
    }
}
