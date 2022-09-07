package studio.soulforged.soulforged.mixin.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import studio.soulforged.soulforged.item.SoulforgedItems;
import studio.soulforged.soulforged.item.tool.ToolInst;
import studio.soulforged.soulforged.item.tool.ToolInstSerializer;
import studio.soulforged.soulforged.item.tool.combat.AttackHandler;

@Mixin(PlayerEntity.class)
public abstract class SetAttackSpeedMixin extends LivingEntity {
    protected SetAttackSpeedMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "getAttackCooldownProgressPerTick", at = @At("HEAD"), cancellable = true)
    private void injectCalculatedAttackSpeed(CallbackInfoReturnable<Float> cir) {
        ItemStack stack = this.getMainHandStack();
        if(stack.getItem() == SoulforgedItems.INSTANCE.getTOOL()) {
            assert stack.getNbt() != null;
            ToolInst tool = ToolInstSerializer.INSTANCE.deserialize(stack.getNbt());
            cir.setReturnValue((float) ((this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED) / 4.0f) / tool.baseAttackSpeed(tool.attackProperties(2)) * 20.0f));
        }
    }
    @ModifyVariable(method = "attack(Lnet/minecraft/entity/Entity;)V", at = @At("STORE"), ordinal = 1)
    private float injectCalculatedAttackDamage(float f) {
        if(this.getMainHandStack().getItem() == SoulforgedItems.INSTANCE.getTOOL()) {
            System.out.println(AttackHandler.Companion.getLastAttackDamage());
            return AttackHandler.Companion.getLastAttackDamage();
        }
        return f;
    }
    @Inject(method = "attack", at = @At(target = "Lnet/minecraft/entity/player/PlayerEntity;resetLastAttackedTicks()V", value = "INVOKE"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void testMethod(Entity target, CallbackInfo ci, float f, float g, float h) {
        System.out.println(f);
    }
}