package studio.soulforged.soulforged.item.tool

import net.minecraft.item.ItemUsageContext
import net.minecraft.util.ActionResult

fun interface RightClickEventProcessor {
    fun onRightClick(ctx: ItemUsageContext?): ActionResult?
}