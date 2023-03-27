package studio.soulforged.soulforged.block

import net.minecraft.block.Block
import net.minecraft.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object SoulforgedBlockTags {
    val WORKSHOP_BLOCKS: TagKey<Block> = TagKey.of(Registry.BLOCK_KEY, Identifier("soulforged:workshop_blocks"))
    fun init() {

    }
}