package online.maestoso.soulforged.material;

import java.awt.*;

public class ArmorSmithingMaterial extends SmithingMaterial {
    public ArmorSmithingMaterial(double hardness, int workability, int density, int durability, int heat, int padding, Color color) {
        super(hardness, 0, workability, density, durability, heat, padding, -1, color);
    }

    @Override
    public boolean canIntoTool() {
        return false;
    }
}
