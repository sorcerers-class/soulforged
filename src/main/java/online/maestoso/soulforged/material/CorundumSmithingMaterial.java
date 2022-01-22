package online.maestoso.soulforged.material;

import java.awt.*;

public class CorundumSmithingMaterial extends CrystalSmithingMaterial {
    private final CorundumTypes type;
    public CorundumSmithingMaterial(CorundumTypes type, float predicate) {
        super(8, 8, 4, 390, 680, 3, 7, predicate);
        this.type = type;
    }

    public CorundumTypes getType() {
        return type;
    }
}
