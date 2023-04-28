package studio.soulforged.soulforged.command

import com.mojang.brigadier.CommandDispatcher
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import org.quiltmc.qkl.library.brigadier.*
import studio.soulforged.soulforged.Soulforged
import studio.soulforged.soulforged.item.SoulforgedItems
import studio.soulforged.soulforged.item.tool.ToolInst
import studio.soulforged.soulforged.material.Materials

object GiveToolCommand {
    fun register(dispatcher: CommandDispatcher<ServerCommandSource>) {
        dispatcher.register("givetool") {
//            required(players("targets")) { targets ->
                required(toolType("type")) { type ->
                    required(toolMaterial("head")) { head ->
                        required(toolMaterial("binding")) { binding ->
                            required(toolMaterial("handle")) { handle ->
                                optional(toolMaterial("pattern")) { pattern ->
                                    requires {
                                            it.hasPermissionLevel(2)
                                    }
                                    execute {
//                                        val targets: Collection<ServerPlayerEntity> = this[targets].required()
                                        val targets = listOf(source.playerOrThrow)
                                        for (target in targets) {
                                            try {
                                                val stack = SoulforgedItems.TOOL.defaultStack
                                                val tool = ToolInst(this[type].value(), this[head].value(), this[binding].value(), this[handle].value(), this[pattern]?.value() ?: Materials.MATERIALS[Identifier("soulforged:tool_material")]!!)
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
                                    }
                                }
                            }
                        }
                    }
                }
//            }
        }
    }
}