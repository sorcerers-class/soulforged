package studio.soulforged.soulforged.block.multiblock

import net.minecraft.block.Blocks
import net.minecraft.block.pattern.BlockPatternBuilder
import net.minecraft.block.pattern.CachedBlockPosition
import net.minecraft.predicate.block.BlockStatePredicate

object SoulforgedMultiblocks {
    internal val MULTIBLOCK_PATTERN = BlockPatternBuilder.start()
        .aisle("BBB", "BBB", "BBB")
        .aisle("BRB", "R R", "BRB")
        .aisle("BCB", "C C", "BCB")
        .aisle("BNB", "NBN", "BNB")
        .where('B', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.DEEPSLATE_BRICKS).or(BlockStatePredicate.forBlock(DeepslateForgeDeepslateBrickBlock))))
        .where('R', CachedBlockPosition.matchesBlockState(
            BlockStatePredicate.forBlock(Blocks.DEEPSLATE_BRICKS)
                .or(BlockStatePredicate.forBlock(DeepslateForgeDeepslateBrickBlock))
                .or { it.block == DeepslateForgeBurner && it.get(TIER) == 1 }
        ))
        .where('C', CachedBlockPosition.matchesBlockState(
            BlockStatePredicate.forBlock(Blocks.DEEPSLATE_BRICKS)
                .or(BlockStatePredicate.forBlock(DeepslateForgeDeepslateBrickBlock))
                .or(BlockStatePredicate.forBlock(DeepslateForgeBurner))
                .or(BlockStatePredicate.forBlock(DeepslateForgeController))
        ))
        .where('N', CachedBlockPosition.matchesBlockState(
            BlockStatePredicate.forBlock(Blocks.DEEPSLATE_BRICKS)
                .or(BlockStatePredicate.forBlock(DeepslateForgeDeepslateBrickBlock))
                .or(BlockStatePredicate.forBlock(DeepslateForgeBunker))
        ))
        .where(' ', CachedBlockPosition.matchesBlockState(
            BlockStatePredicate.forBlock(Blocks.AIR)
        ))
        .build()
}