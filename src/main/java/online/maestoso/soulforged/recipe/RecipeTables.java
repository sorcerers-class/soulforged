package online.maestoso.soulforged.recipe;

import org.apache.commons.lang3.tuple.Triple;

import java.util.HashMap;

public class RecipeTables {
    public static final HashMap<String, Triple<String, String, String>> TOOL_RECIPES = new HashMap<>();
    public static void initRecipeTables() {
        TOOL_RECIPES.put("soulforged:shortsword", Triple.of("soulforged:shortsword_blade", "soulforged:hilt", "soulforged:handle"));
        TOOL_RECIPES.put("soulforged:broadsword", Triple.of("soulforged:broadsword_blade", "soulforged:wide_hilt", "soulforged:handle"));
        TOOL_RECIPES.put("soulforged:longsword", Triple.of("soulforged:longsword_blade", "soulforged:wide_hilt", "soulforged:long_handle"));
        TOOL_RECIPES.put("soulforged:greatsword", Triple.of("soulforged:greatsword_blade", "soulforged:wide_hilt", "soulforged:long_handle"));
        TOOL_RECIPES.put("soulforged:rapier", Triple.of("soulforged:rapier_blade", "soulforged:slim_hilt", "soulforged:handle"));
        TOOL_RECIPES.put("soulforged:shortspear", Triple.of("soulforged:spearhead", "soulforged:binding", "soulforged:long_shaft"));
        TOOL_RECIPES.put("soulforged:longspear", Triple.of("soulforged:spearhead", "soulforged:binding", "soulforged:very_long_shaft'"));
        TOOL_RECIPES.put("soulforged:javelin", Triple.of("soulforged:spearhead", "soulforged:binding", "soulforged:javelin_shaft"));
        TOOL_RECIPES.put("soulforged:mace", Triple.of("soulforged:macehead", "soulforged:binding", "soulforged:short_shaft"));
        TOOL_RECIPES.put("soulforged:morningstar", Triple.of("soulforged:morningstar_head", "soulforged:binding", "soulforged:short_shaft"));
        TOOL_RECIPES.put("soulforged:greataxe", Triple.of("soulforged:greataxe_head", "soulforged:tough_binding", "soulforged:long_handle"));
        TOOL_RECIPES.put("soulforged:greathammer", Triple.of("soulforged:greathammer_head", "soulforged:tough_binding", "soulforged:long_handle"));
        TOOL_RECIPES.put("soulforged:warhammer", Triple.of("soulforged:warhammer_head", "soulforged:binding", "soulforged:long_handle"));
        TOOL_RECIPES.put("soulforged:axe", Triple.of("soulforged:axe_head", "soulforged:binding", "soulforged:handle"));
        TOOL_RECIPES.put("soulforged:pickaxe", Triple.of("soulforged:pickaxe_head", "soulforged:binding", "soulforged:long_handle"));
        TOOL_RECIPES.put("soulforged:shovel", Triple.of("soulforged:shovel_head", "soulforged:binding", "soulforged:long_handle"));
        TOOL_RECIPES.put("soulforged:hoe", Triple.of("soulforged:hoe_head", "soulforged:binding", "soulforged:long_handle"));
    }
}
