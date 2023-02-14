package studio.soulforged.soulforged.block

import net.minecraft.block.Block
import net.minecraft.registry.tag.BlockTags
import net.minecraft.registry.tag.TagKey
object SoulforgedBlockTags {
    val WORKSHOP_BLOCKS: TagKey<Block> = BlockTags.create("soulforged:workshop_blocks")
    fun init() {

    }
}