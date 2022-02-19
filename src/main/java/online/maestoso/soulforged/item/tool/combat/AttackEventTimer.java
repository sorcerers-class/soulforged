package online.maestoso.soulforged.item.tool.combat;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import online.maestoso.soulforged.Soulforged;
import online.maestoso.soulforged.item.tool.ToolItem;

public class AttackEventTimer {
    private final LivingEntity target;
    private final LivingEntity attacker;
    private final ItemStack tool;
    private int timer = 0;
    private int hitCounter;
    private float lowestCooldown = 1.0f;
    public AttackEventTimer(LivingEntity target, LivingEntity attacker, ItemStack tool) {
        this.target = target;
        this.attacker = attacker;
        this.tool = tool;
        this.hitCounter = 1;
    }
    public void tick() {
        float cooldownProgress = ((PlayerEntity) attacker).getAttackCooldownProgress(0.0f);
        timer++;
        lowestCooldown = Math.min(cooldownProgress, lowestCooldown);

    }
    public void addHit() {
        hitCounter++;
    }
    public boolean isTimerFinished() {
        return timer > 15;
    }
    public void onTimerFinished() {
        boolean fullAttack = lowestCooldown == 1.0f || lowestCooldown == 0.0f;
        switch(hitCounter) {
            case 1 -> {
                if(fullAttack) {
                    target.damage(DamageSource.player((PlayerEntity) attacker), (float) ToolItem.calcDamage(tool, 0));
                } else {
                    target.damage(DamageSource.player((PlayerEntity) attacker), 1);
                }
            }
            case 2 -> {
                target.damage(DamageSource.player((PlayerEntity) attacker), (float) ToolItem.calcDamage(tool, 1));
            }
            default -> Soulforged.LOGGER.error("AttackEventTimer finished without any attacks!");
        }
    }
}
