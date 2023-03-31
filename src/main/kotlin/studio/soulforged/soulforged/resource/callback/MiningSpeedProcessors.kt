package studio.soulforged.soulforged.resource.callback

import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.tag.BlockTags
import net.minecraft.util.Identifier
import studio.soulforged.soulforged.material.Materials
import studio.soulforged.soulforged.resource.callback.MiningSpeedProcessors.MiningSpeedProcessor
import studio.soulforged.soulforged.util.RegistryUtil


object MiningSpeedProcessors {

    val NONE = MiningSpeedProcessor { _, _ ->  return@MiningSpeedProcessor 0.0f}
    val MINING_SPEED_REGISTRY: Registry<MiningSpeedProcessor> = RegistryUtil.createRegistry("mining_speed_processors", NONE)
    val HAND = register(Identifier("soulforged:hand")) { state: BlockState?, mat: Materials.Material? ->
        if (!state!!.isToolRequired) mat!!.miningLevel / 2.0f else if (Registries.BLOCK.get(
                Registries.BLOCK.getRawId(
                    state.block
                )
            ).defaultState.isIn(
                BlockTags.PICKAXE_MINEABLE
            )
        ) 0.0f else 1.0f
    }
    val AXE = register(Identifier("soulforged:axe")) { state: BlockState?, mat: Materials.Material? ->
        if (Registries.BLOCK.get(Registries.BLOCK.getRawId(state?.block)).defaultState.isIn(BlockTags.AXE_MINEABLE)) mat!!.miningSpeed.toFloat()
        else if (Registries.BLOCK.get(Registries.BLOCK.getRawId(state?.block)).defaultState.isIn(BlockTags.PICKAXE_MINEABLE)) 0.0f
        else 1.0f
    }
    val SHOVEL = register(Identifier("soulforged:shovel")) { state: BlockState?, mat: Materials.Material? ->
        if (Registries.BLOCK.get(Registries.BLOCK.getRawId(state?.block)).defaultState.isIn(BlockTags.SHOVEL_MINEABLE)) mat!!.miningSpeed.toFloat() else if (Registries.BLOCK.get(
                Registries.BLOCK.getRawId(
                    state?.block
                )
            ).defaultState.isIn(BlockTags.PICKAXE_MINEABLE)
        ) 0.0f else 1.0f
    }
    val PICKAXE = register(Identifier("soulforged:pickaxe")) { state: BlockState?, mat: Materials.Material? ->
        if (Registries.BLOCK.get(Registries.BLOCK.getRawId(state?.block)).defaultState.isIn(BlockTags.PICKAXE_MINEABLE)) mat!!.miningSpeed.toFloat() else if (Registries.BLOCK.get(
                Registries.BLOCK.getRawId(
                    state?.block
                )
            ).defaultState.isIn(BlockTags.PICKAXE_MINEABLE)
        ) 0.0f else 1.0f
    }
    val HOE = register(Identifier("soulforged:hoe")) { state: BlockState?, mat: Materials.Material? ->
        if (Registries.BLOCK.get(Registries.BLOCK.getRawId(state?.block)).defaultState.isIn(BlockTags.HOE_MINEABLE)) mat!!.miningSpeed.toFloat() else if (Registries.BLOCK.get(
                Registries.BLOCK.getRawId(
                    state?.block
                )
            ).defaultState.isIn(BlockTags.PICKAXE_MINEABLE)
        ) 0.0f else 1.0f
    }
    val SWORD = register(Identifier("soulforged:sword")) { state: BlockState?, mat: Materials.Material? ->
        if (state!!.isOf(Blocks.COBWEB)) {
            mat!!.miningSpeed
        }
        val material = state.material
        if (material == net.minecraft.block.Material.PLANT || material == net.minecraft.block.Material.REPLACEABLE_PLANT || state.isIn(
                BlockTags.LEAVES
            ) || material == net.minecraft.block.Material.GOURD
        ) {
            mat!!.miningSpeed / 10
        }
        if (Registries.BLOCK.get(Registries.BLOCK.getRawId(state.block)).defaultState.isIn(BlockTags.PICKAXE_MINEABLE)) 0.0f else 1.0f
    }

    fun register(id: Identifier, msp: MiningSpeedProcessor): MiningSpeedProcessor {
        return Registry.register(MINING_SPEED_REGISTRY, id, msp)
    }
    fun init() {}
    fun interface MiningSpeedProcessor {
        fun getMiningSpeed(state: BlockState?, mat: Materials.Material?): Float
    }
}

