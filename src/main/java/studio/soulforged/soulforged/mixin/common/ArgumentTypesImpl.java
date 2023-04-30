package studio.soulforged.soulforged.mixin.common;

import com.mojang.brigadier.arguments.ArgumentType;
import net.minecraft.command.argument.ArgumentTypeInfo;
import net.minecraft.command.argument.ArgumentTypeInfos;
import net.minecraft.command.argument.SingletonArgumentInfo;
import net.minecraft.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import studio.soulforged.soulforged.command.MaterialArgumentType;
import studio.soulforged.soulforged.command.ToolTypeArgumentType;

@Mixin(ArgumentTypeInfos.class)
public abstract class ArgumentTypesImpl {
    @Shadow
    private static <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>> ArgumentTypeInfo<A, T> register(
            Registry<ArgumentTypeInfo<?, ?>> registry, String id, Class<? extends A> entry, ArgumentTypeInfo<A, T> type
    ) {
        return null;
    }

    @Inject(method = "bootstrap", at = @At("RETURN"))
    private static void soulforged$injectBootstrap(Registry<ArgumentTypeInfo<?, ?>> registry, CallbackInfoReturnable<ArgumentTypeInfo<?, ?>> cir) {
        register(registry, "soulforged:tool_type", ToolTypeArgumentType.class, SingletonArgumentInfo.contextFree(ToolTypeArgumentType::new));
        register(registry, "soulforged:tool_material", MaterialArgumentType.class, SingletonArgumentInfo.contextFree(MaterialArgumentType::new));
    }
}
