package online.maestoso.soulforged.material;

import java.util.Optional;

public record Material(double hardness, double edgeholding, int workability, int density, int durability, int heat, int padding, int miningLevel, float miningSpeed, boolean canIntoTool, boolean canIntoArmor, Optional<Classifiers> classifier) {}