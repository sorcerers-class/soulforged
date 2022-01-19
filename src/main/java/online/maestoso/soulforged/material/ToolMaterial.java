package online.maestoso.soulforged.material;

public class ToolMaterial extends Material {
    public ToolMaterial(double hardness, double edgeholding, int workability, int density, int durability, int heat) {
        super(hardness, edgeholding, workability, density, durability, heat, 0);
    }

    @Override
    public boolean canIntoArmor() {
        return false;
    }
}
