package studio.soulforged.soulforged.mixin.common;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Registry.class)
@SuppressWarnings("unused")
public interface RegistryAccessor {
    @Invoker("create") static<T> Registry<T> create(RegistryKey<? extends Registry<T>> key, Registry.DefaultEntryGetter<T> defaultEntryGetter) {
        throw new AssertionError();
    }
}
