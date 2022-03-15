package studio.soulforged.soulforged.sound

import net.minecraft.sound.SoundEvent
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object SoulforgedSoundEvents {
    val CRIT_THRUSTING = register("soulforged:crit_thrusting")
    val CRIT_SLASHING = register("soulforged:crit_slashing")
    val CRIT_CRUSHING = register("soulforged:crit_crushing")
    private fun register(identifier: String): SoundEvent {
        val id = Identifier.tryParse(identifier)
        val se = SoundEvent(id)
        Registry.register(Registry.SOUND_EVENT, id, se)
        return se
    }
}