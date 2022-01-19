package online.maestoso.soulforged.material;

import net.minecraft.item.Item;
import net.minecraft.util.Pair;

import java.awt.*;
import java.util.List;

public class ToolMaterial extends Material {
    public ToolMaterial(double hardness, double edgeholding, int workability, int density, int durability, int heat, Color color) {
        super(hardness, edgeholding, workability, density, durability, heat, 0, color);
    }

    @Override
    public boolean canIntoArmor() {
        return false;
    }
}
