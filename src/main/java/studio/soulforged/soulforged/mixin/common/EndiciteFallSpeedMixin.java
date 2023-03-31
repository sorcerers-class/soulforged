package studio.soulforged.soulforged.mixin.common;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import studio.soulforged.soulforged.item.SoulforgedItems;

@Mixin(ItemEntity.class)
public abstract class EndiciteFallSpeedMixin {
    @Shadow public abstract ItemStack getStack();

    @ModifyArg(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec3d;add(DDD)Lnet/minecraft/util/math/Vec3d;"), index = 1)
    private double soulforged$adjustYVelocity(double y) {
        if(this.getStack().isOf(SoulforgedItems.INSTANCE.getENDICITE()) || this.getStack().isOf(SoulforgedItems.INSTANCE.getENDICITE_ORE())) {
            return -0.005;
        } else return -0.04;
    }
}
