package online.maestoso.soulforged.item.tool;

import java.util.Optional;

public class ForgedToolType {
    private final AttackProperties defaultAttack;
    private final MiningSpeedProcessor miningSpeed;
    private final RightClickEventProcessor rightClick;
    private final AttackProperties hcAttack;
    private final AttackProperties dcAttack;


    public ForgedToolType(AttackProperties defaultAttack, Optional<AttackProperties> hcAttack, Optional<AttackProperties> dcAttack, MiningSpeedProcessor miningSpeed, RightClickEventProcessor rightClick) {

        this.defaultAttack = defaultAttack;
        this.miningSpeed = miningSpeed;
        this.rightClick = rightClick;
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

    public MiningSpeedProcessor getMiningSpeed() {
        return miningSpeed;
    }

    public RightClickEventProcessor getRightClick() {
        return rightClick;
    }
}
