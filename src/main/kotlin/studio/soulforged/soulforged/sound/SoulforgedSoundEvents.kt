package studio.soulforged.soulforged.sound

import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.sound.SoundEvent
import net.minecraft.util.Identifier

@Suppress("unused")
object SoulforgedSoundEvents {
    val CRIT_THRUSTING = register("soulforged:crit_thrusting")
    val CRIT_SLASHING = register("soulforged:crit_slashing")
    val CRIT_CRUSHING = register("soulforged:crit_crushing")

    private fun register(identifier: String): SoundEvent {
        val id = Identifier.tryParse(identifier)
        val se = SoundEvent.createVariableRangeEvent(id)
        Registry.register(Registries.SOUND_EVENT, id, se)
        return se
    }
    fun register() {

    }
}