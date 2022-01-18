package online.maestoso.soulforged.mixin.common;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Supplier;

@Mixin(Registry.class)
public interface RegistryAccessor {
    @Invoker("create") public static<T> Registry<T> create(RegistryKey<? extends Registry<T>> key, Supplier<T> defaultEntry) {
        throw new AssertionError();
    }
}
