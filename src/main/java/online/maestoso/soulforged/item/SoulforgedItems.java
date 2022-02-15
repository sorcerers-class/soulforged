package online.maestoso.soulforged.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import online.maestoso.soulforged.item.tool.ForgedToolItem;

@SuppressWarnings("unused")
public class SoulforgedItems {
    public static final Item TOOL = Registry.register(Registry.ITEM, new Identifier("soulforged", "tool"), new ForgedToolItem());
    public static final Item BROKEN_TOOL = Registry.register(Registry.ITEM, new Identifier("soulforged", "broken_tool"), new Item(new FabricItemSettings().maxCount(1)));
}
