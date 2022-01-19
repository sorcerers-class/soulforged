package online.maestoso.soulforged.material;

import net.minecraft.item.Item;
import net.minecraft.util.Pair;

import java.awt.*;
import java.util.List;

public class CorundumMaterial extends CrystalMaterial {
    private final CorundumTypes type;
    public CorundumMaterial(CorundumTypes type, Color color) {
        super(8, 8, 4, 390, 680, color);
        this.type = type;
    }

    public CorundumTypes getType() {
        return type;
    }
}
