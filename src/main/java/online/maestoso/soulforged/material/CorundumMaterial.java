package online.maestoso.soulforged.material;

public class CorundumMaterial extends CrystalMaterial {
    private final CorundumTypes type;
    public CorundumMaterial(CorundumTypes type) {
        super(8, 8, 1, 390, 680);
        this.type = type;
    }

    public CorundumTypes getType() {
        return type;
    }
}
