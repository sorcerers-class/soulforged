package online.maestoso.soulforged.mixin.common;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Vec3d;
import online.maestoso.soulforged.item.SoulforgedItems;
import online.maestoso.soulforged.item.tool.ToolItem;
import online.maestoso.soulforged.item.tool.combat.AttackEventTimer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.UUID;

@Mixin(ServerPlayNetworkHandler.class)
public class ToolAttackHandlerImpl {
    @Shadow
    public ServerPlayerEntity player;

    @Inject(method = "onPlayerInteractEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;setSneaking(Z)V", ordinal = 0), cancellable = true)
    private void injectOnPlayerInteractEntity(PlayerInteractEntityC2SPacket packet, CallbackInfo ci) {
        ServerWorld serverWorld = this.player.getWorld();
        Entity target = packet.getEntity(serverWorld);
        if(target != null)
        if(target instanceof LivingEntity && player.getMainHandStack().getItem() == SoulforgedItems.TOOL) {
            UUID playerUuid = player.getUuid();
            double d = 36.0;
            if (this.player.squaredDistanceTo(target) < 36.0) {
                LivingEntity finalTarget = (LivingEntity) target;
                packet.handle(new PlayerInteractEntityC2SPacket.Handler(){
                    private void processInteract(Hand hand, ServerPlayNetworkHandler.Interaction action) {
                        ItemStack itemStack = ToolAttackHandlerImpl.this.player.getStackInHand(hand).copy();
                        ActionResult actionResult = action.run(ToolAttackHandlerImpl.this.player, target, hand);
                        if (actionResult.isAccepted()) {
                            Criteria.PLAYER_INTERACTED_WITH_ENTITY.trigger(ToolAttackHandlerImpl.this.player, itemStack, target);
                            if (actionResult.shouldSwingHand()) {
                                ToolAttackHandlerImpl.this.player.swingHand(hand, true);
                            }
                        }
                    }
                    @Override
                    public void interact(Hand hand) {
                        this.processInteract(hand, PlayerEntity::interact);
                    }

                    @Override
                    public void interactAt(Hand hand2, Vec3d pos) {
                        this.processInteract(hand2, (player, entity, hand) -> entity.interactAt(player, pos, hand));
                    }

                    @Override
                    public void attack() {
                        System.out.println("Tried to attack with soulforged:tool!");
                        if(!ToolItem.attackEventTimers.containsKey(new Pair<>(target.getUuid(), ToolAttackHandlerImpl.this.player.getUuid()))) {
                            ToolItem.attackEventTimers.put(new Pair<>(target.getUuid(), ToolAttackHandlerImpl.this.player.getUuid()), new AttackEventTimer(finalTarget, ToolAttackHandlerImpl.this.player, ToolAttackHandlerImpl.this.player.getMainHandStack()));
                        } else {
                            Objects.requireNonNull(ToolItem.attackEventTimers.put(new Pair<>(target.getUuid(), ToolAttackHandlerImpl.this.player.getUuid()), ToolItem.attackEventTimers.get(new Pair<>(target.getUuid(), ToolAttackHandlerImpl.this.player.getUuid())))).addHit();
                        }
                        ToolAttackHandlerImpl.this.player.getMainHandStack().postHit(finalTarget, ToolAttackHandlerImpl.this.player);
                    }
                });
                ci.cancel();
            }
        }
    }
}
