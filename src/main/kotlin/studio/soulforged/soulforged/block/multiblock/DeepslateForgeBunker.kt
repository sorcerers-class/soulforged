package studio.soulforged.soulforged.block.multiblock

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.slot.Slot
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.quiltmc.loader.api.minecraft.ClientOnly
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings
import studio.soulforged.soulforged.block.SoulforgedInventory
import studio.soulforged.soulforged.block.entity.SoulforgedBlockEntityTypes
import studio.soulforged.soulforged.client.gui.SoulforgedScreenHandlerTypes

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
class DeepslateForgeBunkerScreenHandler(syncId: Int, playerInventory: PlayerInventory, val inventory: Inventory)
    : ScreenHandler(SoulforgedScreenHandlerTypes.DEEPSLATE_FORGE_BUNKER, syncId) {
    constructor(syncId: Int, playerInventory: PlayerInventory) : this(syncId, playerInventory, SimpleInventory(1))
    init {
        checkSize(inventory, 1)
        inventory.onOpen(playerInventory.player)
        addSlot(Slot(inventory, 0, 80, 53))
        for (i in 0..2) {
            for (j in 0..8) {
                addSlot(Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18))
            }
        }

        for (i in 0..8) {
            addSlot(Slot(playerInventory, i, 8 + i * 18, 142))
        }
    }

    override fun canUse(player: PlayerEntity?): Boolean {
        return this.inventory.canPlayerUse(player)
    }

    override fun quickTransfer(player: PlayerEntity?, fromIndex: Int): ItemStack? {
        var itemStack = ItemStack.EMPTY
        val slot = slots[fromIndex]
        if (slot.hasStack()) {
            val itemStack2 = slot.stack
            itemStack = itemStack2.copy()
            if (fromIndex < 1) {
                if (!insertItem(itemStack2, 1, 36, true)) {
                    return ItemStack.EMPTY
                }
            } else if (!insertItem(itemStack2, 0, 1, false)) {
                return ItemStack.EMPTY
            }
            if (itemStack2.isEmpty) {
                slot.setStackByPlayer(ItemStack.EMPTY)
            } else {
                slot.markDirty()
            }
            if (itemStack2.count == itemStack.count) {
                return ItemStack.EMPTY
            }
            slot.onTakeItem(player, itemStack2)
        }
        return itemStack
    }

    override fun close(player: PlayerEntity?) {
        super.close(player)
        this.inventory.onClose(player)
    }
}
@ClientOnly
class DeepslateForgeBunkerScreen(handler: DeepslateForgeBunkerScreenHandler, inventory: PlayerInventory)
    : HandledScreen<DeepslateForgeBunkerScreenHandler>(handler, inventory, Text.literal("Deepslate Forge Bunker")) {
    companion object {
        private val TEXTURE = Identifier("soulforged:textures/gui/container/deepslate_forge_bunker.png")
    }
    override fun init() {
        super.init()
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2
    }
    override fun render(graphics: GuiGraphics?, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(graphics)
        super.render(graphics, mouseX, mouseY, delta)
        drawMouseoverTooltip(graphics, mouseX, mouseY)
    }
    override fun drawBackground(graphics: GuiGraphics?, delta: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShaderTexture(0, TEXTURE)
        val i = (width - backgroundWidth) / 2
        val j = (height - backgroundHeight) / 2
        graphics?.drawTexture(TEXTURE, i, j, 0, 0, backgroundWidth, backgroundHeight)
    }

}