package online.maestoso.soulforged.material;

public class Material {
    private int hardness;
    private float edgeholding;
    private int workability;
    private int density;
    private int durability;
    private int heat;
    private int padding;

    public Material(int hardness, double edgeholding, double workability, int density, int durability, int heat, int padding) {
        this.hardness = hardness;
    }
    public int getHardness() {
        return this.hardness;
    }
}
