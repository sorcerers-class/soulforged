package studio.soulforged.soulforged.mixin.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import studio.soulforged.soulforged.item.SoulforgedItemTags;

/**
 * Adjusts the velocity of all items within the soulforged:low_gravity_items tag to have 1/8 gravity and be unable to fall in the void.
 */
@Mixin(ItemEntity.class)
public abstract class EndiciteFallSpeedMixin extends Entity {
    public EndiciteFallSpeedMixin(EntityType<?> variant, World world) {
        super(variant, world);
    }

    @Shadow public abstract ItemStack getStack();
    @ModifyArg(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec3d;add(DDD)Lnet/minecraft/util/math/Vec3d;"), index = 1)
    private double soulforged$adjustYVelocity(double y) {
        double towardsVoid = getY() > 0.0 ? -1.0 : 2.0;
        if(this.getStack().streamTags().anyMatch((tag) -> tag.equals(SoulforgedItemTags.INSTANCE.getLOW_GRAVITY_ITEMS()))) {
            return 0.005 * towardsVoid;
        } else return -0.04;
    }
}
