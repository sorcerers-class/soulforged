package studio.soulforged.soulforged.recipe

import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import studio.soulforged.soulforged.Soulforged

@Suppress("unused")
object ToolRecipes {
    private val TOOL_RECIPES_KEY: RegistryKey<Registry<ToolRecipe>> = RegistryKey.ofRegistry(Identifier("soulforged", "materials"))
    val TOOL_RECIPES_REGISTRY: Registry<ToolRecipe> =
        Registry.registerSimple(TOOL_RECIPES_KEY) { ToolRecipe(null, null, null, null) }
    val SHORTSWORD_RECIPE = register(Identifier("soulforged:shortsword_recipe"), ToolRecipe(
        Identifier("soulforged:shortsword"),
        Identifier("soulforged:shortsword_blade"),
        Identifier("soulforged:hilt"),
        Identifier("soulforged:handle")
    ))
    val BROADSWORD_RECIPE = register(Identifier("soulforged:broadsword_recipe"), ToolRecipe(
        Identifier("soulforged:broadsword"),
        Identifier("soulforged:broadsword_blade"),
        Identifier("soulforged:wide_hilt"),
        Identifier( "soulforged:handle")
    ))
    val LONGSWORD_RECIPE = register(Identifier("soulforged:longsword_recipe"), ToolRecipe(
        Identifier("soulforged:longsword"),
        Identifier("soulforged:longsword_blade"),
        Identifier("soulforged:wide_hilt"),
        Identifier("soulforged:long_handle")
    ))
    val GREATSWORD_RECIPE = register(Identifier("soulforged:greatsword_recipe"), ToolRecipe(
        Identifier("soulforged:greatsword"),
        Identifier("soulforged:greatsword_blade"),
        Identifier("soulforged:wide_hilt"),
        Identifier("soulforged:long_handle")
    ))
    val RAPIER_RECIPE = register(Identifier("soulforged:rapier_recipe"), ToolRecipe(
        Identifier("soulforged:rapier"),
        Identifier("soulforged:rapier_blade"),
        Identifier("soulforged:slim_hilt"),
        Identifier("soulforged:handle")
    ))
    val SHORTSPEAR_RECIPE = register(Identifier("soulforged:shortspear_recipe"), ToolRecipe(
        Identifier("soulforged:shortspear"),
        Identifier("soulforged:spearhead"),
        Identifier("soulforged:binding"),
        Identifier("soulforged:long_shaft")
    ))
    val LONGSPEAR_RECIPE = register(Identifier("soulforged:longspear_recipe"), ToolRecipe(
        Identifier("soulforged:longspear"),
        Identifier("soulforged:spearhead"),
        Identifier("soulforged:binding"),
        Identifier("soulforged:very_long_shaft")
    ))
    val JAVELIN_RECIPE = register(Identifier("soulforged:javelin_recipe"), ToolRecipe(
        Identifier("soulforged:javelin"),
        Identifier("soulforged:spearhead"),
        Identifier("soulforged:binding"),
        Identifier("soulforged:javelin_shaft")
    ))
    val MACE_RECIPE = register(Identifier("soulforged:mace_recipe"), ToolRecipe(
        Identifier("soulforged:mace"),
        Identifier("soulforged:macehead"),
        Identifier("soulforged:binding"),
        Identifier("soulforged:short_shaft")
    ))
    val MORNINGSTAR_RECIPE = register(Identifier("soulforged:morningstar_recipe"), ToolRecipe(
        Identifier("soulforged:morningstar"),
        Identifier("soulforged:morningstar_head"),
        Identifier("soulforged:binding"),
        Identifier("soulforged:short_shaft")
    ))
    val GREATAXE_RECIPE = register(Identifier("soulforged:greataxe_recipe"), ToolRecipe(
        Identifier("soulforged:greataxe"),
        Identifier("soulforged:greataxe_head"),
        Identifier("soulforged:binding"),
        Identifier("soulforged:long_handle")
    ))
    val GREATHAMMER_RECIPE = register(Identifier("soulforged:greathammer_recipe"), ToolRecipe(
        Identifier("soulforged:greathammer"),
        Identifier("soulforged:greathammer_head"),
        Identifier("soulforged:tough_binding"),
        Identifier("soulforged:long_handle")
    ))
    val WARHAMMER_RECIPE = register(Identifier("soulforged:warhammer_recipe"), ToolRecipe(
        Identifier("soulforged:warhammer"),
        Identifier("soulforged:warhammer_head"),
        Identifier("soulforged:binding"),
        Identifier("soulforged:long_handle")
    ))
    val AXE_RECIPE = register(Identifier("soulforged:axe_recipe"), ToolRecipe(
        Identifier("soulforged:axe"),
        Identifier("soulforged:axe_head"),
        Identifier("soulforged:binding"),
        Identifier("soulforged:handle")
    ))
    val PICKAXE_RECIPE = register(Identifier("soulforged:pickaxe_recipe"), ToolRecipe(
        Identifier("soulforged:pickaxe"),
        Identifier("soulforged:pickaxe_head"),
        Identifier("soulforged:binding"),
        Identifier("soulforged:long_handle")
    ))
    val SHOVEL_RECIPE = register(Identifier("soulforged:shovel_recipe"), ToolRecipe(
        Identifier("soulforged:shovel"),
        Identifier("soulforged:shovel_head"),
        Identifier("soulforged:binding"),
        Identifier("soulforged:long_handle")
    ))
    val HOE_RECIPE = register(Identifier("soulforged:hoe_recipe"), ToolRecipe(
        Identifier("soulforged:hoe"),
        Identifier("soulforged:hoe_head"),
        Identifier("soulforged:binding"),
        Identifier("soulforged:long_handle")
    ))
    fun register(id: Identifier, recipe: ToolRecipe): ToolRecipe {
        Soulforged.LOGGER.debug("Try register: $id")
        return Registry.register(TOOL_RECIPES_REGISTRY, id, recipe)
    }
}