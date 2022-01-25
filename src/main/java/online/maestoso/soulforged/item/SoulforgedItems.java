package online.maestoso.soulforged.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import online.maestoso.soulforged.client.render.ForgedToolItemModel;
import online.maestoso.soulforged.item.tool.ForgedToolItem;
import online.maestoso.soulforged.item.tool.ForgedToolTypes;
import online.maestoso.soulforged.material.SmithingMaterials;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class SoulforgedItems {
    public static Map<Identifier, Item> TOOL_PARTS = new HashMap<>();
    public static final Item TOOL = Registry.register(Registry.ITEM, new Identifier("soulforged", "tool"), new ForgedToolItem());
    public static void getToolParts() {
        ArrayList<Item> tool_parts;
        ForgedToolTypes.TOOL_TYPES_REGISTRY.forEach(type -> SmithingMaterials.SMITHING_MATERIALS_REGISTRY.forEach(material -> {
            TOOL_PARTS.put(new Identifier("soulforged", ForgedToolItemModel.getModelName(material, type, ForgedToolItemModel.ModelToolParts.HEAD)), Registry.register(Registry.ITEM, new Identifier("soulforged", ForgedToolItemModel.getModelName(material, type, ForgedToolItemModel.ModelToolParts.HEAD)), new Item(new FabricItemSettings().maxCount(16))));
            TOOL_PARTS.put(new Identifier("soulforged", ForgedToolItemModel.getModelName(material, type, ForgedToolItemModel.ModelToolParts.BINDING)), Registry.register(Registry.ITEM, new Identifier("soulforged", ForgedToolItemModel.getModelName(material, type, ForgedToolItemModel.ModelToolParts.BINDING)), new Item(new FabricItemSettings().maxCount(16))));
            TOOL_PARTS.put(new Identifier("soulforged", ForgedToolItemModel.getModelName(material, type, ForgedToolItemModel.ModelToolParts.HANDLE)), Registry.register(Registry.ITEM, new Identifier("soulforged", ForgedToolItemModel.getModelName(material, type, ForgedToolItemModel.ModelToolParts.HANDLE)), new Item(new FabricItemSettings().maxCount(16))));
        }));
    }
}
