package studio.soulforged.soulforged.util

import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.util.Identifier

object RegistryUtil {
    fun<T> createRegistry(id: Identifier, default: T): Registry<T> {
        val key: RegistryKey<Registry<T>> = RegistryKey.ofRegistry(id)
        return Registries.registerSimple(key) { default }
    }
    fun<T> createRegistry(id: String, default: T): Registry<T> {
        return createRegistry(Identifier(id), default)
    }
}