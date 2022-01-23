package online.maestoso.soulforged;

import net.fabricmc.api.ModInitializer;
import online.maestoso.soulforged.item.SoulforgedItems;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Soulforged implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("Soulforged");
    @Override
    public void onInitialize() {
        SoulforgedItems.TOOL.asItem();
    }
}
