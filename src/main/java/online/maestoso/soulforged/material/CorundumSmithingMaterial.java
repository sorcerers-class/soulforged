package online.maestoso.soulforged.material;

import java.awt.*;

public class CorundumSmithingMaterial extends CrystalSmithingMaterial {
    private final CorundumTypes type;
    public CorundumSmithingMaterial(CorundumTypes type, Color color) {
        super(8, 8, 4, 390, 680, 3, 7, color);
        this.type = type;
    }

    public CorundumTypes getType() {
        return type;
    }
}
