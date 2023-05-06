package studio.soulforged.soulforged.item

import net.minecraft.item.Item
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import studio.soulforged.soulforged.Soulforged.id

/**
 * The item tags created by the mod.
 */
object SoulforgedItemTags {
    val LOW_GRAVITY_ITEMS: TagKey<Item> = TagKey.of(RegistryKeys.ITEM, "low_gravity_items".id())
}