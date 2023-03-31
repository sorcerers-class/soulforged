package studio.soulforged.soulforged.util

import net.minecraft.item.Item
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
    fun stripJson(id: Identifier, prefix: String): Identifier {
        return Identifier(id.namespace, id.path.replace(prefix, "").replace(".json", ""))
    }
    fun resolveTranslationKey(id: Identifier, ns: String, group: String): String {
        return "$ns.${id.namespace}.$group.${id.path}"
    }
}

typealias ItemOrTag = Pair<Item?, Identifier?>