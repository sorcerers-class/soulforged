package online.maestoso.soulforged.item.tool;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import net.minecraft.block.BlockState;

import net.minecraft.client.item.TooltipContext;

import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

import net.minecraft.item.*;

import net.minecraft.nbt.NbtCompound;

import net.minecraft.tag.BlockTags;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;

import net.minecraft.world.World;

import online.maestoso.soulforged.item.tool.part.ForgedToolPart;
import online.maestoso.soulforged.item.tool.part.ForgedToolParts;
import online.maestoso.soulforged.material.SmithingMaterial;
import online.maestoso.soulforged.material.SmithingMaterials;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class ForgedToolItem extends Item {
    static final int head = 0;
    static final int binding = 1;
    static final int handle = 2;
    public ForgedToolItem() {
        super(new FabricItemSettings()
                .rarity(Rarity.RARE)
                .fireproof()
                .maxCount(1)
                .maxDamage(256));
    }

    @Override
    public int getEnchantability() {
        return -1;
    }

    public static double calcAttackSpeed(ItemStack stack) {
        return (getWeight(stack) / 800) / ForgedToolTypes.TOOL_TYPES_REGISTRY.get(new Identifier(stack.getNbt().getString("sf_tool_type"))).getDefaultAttack().speed();
    }
    public static double calcDamage(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        assert nbt != null;
        ForgedToolPart head = ForgedToolParts.TOOL_PARTS_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_head").getString("type"))),
                binding = ForgedToolParts.TOOL_PARTS_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_binding").getString("type"))),
                handle = ForgedToolParts.TOOL_PARTS_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_handle").getString("type")));
        SmithingMaterial mhead = SmithingMaterials.SMITHING_MATERIALS_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_head").getString("material"))),
                mbinding = SmithingMaterials.SMITHING_MATERIALS_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_binding").getString("material"))),
                mhandle = SmithingMaterials.SMITHING_MATERIALS_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_handle").getString("material")));

        assert mhead != null;
        double head_edgeholding =  mhead.getEdgeholding();
        double head_hardness = mhead.getHardness();
        ForgedToolType type = ForgedToolTypes.TOOL_TYPES_REGISTRY.get(Identifier.tryParse(nbt.getString("sf_tool_type")));
        assert type != null;
        double piercing_damage = type.getDefaultAttack().piercingDamage();
        double total_piercing_damage = ((head_edgeholding + (head_hardness * 0.75)) / 2) * piercing_damage;

        assert head != null;
        double head_weight = head.weight() * mhead.getDensity(),
                binding_weight = Objects.requireNonNull(binding).weight() * Objects.requireNonNull(mbinding).getDensity(),
                handle_weight = Objects.requireNonNull(handle).weight() * Objects.requireNonNull(mhandle).getDensity();

        double effective_weight = head_weight + binding_weight + (0.25 * handle_weight);
        double total_blunt_damage = (((effective_weight/100) + (head_hardness*0.25))*type.getDefaultAttack().bluntDamage())*0.8;
        return total_blunt_damage + total_piercing_damage;
    }
    public static int calcDurability(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        assert nbt != null;
        if (!nbt.contains("sf_damage")) {
            int head_dura = (int) (Objects.requireNonNull(ForgedToolParts.TOOL_PARTS_REGISTRY.get(new Identifier(nbt.getCompound("sf_head").getString("type")))).durability() * Objects.requireNonNull(SmithingMaterials.SMITHING_MATERIALS_REGISTRY.get(new Identifier(nbt.getCompound("sf_head").getString("material")))).getDurability());
            int binding_dura = (int) (Objects.requireNonNull(ForgedToolParts.TOOL_PARTS_REGISTRY.get(new Identifier(nbt.getCompound("sf_binding").getString("type")))).durability() * Objects.requireNonNull(SmithingMaterials.SMITHING_MATERIALS_REGISTRY.get(new Identifier(nbt.getCompound("sf_binding").getString("material")))).getDurability());
            int handle_dura = (int) (Objects.requireNonNull(ForgedToolParts.TOOL_PARTS_REGISTRY.get(new Identifier(nbt.getCompound("sf_handle").getString("type")))).durability() * Objects.requireNonNull(SmithingMaterials.SMITHING_MATERIALS_REGISTRY.get(new Identifier(nbt.getCompound("sf_handle").getString("material")))).getDurability());

            nbt.getCompound("sf_head").putInt("max_damage", head_dura);
            nbt.getCompound("sf_binding").putInt("max_damage", binding_dura);
            nbt.getCompound("sf_handle").putInt("max_damage", handle_dura);

            nbt.getCompound("sf_head").putInt("damage", head_dura);
            nbt.getCompound("sf_binding").putInt("damage", binding_dura);
            nbt.getCompound("sf_handle").putInt("damage", handle_dura);
        }
        int[] durability = getDurabilities(stack);
        int[] max_durability = getMaxDurabilities(stack);

        nbt.getCompound("sf_head").putInt("damage", durability[head] - 1);
        int head_damage = (durability[head] / max_durability[head]) * 256 - 1;

        nbt.getCompound("sf_binding").putInt("damage", durability[binding] - 1);
        int binding_damage = (durability[binding] / max_durability[binding]) * 256 - 1;

        nbt.getCompound("sf_handle").putInt("damage", durability[handle] - 1);
        int handle_damage = (durability[handle] / max_durability[handle]) * 256 - 1;

        return 256 - Math.min(Math.min(handle_damage, binding_damage), head_damage);
    }
    public static int[] getDurabilities(ItemStack stack) {
        return new int[]{stack.getNbt().getCompound("sf_head").getInt("damage"), stack.getNbt().getCompound("sf_binding").getInt("damage"), stack.getNbt().getCompound("sf_handle").getInt("damage")};
    }
    public static int[] getMaxDurabilities(ItemStack stack) {
        return new int[]{stack.getNbt().getCompound("sf_head").getInt("max_damage"), stack.getNbt().getCompound("sf_binding").getInt("max_damage"), stack.getNbt().getCompound("sf_handle").getInt("max_damage")};
    }
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.setDamage(calcDurability(stack));
        NbtCompound nbt = stack.getNbt();
        if(!nbt.getBoolean("sf_set")) {

        }
        target.damage(DamageSource.player((PlayerEntity) attacker), (float) calcDamage(stack));
        return true;
    }
    public static double getWeight(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();

        ForgedToolPart head = ForgedToolParts.TOOL_PARTS_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_head").getString("type"))),
                binding = ForgedToolParts.TOOL_PARTS_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_binding").getString("type"))),
                handle = ForgedToolParts.TOOL_PARTS_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_handle").getString("type")));
        SmithingMaterial mhead = SmithingMaterials.SMITHING_MATERIALS_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_head").getString("material"))),
                mbinding = SmithingMaterials.SMITHING_MATERIALS_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_binding").getString("material"))),
                mhandle = SmithingMaterials.SMITHING_MATERIALS_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_handle").getString("material")));
        return (head.weight() * mhead.getDensity()) + (binding.weight() * mbinding.getDensity()) + (0.25 * handle.weight() * mhandle.getDensity());

    }
    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        assert stack.getNbt() != null;
        MiningSpeedProcessor msp = Objects.requireNonNull(ForgedToolTypes.TOOL_TYPES_REGISTRY.get(new Identifier(stack.getNbt().getString("sf_tool_type")))).getMiningSpeed();
        return msp.getMiningSpeed(state, SmithingMaterials.SMITHING_MATERIALS_REGISTRY.get(new Identifier(stack.getNbt().getCompound("sf_head").getString("material"))));
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        stack.setDamage(calcDurability(stack));
        return true;
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return true;
    }
    @Override
    public boolean isSuitableFor(BlockState state) {
        return true;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return Objects.requireNonNull(ForgedToolTypes.TOOL_TYPES_REGISTRY.get(Identifier.tryParse(Objects.requireNonNull(Objects.requireNonNull(context.getPlayer()).getMainHandStack().getNbt()).getString("sf_tool_type")))).getRightClick().onRightClick(context);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.of(I18n.translate("item.soulforged.tool.tooltip.weight", Math.round(getWeight(stack) * 100.0) / 100.0)));
        tooltip.add(Text.of(I18n.translate("item.soulforged.tool.tooltip.speed", Math.round(calcAttackSpeed(stack) * 100.0) / 100.0)));
        tooltip.add(Text.of(I18n.translate("item.soulforged.tool.tooltip.attack", Math.round(calcDamage(stack) * 100.0) / 100.0)));
    }

    @Override
    @Environment(EnvType.CLIENT)
    public Text getName(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        assert nbt != null;
        ForgedToolType type = ForgedToolTypes.TOOL_TYPES_REGISTRY.get(new Identifier(nbt.getString("sf_tool_type")));
        SmithingMaterial mat = SmithingMaterials.SMITHING_MATERIALS_REGISTRY.get(new Identifier(nbt.getCompound("sf_head").getString("material")));
        String matLocalized = I18n.translate("item.soulforged.tool.material." + Objects.requireNonNull(SmithingMaterials.SMITHING_MATERIALS_REGISTRY.getId(mat)).getPath());
        return new LiteralText(I18n.translate("item.soulforged.tool.type." + Objects.requireNonNull(ForgedToolTypes.TOOL_TYPES_REGISTRY.getId(type)).getPath(), matLocalized));

    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return false;
    }

}
