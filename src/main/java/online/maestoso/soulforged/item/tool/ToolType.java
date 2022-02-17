package online.maestoso.soulforged.item.tool;

import online.maestoso.soulforged.item.tool.attack.AttackProperties;

import java.util.Optional;

public record ToolType(AttackProperties defaultAttack, Optional<AttackProperties> hcAttack, Optional<AttackProperties> dcAttack, MiningSpeedProcessor miningSpeed, RightClickEventProcessor rightClick) { }
