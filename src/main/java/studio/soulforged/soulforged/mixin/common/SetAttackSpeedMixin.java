package studio.soulforged.soulforged.mixin.common;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import studio.soulforged.soulforged.item.SoulforgedItems;
import studio.soulforged.soulforged.item.tool.ToolInst;

@Mixin(PlayerEntity.class)
public abstract class SetAttackSpeedMixin extends LivingEntity {
    protected SetAttackSpeedMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "getAttackCooldownProgressPerTick", at = @At("HEAD"), cancellable = true)
    private void soulforged$injectCalculatedAttackSpeed(CallbackInfoReturnable<Float> cir) {
        ItemStack stack = this.getMainHandStack();
        if(stack.isOf(SoulforgedItems.INSTANCE.getTOOL())) {
            assert stack.getNbt() != null;
            ToolInst tool = ToolInst.ToolInstSerializer.INSTANCE.deserialize(stack.getNbt());
            cir.setReturnValue((float) ((this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED) / 4.0f) / tool.baseAttackSpeed(tool.attackProperties(1)) * 20.0f));
        }
    }
}