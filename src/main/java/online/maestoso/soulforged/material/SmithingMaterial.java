package online.maestoso.soulforged.material;

import java.awt.*;

public class SmithingMaterial {
    private double hardness;
    private double edgeholding;
    private int workability;
    private int density;
    private int durability;
    private int heat;
    private int padding;
    private Color color;

    public SmithingMaterial(double hardness, double edgeholding, int workability, int density, int durability, int heat, int padding, int miningLevel, Color color) {
        this.hardness = hardness;
        this.edgeholding = edgeholding;
        this.workability = workability;
        this.density = density;
        this.durability = durability;
        this.heat = heat;
        this.padding = padding;
        this.color = color;
    }

    /**
     * Indicates if the material can be made into tools.
     */
    public boolean canIntoTool() {
        return true;
    }

    /**
     * Indicates if the material can be made into armor.
     */
    public boolean canIntoArmor() {
        return true;
    }

    public double getHardness() {
        return hardness;
    }

    public void setHardness(double hardness) {
        this.hardness = hardness;
    }

    public double getEdgeholding() {
        return edgeholding;
    }

    public void setEdgeholding(double edgeholding) {
        this.edgeholding = edgeholding;
    }

    public int getWorkability() {
        return workability;
    }

    public void setWorkability(int workability) {
        this.workability = workability;
    }

    public int getDensity() {
        return density;
    }

    public void setDensity(int density) {
        this.density = density;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public int getHeat() {
        return heat;
    }

    public void setHeat(int heat) {
        this.heat = heat;
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
