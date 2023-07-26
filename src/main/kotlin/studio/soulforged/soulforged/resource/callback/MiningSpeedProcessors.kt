package studio.soulforged.soulforged.resource.callback

import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.tag.BlockTags
import org.quiltmc.qkl.library.registry.invoke
import studio.soulforged.soulforged.Soulforged
import studio.soulforged.soulforged.Soulforged.sid
import studio.soulforged.soulforged.material.Materials
import studio.soulforged.soulforged.resource.callback.MiningSpeedProcessors.MiningSpeedProcessor
import studio.soulforged.soulforged.util.RegistryUtil


object MiningSpeedProcessors {

    val NONE = MiningSpeedProcessor { _, _ ->  return@MiningSpeedProcessor 0.0f}
    val MINING_SPEED_REGISTRY: Registry<MiningSpeedProcessor> = RegistryUtil.createRegistry("mining_speed_processors".sid(), NONE)
    val HAND = MiningSpeedProcessor { state: BlockState?, mat: Materials.Material? ->
        if (!state!!.isToolRequired) mat!!.miningLevel / 2.0f else if (Registries.BLOCK.get(
                Registries.BLOCK.getRawId(
                    state.block
                )
            ).defaultState.isIn(
                BlockTags.PICKAXE_MINEABLE
            )
        ) 0.0f else 1.0f
    }
    val AXE = MiningSpeedProcessor { state: BlockState?, mat: Materials.Material? ->
        if (Registries.BLOCK.get(Registries.BLOCK.getRawId(state?.block)).defaultState.isIn(BlockTags.AXE_MINEABLE)) mat!!.miningSpeed.toFloat()
        else if (Registries.BLOCK.get(Registries.BLOCK.getRawId(state?.block)).defaultState.isIn(BlockTags.PICKAXE_MINEABLE)) 0.0f
        else 1.0f
    }
    val SHOVEL = MiningSpeedProcessor { state: BlockState?, mat: Materials.Material? ->
        if (Registries.BLOCK.get(Registries.BLOCK.getRawId(state?.block)).defaultState.isIn(BlockTags.SHOVEL_MINEABLE)) mat!!.miningSpeed.toFloat() else if (Registries.BLOCK.get(
                Registries.BLOCK.getRawId(
                    state?.block
                )
            ).defaultState.isIn(BlockTags.PICKAXE_MINEABLE)
        ) 0.0f else 1.0f
    }
    val PICKAXE = MiningSpeedProcessor { state: BlockState?, mat: Materials.Material? ->
        if (Registries.BLOCK.get(Registries.BLOCK.getRawId(state?.block)).defaultState.isIn(BlockTags.PICKAXE_MINEABLE)) mat!!.miningSpeed.toFloat() else if (Registries.BLOCK.get(
                Registries.BLOCK.getRawId(
                    state?.block
                )
            ).defaultState.isIn(BlockTags.PICKAXE_MINEABLE)
        ) 0.0f else 1.0f
    }
    val HOE = MiningSpeedProcessor { state: BlockState?, mat: Materials.Material? ->
        if (Registries.BLOCK.get(Registries.BLOCK.getRawId(state?.block)).defaultState.isIn(BlockTags.HOE_MINEABLE)) mat!!.miningSpeed.toFloat() else if (Registries.BLOCK.get(
                Registries.BLOCK.getRawId(
                    state?.block
                )
            ).defaultState.isIn(BlockTags.PICKAXE_MINEABLE)
        ) 0.0f else 1.0f
    }
    val SWORD = MiningSpeedProcessor { state: BlockState?, mat: Materials.Material? ->
        if (state!!.isOf(Blocks.COBWEB)) {
            mat!!.miningSpeed
        }
        if (state.isIn(BlockTags.SWORD_EFFICIENT)
        ) {
            mat!!.miningSpeed / 10
        }
        if (Registries.BLOCK.get(Registries.BLOCK.getRawId(state.block)).defaultState.isIn(BlockTags.PICKAXE_MINEABLE)) 0.0f else 1.0f
    }
    internal fun init() {
        MINING_SPEED_REGISTRY(Soulforged.NAME) {
            HAND withId "hand"
            AXE withId "axe"
            SHOVEL withId "shovel"
            PICKAXE withId "pickaxe"
            HOE withId "hoe"
            SWORD withId "sword"
        }
    }
    fun interface MiningSpeedProcessor {
        fun getMiningSpeed(state: BlockState?, mat: Materials.Material?): Float
    }
}

