package studio.soulforged.soulforged.item.tool

import net.minecraft.item.ItemUsageContext
import net.minecraft.util.ActionResult

interface RightClickEventProcessor {
    fun onRightClick(ctx: ItemUsageContext?): ActionResult?
}