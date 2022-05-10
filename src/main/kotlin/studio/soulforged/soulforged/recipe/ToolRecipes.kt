package studio.soulforged.soulforged.recipe

import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import studio.soulforged.soulforged.Soulforged

@Suppress("unused")
object ToolRecipes {
    private val TOOL_RECIPES_KEY: RegistryKey<Registry<ToolRecipe>> = RegistryKey.ofRegistry(Identifier("soulforged", "tool_recipes"))
    val TOOL_RECIPES_REGISTRY: Registry<ToolRecipe> =
        Registry.registerSimple(TOOL_RECIPES_KEY) { ToolRecipe(null, null, null) }
    val SHORTSWORD_RECIPE = register(Identifier("soulforged:shortsword"), ToolRecipe(
        Identifier("soulforged:shortsword_blade"),
        Identifier("soulforged:hilt"),
        Identifier("soulforged:handle")
    ))
    val BROADSWORD_RECIPE = register(Identifier("soulforged:broadsword"), ToolRecipe(
        Identifier("soulforged:broadsword_blade"),
        Identifier("soulforged:wide_hilt"),
        Identifier( "soulforged:handle")
    ))
    val LONGSWORD_RECIPE = register(Identifier("soulforged:longsword"), ToolRecipe(
        Identifier("soulforged:longsword_blade"),
        Identifier("soulforged:wide_hilt"),
        Identifier("soulforged:long_handle")
    ))
    val GREATSWORD_RECIPE = register(Identifier("soulforged:greatsword"), ToolRecipe(
        Identifier("soulforged:greatsword_blade"),
        Identifier("soulforged:wide_hilt"),
        Identifier("soulforged:long_handle")
    ))
    val RAPIER_RECIPE = register(Identifier("soulforged:rapier"), ToolRecipe(
        Identifier("soulforged:rapier_blade"),
        Identifier("soulforged:slim_hilt"),
        Identifier("soulforged:handle")
    ))
    val SHORTSPEAR_RECIPE = register(Identifier("soulforged:shortspear"), ToolRecipe(
        Identifier("soulforged:spearhead"),
        Identifier("soulforged:binding"),
        Identifier("soulforged:long_shaft")
    ))
    val LONGSPEAR_RECIPE = register(Identifier("soulforged:longspear"), ToolRecipe(
        Identifier("soulforged:spearhead"),
        Identifier("soulforged:binding"),
        Identifier("soulforged:very_long_shaft")
    ))
    val JAVELIN_RECIPE = register(Identifier("soulforged:javelin"), ToolRecipe(
        Identifier("soulforged:spearhead"),
        Identifier("soulforged:binding"),
        Identifier("soulforged:javelin_shaft")
    ))
    val MACE_RECIPE = register(Identifier("soulforged:mace"), ToolRecipe(
        Identifier("soulforged:macehead"),
        Identifier("soulforged:binding"),
        Identifier("soulforged:short_shaft")
    ))
    val MORNINGSTAR_RECIPE = register(Identifier("soulforged:morningstar"), ToolRecipe(
        Identifier("soulforged:morningstar_head"),
        Identifier("soulforged:binding"),
        Identifier("soulforged:short_shaft")
    ))
    val GREATAXE_RECIPE = register(Identifier("soulforged:greataxe"), ToolRecipe(
        Identifier("soulforged:greataxe_head"),
        Identifier("soulforged:binding"),
        Identifier("soulforged:long_handle")
    ))
    val GREATHAMMER_RECIPE = register(Identifier("soulforged:greathammer"), ToolRecipe(
        Identifier("soulforged:greathammer_head"),
        Identifier("soulforged:tough_binding"),
        Identifier("soulforged:long_handle")
    ))
    val WARHAMMER_RECIPE = register(Identifier("soulforged:warhammer"), ToolRecipe(
        Identifier("soulforged:warhammer_head"),
        Identifier("soulforged:binding"),
        Identifier("soulforged:long_handle")
    ))
    val AXE_RECIPE = register(Identifier("soulforged:axe"), ToolRecipe(
        Identifier("soulforged:axe_head"),
        Identifier("soulforged:binding"),
        Identifier("soulforged:handle")
    ))
    val PICKAXE_RECIPE = register(Identifier("soulforged:pickaxe"), ToolRecipe(
        Identifier("soulforged:pickaxe_head"),
        Identifier("soulforged:binding"),
        Identifier("soulforged:long_handle")
    ))
    val SHOVEL_RECIPE = register(Identifier("soulforged:shovel"), ToolRecipe(
        Identifier("soulforged:shovel_head"),
        Identifier("soulforged:binding"),
        Identifier("soulforged:long_handle")
    ))
    val HOE_RECIPE = register(Identifier("soulforged:hoe_recipe"), ToolRecipe(
        Identifier("soulforged:hoe_head"),
        Identifier("soulforged:binding"),
        Identifier("soulforged:long_handle")
    ))
    fun register(id: Identifier, recipe: ToolRecipe): ToolRecipe {
        Soulforged.LOGGER.debug("Try register: $id")
        return Registry.register(TOOL_RECIPES_REGISTRY, id, recipe)
    }
}