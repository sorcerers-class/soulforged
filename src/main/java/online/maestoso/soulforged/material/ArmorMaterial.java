package online.maestoso.soulforged.material;

public class ArmorMaterial extends Material {
    public ArmorMaterial(double hardness, int workability, int density, int durability, int heat, int padding) {
        super(hardness, 0, workability, density, durability, heat, padding);
    }

    @Override
    public boolean canIntoTool() {
        return false;
    }
}
