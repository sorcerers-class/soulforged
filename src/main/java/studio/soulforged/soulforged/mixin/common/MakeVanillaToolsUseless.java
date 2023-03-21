package studio.soulforged.soulforged.mixin.common;

import net.minecraft.item.ToolMaterials;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Lazy;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
@Mixin(ToolMaterials.class)
public class MakeVanillaToolsUseless {
    @Final
    @Mutable
    @Shadow
    private int miningLevel;
    @Final
    @Mutable
    @Shadow
    private int itemDurability;
    @Final
    @Mutable
    @Shadow
    private float miningSpeed;
    @Final
    @Mutable
    @Shadow
    private float attackDamage;
    @Final
    @Mutable
    @Shadow
    private int enchantability;
    @Final
    @Mutable
    @Shadow
    private Lazy<Ingredient> repairIngredient;
    /**
     * @author Lilly Rosaline
     * @reason because vanilla tools are stinky and so are modded ones
     */
    @Inject(method = "<init>", at = @At("RETURN"))
    public void soulforged$injectToolMaterialsConstructor(String string, int i, int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier repairIngredient, CallbackInfo ci) {
        this.miningLevel = -1;
        this.itemDurability = 0;
        this.miningSpeed = 0.0f;
        this.attackDamage = 0.0f;
        this.enchantability = 0;
        this.repairIngredient = new Lazy<>(Ingredient::empty);
    }
}
