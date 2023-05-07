package studio.soulforged.soulforged.client.attack

import org.quiltmc.loader.api.minecraft.ClientOnly

@ClientOnly
interface AttackQueueHolder {
    val queue: AttackQueue
}