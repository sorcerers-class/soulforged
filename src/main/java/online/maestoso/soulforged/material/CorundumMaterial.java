package online.maestoso.soulforged.material;

import net.minecraft.item.Item;
import net.minecraft.util.Pair;

import java.util.List;

public class CorundumMaterial extends CrystalMaterial {
    private final CorundumTypes type;
    public CorundumMaterial(CorundumTypes type) {
        super(8, 8, 4, 390, 680);
        this.type = type;
    }

    public CorundumTypes getType() {
        return type;
    }
}
