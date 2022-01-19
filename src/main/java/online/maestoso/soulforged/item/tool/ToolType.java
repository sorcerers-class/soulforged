package online.maestoso.soulforged.item.tool;

import net.minecraft.block.BlockState;
import online.maestoso.soulforged.material.SmithingMaterial;

import java.util.Optional;

public class ToolType {
    private final AttackProperties defaultAttack;
    private final AttackProperties hcAttack;
    private final AttackProperties dcAttack;

    @FunctionalInterface
    public interface MiningSpeedGetter {
        float getMiningSpeed(BlockState state, SmithingMaterial mat);
    }
    public ToolType(AttackProperties defaultAttack, Optional<AttackProperties> hcAttack, Optional<AttackProperties> dcAttack, MiningSpeedGetter miningSpeed) {

        this.defaultAttack = defaultAttack;
        this.hcAttack = hcAttack.orElse(defaultAttack);
        this.dcAttack = dcAttack.orElse(defaultAttack);
    }

    public AttackProperties getDefaultAttack() {
        return defaultAttack;
    }

    public AttackProperties getHcAttack() {
        return hcAttack;
    }

    public AttackProperties getDcAttack() {
        return dcAttack;
    }
}
