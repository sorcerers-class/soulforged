package online.maestoso.soulforged.item;

import net.minecraft.item.Item;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import online.maestoso.soulforged.item.tool.ForgedToolItem;

@SuppressWarnings("unused")
public class SoulforgedItems {
    //public static final Item TOOL_PART = Registry.register(Registry.ITEM, new Identifier("soulforged", "tool_part"), new Item(new FabricItemSettings().maxCount(16).fireproof().rarity(Rarity.UNCOMMON)));
    public static final Item TOOL = Registry.register(Registry.ITEM, new Identifier("soulforged", "tool"), new ForgedToolItem());
}
