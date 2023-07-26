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
import net.minecraft.screen.slot.FurnaceOutputSlot
import net.minecraft.screen.slot.Slot
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import org.quiltmc.loader.api.minecraft.ClientOnly
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings
import studio.soulforged.soulforged.Soulforged
import studio.soulforged.soulforged.block.SoulforgedInventory
import studio.soulforged.soulforged.block.entity.SoulforgedBlockEntityTypes
import studio.soulforged.soulforged.client.gui.SoulforgedScreenHandlerTypes

object DeepslateForgeController : DeepslateForgeDummyBlock(QuiltBlockSettings.copyOf(Blocks.DEEPSLATE_BRICKS).sounds(BlockSoundGroup.DEEPSLATE_BRICKS).luminance { state -> return@luminance if(state.get(
        Properties.LIT)) 7 else 0 }) {
    init {
        defaultState = this.stateManager.defaultState
            .with(HorizontalFacingBlock.FACING, Direction.NORTH)
            .with(Properties.LIT, false)
    }
    override fun getRenderType(state: BlockState?) = BlockRenderType.MODEL
    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
        builder?.add(HorizontalFacingBlock.FACING)
        builder?.add(Properties.LIT)
    }
    override fun getPlacementState(ctx: ItemPlacementContext): BlockState {
        return defaultState.with(AbstractFurnaceBlock.FACING, ctx.playerFacing.opposite) as BlockState
    }

    override fun afterBreak(
        world: World?,
        player: PlayerEntity?,
        pos: BlockPos?,
        state: BlockState?,
        blockEntity: BlockEntity?,
        stack: ItemStack?
    ) {
        super.afterBreak(world, player, pos, state, blockEntity, stack)
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
            if (blockEntity is DeepslateForgeControllerBlockEntity) {
                if(blockEntity.bunkers > 0)
                    player!!.openHandledScreen(blockEntity)
            }
            ActionResult.CONSUME
        }
    }
}
class DeepslateForgeControllerBlockEntity(pos: BlockPos, val state: BlockState) : BlockEntity(SoulforgedBlockEntityTypes.DEEPSLATE_FORGE_CONTROLLER, pos, state),
    DeepslateForgeMultiblockComponent, SoulforgedInventory, NamedScreenHandlerFactory {
    var bunkers = 0
    override var inventory: DefaultedList<ItemStack> = DefaultedList.ofSize(3, ItemStack.EMPTY)
    override var controllerPos: BlockPos? = pos
    override fun readNbt(nbt: NbtCompound?) {
        bunkers = nbt!!.getInt("Bunkers")
        super<BlockEntity>.readNbt(nbt)
        super<DeepslateForgeMultiblockComponent>.readNbt(nbt)
        Inventories.readNbt(nbt, inventory)

    }

    override fun writeNbt(nbt: NbtCompound?) {
        Inventories.writeNbt(nbt, inventory)
        super<DeepslateForgeMultiblockComponent>.writeNbt(nbt)
        super<BlockEntity>.writeNbt(nbt)
        nbt?.putInt("Bunkers", bunkers)
    }
    private fun bunkerAt(pos: BlockPos, value: Int): Int {
        if(world?.getBlockState(pos)?.block == DeepslateForgeBunker
                && world?.getBlockState(pos.offset(Direction.UP))?.block == DeepslateForgeBurner
                && world?.getBlockState(pos.offset(Direction.UP, 2))?.block == DeepslateForgeBurner) {
            return value
        }
        return 0
    }
    fun getBunkers(bunkers: Int): ArrayList<DeepslateForgeBunkerBlockEntity> {
        val facing = state.get(HorizontalFacingBlock.FACING)
        val bottomCenter = pos.offset(Direction.DOWN).offset(facing.opposite)
        val array: ArrayList<DeepslateForgeBunkerBlockEntity> = arrayListOf()
        if(bunkers shr 0 == 1) array.add(world?.getBlockEntity(bottomCenter.offset(facing)) as DeepslateForgeBunkerBlockEntity)
        if(bunkers shr 1 == 1) array.add(world?.getBlockEntity(bottomCenter.offset(facing.rotateYCounterclockwise())) as DeepslateForgeBunkerBlockEntity)
        if(bunkers shr 2 == 1) array.add(world?.getBlockEntity(bottomCenter.offset(facing.opposite)) as DeepslateForgeBunkerBlockEntity)
        if(bunkers shr 3 == 1) array.add(world?.getBlockEntity(bottomCenter.offset(facing.rotateYClockwise())) as DeepslateForgeBunkerBlockEntity)
        return array
    }

    fun deleteMultiblock() {
        val facing = state.get(HorizontalFacingBlock.FACING)
        for(x in -1..1)
            for(y in -1..2)
                for(z in 0..2) {
                    val pos = pos
                        .offset(facing.rotateYClockwise(), x)
                        .offset(Direction.UP, y)
                        .offset(facing.opposite, z)
                    world?.setBlockState(pos, when(world!!.getBlockState(pos).block) {
                        DeepslateForgeDeepslateBrickBlock -> Blocks.DEEPSLATE_BRICKS.defaultState
                        DeepslateForgeBurner -> DeepslateForgeBurner.defaultState
                        DeepslateForgeBunker -> DeepslateForgeBunker.defaultState
                        DeepslateForgeController -> DeepslateForgeController.defaultState.with(HorizontalFacingBlock.FACING, facing)
                        else -> world?.getBlockState(pos)
                    })
                    val entity = world?.getBlockEntity(pos)
                    if(entity is DeepslateForgeMultiblockComponent) {
                        entity.controllerPos = pos
                    }
                    world?.addBlockBreakParticles(pos, state)
                }
        bunkers = 0
        world?.playSound(pos, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.BLOCKS, 1.0f, 0.25f, false)
    }
    fun createMultiblock() {
        Soulforged.LOGGER.info("Trying to create Deepslate forge multiblock at $pos")
        val facing = state.get(HorizontalFacingBlock.FACING)
        val result = SoulforgedMultiblocks.MULTIBLOCK_PATTERN.searchAround(world, pos)
        if(
            result != null
            && world?.getBlockState(pos.offset(Direction.DOWN))?.block == DeepslateForgeBunker
            && world?.getBlockState(pos.offset(Direction.UP))?.block == DeepslateForgeBurner
        ) {
            val bottomCenter = pos.offset(Direction.DOWN).offset(facing.opposite)
            bunkers = 1 or bunkerAt(bottomCenter.offset(facing.rotateYCounterclockwise()), 2) or bunkerAt(bottomCenter.offset(facing.opposite), 4) or bunkerAt(bottomCenter.offset(facing.rotateYClockwise()), 8)
            val controllerPos: BlockPos = pos
            for(x in -1..1)
                for(y in -1..2)
                    for(z in 0..2) {
                        val pos = pos
                            .offset(facing.rotateYClockwise(), x)
                            .offset(Direction.UP, y)
                            .offset(facing.opposite, z)
                        world?.setBlockState(pos, when(world?.getBlockState(pos)?.block) {
                            Blocks.DEEPSLATE_BRICKS -> DeepslateForgeDeepslateBrickBlock.defaultState
                            DeepslateForgeBurner -> DeepslateForgeBurner.defaultState.with(
                                TIER, (y + 1).coerceIn(1..2))
                            DeepslateForgeBunker -> DeepslateForgeBunker.defaultState
                            DeepslateForgeController -> DeepslateForgeController.defaultState.with(HorizontalFacingBlock.FACING, facing)
                            else -> world?.getBlockState(pos)
                        })
                        val entity = world?.getBlockEntity(pos)
                        (entity as? DeepslateForgeMultiblockComponent)?.controllerPos = controllerPos
                        world?.addBlockBreakParticles(pos, state)
                    }
            world?.playSound(pos, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.BLOCKS, 1.0f, 1.0f, false)
        }
    }
    override fun createMenu(syncId: Int, playerInventory: PlayerInventory, playerEntity: PlayerEntity?): ScreenHandler {
        return DeepslateForgeControllerScreenHandler(syncId, playerInventory, this, bunkers)
    }

    override fun getDisplayName(): Text = Text.literal("Deepslate Forge Controller")
}
class DeepslateForgeControllerScreenHandler(syncId: Int, playerInventory: PlayerInventory, private val inventory: Inventory, val bunkers: Int)
    : ScreenHandler(SoulforgedScreenHandlerTypes.DEEPSLATE_FORGE_CONTROLLER, syncId) {
    constructor(syncId: Int, playerInventory: PlayerInventory) : this(syncId, playerInventory, SimpleInventory(3), 2)
    init {
        checkSize(inventory, 3)
        inventory.onOpen(playerInventory.player)
        addSlot(Slot(inventory, 0, 56, 17))
        addSlot(Slot(inventory, 1, 56, 35))
        addSlot(FurnaceOutputSlot(playerInventory.player, inventory, 2, 116, 26))
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
                if (!insertItem(itemStack2, 3, 39, true)) {
                    return ItemStack.EMPTY
                }
            } else if (!insertItem(itemStack2, 0, 3, false)) {
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
class DeepslateForgeControllerScreen(handler: DeepslateForgeControllerScreenHandler, inventory: PlayerInventory)
    : HandledScreen<DeepslateForgeControllerScreenHandler>(handler, inventory, Text.literal("Deepslate Forge Controller")) {
    companion object {
        private val TEXTURE = Identifier("soulforged:textures/gui/container/deepslate_forge_controller.png")
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
        val k = intArrayOf( 1, 5, 9, 12 )[getBunkerCount(handler.bunkers)]
        graphics?.drawTexture(TEXTURE, this.x + 56, this.y + 54 + 12 - k, 172, 12 - k, 14, k + 1)
        graphics?.drawCenteredShadowedText(this.textRenderer, Text.literal(handler.bunkers.toString(2)).asOrderedText(), this.x + 80, this.y + 80, 0xFF0000)
    }
    private fun getBunkerCount(bunkers: Int): Int {
        var c = 0
        for(i in 0..3)
            if (bunkers shl x.inv() < 0) c += 1
        return c
    }
}