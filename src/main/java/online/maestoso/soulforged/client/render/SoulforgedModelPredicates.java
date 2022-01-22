package online.maestoso.soulforged.client.render;

import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import online.maestoso.soulforged.item.SoulforgedItems;
import online.maestoso.soulforged.item.tool.part.ToolParts;
import online.maestoso.soulforged.material.SmithingMaterials;

public class SoulforgedModelPredicates {
    public static void register() {
        FabricModelPredicateProviderRegistry.register(SoulforgedItems.TOOL, new Identifier("soulforged", "head_type"), (stack, world, entity, _i) -> ToolParts.TOOL_PARTS_REGISTRY.get(Identifier.tryParse(stack.getNbt().getCompound("sf_head").getString("type"))).predicate());
        FabricModelPredicateProviderRegistry.register(SoulforgedItems.TOOL, new Identifier("soulforged", "head_material"), (stack, world, entity, _i) -> SmithingMaterials.SMITHING_MATERIALS_REGISTRY.get(Identifier.tryParse(stack.getNbt().getCompound("sf_head").getString("material"))).getPredicate());
        FabricModelPredicateProviderRegistry.register(SoulforgedItems.TOOL, new Identifier("soulforged", "binding_type"), (stack, world, entity, _i) -> ToolParts.TOOL_PARTS_REGISTRY.get(Identifier.tryParse(stack.getNbt().getCompound("sf_binding").getString("type"))).predicate());
        FabricModelPredicateProviderRegistry.register(SoulforgedItems.TOOL, new Identifier("soulforged", "binding_material"), (stack, world, entity, _i) -> SmithingMaterials.SMITHING_MATERIALS_REGISTRY.get(Identifier.tryParse(stack.getNbt().getCompound("sf_binding").getString("material"))).getPredicate());
        FabricModelPredicateProviderRegistry.register(SoulforgedItems.TOOL, new Identifier("soulforged", "shaft_type"), (stack, world, entity, _i) -> ToolParts.TOOL_PARTS_REGISTRY.get(Identifier.tryParse(stack.getNbt().getCompound("sf_shaft").getString("type"))).predicate());
        FabricModelPredicateProviderRegistry.register(SoulforgedItems.TOOL, new Identifier("soulforged", "shaft_material"), (stack, world, entity, _i) -> SmithingMaterials.SMITHING_MATERIALS_REGISTRY.get(Identifier.tryParse(stack.getNbt().getCompound("sf_shaft").getString("material"))).getPredicate());
    }
}
