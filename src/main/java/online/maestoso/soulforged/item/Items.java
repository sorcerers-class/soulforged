package online.maestoso.soulforged.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import online.maestoso.soulforged.item.tool.ToolItem;

@SuppressWarnings("unused")
public class Items {
    public static final Item TOOL_PART = Registry.register(Registry.ITEM, new Identifier("soulforged", "tool_part"), new Item(new FabricItemSettings().maxCount(16).fireproof().rarity(Rarity.UNCOMMON)));
    public static final Item TOOL = Registry.register(Registry.ITEM, new Identifier("soulforged", "tool"), new ToolItem());
    public static void register() {}
}
