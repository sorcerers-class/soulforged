package online.maestoso.soulforged.item.tool;

import net.minecraft.item.ItemUsageContext;

import net.minecraft.util.ActionResult;

public interface RightClickEventProcessor {
    ActionResult onRightClick(ItemUsageContext ctx);
}
