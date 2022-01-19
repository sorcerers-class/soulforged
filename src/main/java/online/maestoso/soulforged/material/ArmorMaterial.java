package online.maestoso.soulforged.material;

import net.minecraft.item.Item;
import net.minecraft.util.Pair;

import java.awt.*;
import java.util.List;

public class ArmorMaterial extends Material {
    public ArmorMaterial(double hardness, int workability, int density, int durability, int heat, int padding, Color color) {
        super(hardness, 0, workability, density, durability, heat, padding, color);
    }

    @Override
    public boolean canIntoTool() {
        return false;
    }
}
