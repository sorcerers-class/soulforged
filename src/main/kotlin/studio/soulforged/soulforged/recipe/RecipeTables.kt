package studio.soulforged.soulforged.recipe

import net.minecraft.util.Identifier
import java.util.HashMap
import org.apache.commons.lang3.tuple.Triple

object RecipeTables {
    val TOOL_RECIPES = HashMap<String, Triple<Identifier, Identifier, Identifier>>()
    fun initRecipeTables() {
        TOOL_RECIPES["soulforged:shortsword"] = Triple.of(
            Identifier("soulforged:shortsword_blade"),
            Identifier("soulforged:hilt"),
            Identifier("soulforged:handle")
        )
        TOOL_RECIPES["soulforged:broadsword"] = Triple.of(
            Identifier("soulforged:broadsword_blade"),
            Identifier("soulforged:wide_hilt"),
            Identifier( "soulforged:handle")
        )
        TOOL_RECIPES["soulforged:longsword"] = Triple.of(
            Identifier(  "soulforged:longsword_blade"),
            Identifier(  "soulforged:wide_hilt"),
            Identifier(  "soulforged:long_handle")
        )
        TOOL_RECIPES["soulforged:greatsword"] = Triple.of(
            Identifier( "soulforged:greatsword_blade"),
            Identifier( "soulforged:wide_hilt"),
            Identifier(  "soulforged:long_handle")
        )
        TOOL_RECIPES["soulforged:rapier"] = Triple.of(
            Identifier(   "soulforged:rapier_blade"),
            Identifier(   "soulforged:slim_hilt"),
            Identifier(   "soulforged:handle")
        )
        TOOL_RECIPES["soulforged:shortspear"] = Triple.of(
            Identifier(    "soulforged:spearhead"),
            Identifier(   "soulforged:binding"),
            Identifier(   "soulforged:long_shaft")
        )
        TOOL_RECIPES["soulforged:longspear"] = Triple.of(
            Identifier("soulforged:spearhead"),
            Identifier("soulforged:binding"),
            Identifier("soulforged:very_long_shaft")
        )
        TOOL_RECIPES["soulforged:javelin"] = Triple.of(
            Identifier("soulforged:spearhead"),
            Identifier("soulforged:binding"),
            Identifier("soulforged:javelin_shaft")
        )
        TOOL_RECIPES["soulforged:mace"] = Triple.of(
            Identifier(    "soulforged:macehead"),
            Identifier(   "soulforged:binding"),
            Identifier(   "soulforged:short_shaft")
        )
        TOOL_RECIPES["soulforged:morningstar"] = Triple.of(
            Identifier(  "soulforged:morningstar_head"),
            Identifier(  "soulforged:binding"),
            Identifier(  "soulforged:short_shaft")
        )
        TOOL_RECIPES["soulforged:greataxe"] = Triple.of(
            Identifier(  "soulforged:greataxe_head"),
            Identifier(  "soulforged:tough_binding"),
            Identifier(  "soulforged:long_handle")
        )
        TOOL_RECIPES["soulforged:greathammer"] = Triple.of(
            Identifier( "soulforged:greathammer_head"),
            Identifier(  "soulforged:tough_binding"),
            Identifier(  "soulforged:long_handle")
        )
        TOOL_RECIPES["soulforged:warhammer"] = Triple.of(
            Identifier( "soulforged:warhammer_head"),
            Identifier( "soulforged:binding"),
            Identifier( "soulforged:long_handle")
        )
        TOOL_RECIPES["soulforged:axe"] = Triple.of(
            Identifier("soulforged:axe_head"),
            Identifier("soulforged:binding"),
            Identifier("soulforged:handle")
        )
        TOOL_RECIPES["soulforged:pickaxe"] = Triple.of(
            Identifier(  "soulforged:pickaxe_head"),
            Identifier(  "soulforged:binding"),
            Identifier(  "soulforged:long_handle")
        )
        TOOL_RECIPES["soulforged:shovel"] = Triple.of(
            Identifier( "soulforged:shovel_head"),
            Identifier(  "soulforged:binding"),
            Identifier(  "soulforged:long_handle")
        )
        TOOL_RECIPES["soulforged:hoe"] = Triple.of(
            Identifier( "soulforged:hoe_head"),
            Identifier( "soulforged:binding"),
            Identifier( "soulforged:long_handle")
        )
    }
}