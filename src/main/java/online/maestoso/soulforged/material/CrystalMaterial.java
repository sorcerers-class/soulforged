package online.maestoso.soulforged.material;

import net.minecraft.item.Item;
import net.minecraft.util.Pair;

import java.awt.*;
import java.util.List;

public class CrystalMaterial extends ToolMaterial {
    public CrystalMaterial(double hardness, double edgeholding, int workability, int density, int durability, Color color) {
        super(hardness, edgeholding, workability, density, durability, 0, color);
    }
}
