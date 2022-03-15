package studio.soulforged.soulforged.recipe

import java.util.HashMap
import org.apache.commons.lang3.tuple.Triple

object RecipeTables {
    val TOOL_RECIPES = HashMap<String, Triple<String, String, String>>()
    fun initRecipeTables() {
        TOOL_RECIPES["soulforged:shortsword"] =
            Triple.of(
                "soulforged:shortsword_blade",
                "soulforged:hilt",
                "soulforged:handle"
            )
        TOOL_RECIPES["soulforged:broadsword"] =
            Triple.of(
                "soulforged:broadsword_blade",
                "soulforged:wide_hilt",
                "soulforged:handle"
            )
        TOOL_RECIPES["soulforged:longsword"] =
            Triple.of(
                "soulforged:longsword_blade",
                "soulforged:wide_hilt",
                "soulforged:long_handle"
            )
        TOOL_RECIPES["soulforged:greatsword"] =
            Triple.of(
                "soulforged:greatsword_blade",
                "soulforged:wide_hilt",
                "soulforged:long_handle"
            )
        TOOL_RECIPES["soulforged:rapier"] =
            Triple.of(
                "soulforged:rapier_blade",
                "soulforged:slim_hilt",
                "soulforged:handle"
            )
        TOOL_RECIPES["soulforged:shortspear"] =
            Triple.of(
                "soulforged:spearhead",
                "soulforged:binding",
                "soulforged:long_shaft"
            )
        TOOL_RECIPES["soulforged:longspear"] = Triple.of(
            "soulforged:spearhead",
            "soulforged:binding",
            "soulforged:very_long_shaft'"
        )
        TOOL_RECIPES["soulforged:javelin"] = Triple.of(
            "soulforged:spearhead",
            "soulforged:binding",
            "soulforged:javelin_shaft"
        )
        TOOL_RECIPES["soulforged:mace"] =
            Triple.of(
                "soulforged:macehead",
                "soulforged:binding",
                "soulforged:short_shaft"
            )
        TOOL_RECIPES["soulforged:morningstar"] =
            Triple.of(
                "soulforged:morningstar_head",
                "soulforged:binding",
                "soulforged:short_shaft"
            )
        TOOL_RECIPES["soulforged:greataxe"] =
            Triple.of(
                "soulforged:greataxe_head",
                "soulforged:tough_binding",
                "soulforged:long_handle"
            )
        TOOL_RECIPES["soulforged:greathammer"] =
            Triple.of(
                "soulforged:greathammer_head",
                "soulforged:tough_binding",
                "soulforged:long_handle"
            )
        TOOL_RECIPES["soulforged:warhammer"] =
            Triple.of(
                "soulforged:warhammer_head",
                "soulforged:binding",
                "soulforged:long_handle"
            )
        TOOL_RECIPES["soulforged:axe"] = Triple.of(
            "soulforged:axe_head",
            "soulforged:binding",
            "soulforged:handle"
        )
        TOOL_RECIPES["soulforged:pickaxe"] =
            Triple.of(
                "soulforged:pickaxe_head",
                "soulforged:binding",
                "soulforged:long_handle"
            )
        TOOL_RECIPES["soulforged:shovel"] =
            Triple.of(
                "soulforged:shovel_head",
                "soulforged:binding",
                "soulforged:long_handle"
            )
        TOOL_RECIPES["soulforged:hoe"] =
            Triple.of(
                "soulforged:hoe_head",
                "soulforged:binding",
                "soulforged:long_handle"
            )
    }
}