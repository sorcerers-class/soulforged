package online.maestoso.soulforged.item;

import net.minecraft.item.Item;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import online.maestoso.soulforged.item.tool.ToolItem;

@SuppressWarnings("unused")
public class SoulforgedItems {
    public static final Item TOOL = Registry.register(Registry.ITEM, new Identifier("soulforged", "tool"), new ToolItem());
}
