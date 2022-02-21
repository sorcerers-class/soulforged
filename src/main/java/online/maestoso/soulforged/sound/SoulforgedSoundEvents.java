package online.maestoso.soulforged.sound;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SoulforgedSoundEvents {
    public static final SoundEvent CRIT_THRUSTING = register("soulforged:crit_thrusting");
    public static final SoundEvent CRIT_SLASHING = register("soulforged:crit_slashing");
    public static final SoundEvent CRIT_CRUSHING = register("soulforged:crit_crushing");

    private static SoundEvent register(String identifier) {
        Identifier id = Identifier.tryParse(identifier);
        SoundEvent se = new SoundEvent(id);
        Registry.register(Registry.SOUND_EVENT, id, se);
        return se;
    }
}
