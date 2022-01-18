package online.maestoso.soulforged;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import online.maestoso.soulforged.material.Materials;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class Soulforged implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("Soulforged");
    @Override
    public void onInitialize() {
        LOGGER.info("Materials registry seems ok: " + (Objects.requireNonNull(Materials.TOOL_MATERIALS_REGISTRY.get(new Identifier("soulforged", "wood"))).getHardness() == 4));
    }
}
