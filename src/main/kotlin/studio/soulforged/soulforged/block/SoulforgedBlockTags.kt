package studio.soulforged.soulforged.block

import net.minecraft.block.Block
import net.minecraft.registry.tag.BlockTags
import net.minecraft.registry.tag.TagKey
import studio.soulforged.soulforged.Soulforged.id

object SoulforgedBlockTags {
    val WORKSHOP_BLOCKS: TagKey<Block> = BlockTags.create("workshop_blocks".id().toString())
    internal fun init() {

    }
}