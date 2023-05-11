package studio.soulforged.soulforged.block.multiblock

import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.ScreenHandler
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings
import studio.soulforged.soulforged.block.SoulforgedInventory
import studio.soulforged.soulforged.block.entity.SoulforgedBlockEntityTypes
import studio.soulforged.soulforged.client.gui.DeepslateForgeBunkerScreenHandler

object DeepslateForgeBunker : DeepslateForgeDummyBlock(
    QuiltBlockSettings.copyOf(Blocks.DEEPSLATE_BRICKS).sounds(
        BlockSoundGroup.DEEPSLATE_BRICKS).luminance { state -> return@luminance if(state.get(Properties.LIT)) 7 else 0 }),
    BlockEntityProvider {
    init {
        defaultState = this.stateManager.defaultState
            .with(Properties.LIT, false)
    }
    override fun getRenderType(state: BlockState?) = BlockRenderType.MODEL
    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
        builder?.add(Properties.LIT)
    }
    override fun getPlacementState(ctx: ItemPlacementContext?): BlockState? {
        return defaultState
    }

    override fun onUse(
        state: BlockState?,
        world: World?,
        pos: BlockPos?,
        player: PlayerEntity?,
        hand: Hand?,
        hit: BlockHitResult?
    ): ActionResult {
        return if (world!!.isClient) {
            ActionResult.SUCCESS
        } else {
            val blockEntity = world.getBlockEntity(pos)
            if (blockEntity is DeepslateForgeBunkerBlockEntity) {
                player!!.openHandledScreen(blockEntity)
            }
            ActionResult.CONSUME
        }
    }
}
class DeepslateForgeBunkerBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(SoulforgedBlockEntityTypes.DEEPSLATE_FORGE_BUNKER, pos, state),
    DeepslateForgeMultiblockComponent, SoulforgedInventory, NamedScreenHandlerFactory {
    override var inventory: DefaultedList<ItemStack> = DefaultedList.ofSize(1, ItemStack.EMPTY)
    override var controllerPos: BlockPos? = pos
    override fun readNbt(nbt: NbtCompound?) {
        super<BlockEntity>.readNbt(nbt)
        super<DeepslateForgeMultiblockComponent>.readNbt(nbt)
        Inventories.readNbt(nbt, inventory)
    }

    override fun writeNbt(nbt: NbtCompound?) {
        Inventories.writeNbt(nbt, inventory)
        super<DeepslateForgeMultiblockComponent>.writeNbt(nbt)
        super<BlockEntity>.writeNbt(nbt)
    }

    override fun createMenu(syncId: Int, playerInventory: PlayerInventory, playerEntity: PlayerEntity?): ScreenHandler {
        return DeepslateForgeBunkerScreenHandler(syncId, playerInventory, this)
    }

    override fun getDisplayName(): Text = Text.literal("Deepslate Forge Bunker")
}