package studio.soulforged.soulforged.sound

import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.sound.SoundEvent
import net.minecraft.util.Identifier
import studio.soulforged.soulforged.Soulforged.sid

@Suppress("unused")
object SoulforgedSoundEvents {
    val CRIT_THRUSTING = register("crit_thrusting".sid())
    val CRIT_SLASHING = register("crit_slashing".sid())
    val CRIT_CRUSHING = register("crit_crushing".sid())

    private fun register(identifier: String): SoundEvent {
        val id = Identifier.tryParse(identifier)
        val se = SoundEvent.createVariableRangeEvent(id)
        Registry.register(Registries.SOUND_EVENT, id, se)
        return se
    }
    internal fun init() {

    }
}