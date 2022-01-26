package online.maestoso.soulforged.item.tool;

import java.util.Optional;

public record ForgedToolType(AttackProperties defaultAttack, Optional<AttackProperties> hcAttack, Optional<AttackProperties> dcAttack, MiningSpeedProcessor miningSpeed, RightClickEventProcessor rightClick) { }
