package studio.soulforged.soulforged.command

import com.mojang.brigadier.CommandDispatcher
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import org.quiltmc.qkl.library.brigadier.argument.players
import org.quiltmc.qkl.library.brigadier.optional
import org.quiltmc.qkl.library.brigadier.register
import org.quiltmc.qkl.library.brigadier.required
import org.quiltmc.qkl.library.brigadier.util.required
import org.quiltmc.qkl.library.brigadier.execute as exec
import studio.soulforged.soulforged.Soulforged
import studio.soulforged.soulforged.item.SoulforgedItems
import studio.soulforged.soulforged.item.tool.ToolInst
import studio.soulforged.soulforged.item.tool.ToolType
import studio.soulforged.soulforged.material.Materials

object GiveToolCommand {
    fun register(dispatcher: CommandDispatcher<ServerCommandSource>) {
        dispatcher.register("givetool") {
            requires { source -> source.hasPermissionLevel(2) }
            required(players("targets")) { targets ->
                required(toolType("type")) { type ->
                    required(
                        toolMaterial("head"),
                        toolMaterial("binding"),
                        toolMaterial("handle")
                    ) { head, binding, handle ->
                        optional(toolMaterial("pattern")) { pattern ->
                            exec {
                                execute(source, targets, type, head, binding, handle, pattern)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun execute(
        source: ServerCommandSource,
        targets: Collection<ServerPlayerEntity>,
        type: ToolType,
        head: Materials.Material,
        binding: Materials.Material,
        handle: Materials.Material,
        pattern: Materials.Material
    ): Int {
        for (target in targets) {
            try {
                val stack = SoulforgedItems.TOOL.defaultStack
                val tool = ToolInst(type, head, binding, handle, pattern)
                tool.setDurability(tool.head.maxDurability, tool.binding.maxDurability, tool.handle.maxDurability)
                stack.nbt = ToolInst.ToolInstSerializer.serialize(tool)
                target.giveItemStack(stack)
                source.sendFeedback(
                    Text.translatable(
                        "commands.give.success.single",
                        1,
                        SoulforgedItems.TOOL.getName(stack),
                        targets.iterator().next().displayName
                    ), true
                )
            } catch(e: Throwable) {
                Soulforged.LOGGER.error(e.stackTraceToString())
            }
        }
        return targets.size
    }
}
