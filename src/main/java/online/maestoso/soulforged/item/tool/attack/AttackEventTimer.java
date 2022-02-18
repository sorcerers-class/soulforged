package online.maestoso.soulforged.item.tool.attack;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import online.maestoso.soulforged.Soulforged;
import online.maestoso.soulforged.item.tool.ToolItem;

import java.util.Objects;

public class AttackEventTimer {
    private final LivingEntity target;
    private final LivingEntity attacker;
    private final ItemStack tool;
    private int timer = 0;
    private int hitCounter;
    private float cooldownProgress = 0.0f;
    private boolean fullAttack = false;

    public AttackEventTimer(LivingEntity target, LivingEntity attacker, ItemStack tool) {
        this.target = target;
        this.attacker = attacker;
        this.tool = tool;
        this.hitCounter = 1;
    }
    public void tick() {
        cooldownProgress = ((PlayerEntity)attacker).getAttackCooldownProgress(0.0f);
        System.out.println(cooldownProgress);
        timer++;
        if(cooldownProgress == 1.0f || cooldownProgress == 0.0f)
            fullAttack = true;
        else if(cooldownProgress > 0.0f && cooldownProgress < 1.0f)
            fullAttack = false;
    }
    public void addHit() {
        Soulforged.LOGGER.info("Hit registerred!");
        hitCounter++;
    }
    public boolean isTimerFinished() {
        return timer > 60;
    }
    public void onTimerFinished() {
        Soulforged.LOGGER.info("Timer finished!");
        switch(hitCounter) {
            case 1 -> {
                if(fullAttack) {
                    Soulforged.LOGGER.info("Executing full power attack");
                    target.damage(DamageSource.player((PlayerEntity) attacker), (float) ToolItem.calcDamage(tool, 0));
                } else {
                    Soulforged.LOGGER.info("Executing weakened attack");
                    target.damage(DamageSource.player((PlayerEntity) attacker), 1);
                }
            }
            case 2 -> {
                Soulforged.LOGGER.info("Executing double click attack");
                target.damage(DamageSource.player((PlayerEntity) attacker), (float) ToolItem.calcDamage(tool, 1));
            }
            default -> {}
        }
    }
}
