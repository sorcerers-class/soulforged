package online.maestoso.soulforged.item.tool;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import net.minecraft.block.BlockState;

import net.minecraft.client.item.TooltipContext;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;

import net.minecraft.nbt.NbtCompound;

import net.minecraft.text.Text;

import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;

import net.minecraft.world.World;

import online.maestoso.soulforged.item.tool.part.ToolPart;
import online.maestoso.soulforged.item.tool.part.ToolParts;
import online.maestoso.soulforged.material.SmithingMaterial;
import online.maestoso.soulforged.material.SmithingMaterials;

import online.maestoso.soulforged.util.BlockUtil;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class ToolItem extends Item {
    public ToolItem() {
        super(new FabricItemSettings()
                .rarity(Rarity.RARE)
                .fireproof()
                .maxCount(1)
                .maxDamage(256));
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        NbtCompound nbt = stack.getNbt();
        ToolPart head = ToolParts.TOOL_PARTS_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_head").getString("type"))),
                binding = ToolParts.TOOL_PARTS_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_binding").getString("type"))),
                shaft = ToolParts.TOOL_PARTS_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_shaft").getString("type")));
        SmithingMaterial mhead = SmithingMaterials.SMITHING_MATERIALS_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_head").getString("material"))),
                        mbinding = SmithingMaterials.SMITHING_MATERIALS_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_binding").getString("material"))),
                         mshaft = SmithingMaterials.SMITHING_MATERIALS_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_shaft").getString("material")));

        double head_edgeholding =  mhead.getEdgeholding();
        double head_hardness = mhead.getHardness();
        ToolType type = ToolTypes.TOOL_TYPES_REGISTRY.get(Identifier.tryParse(nbt.getString("sf_tool_type")));
        double piercing_damage = type.getDefaultAttack().piercingDamage();
        double total_piercing_damage = ((head_edgeholding + (head_hardness * 0.75)) / 2) * piercing_damage;

        double head_weight = head.weight() * mhead.getDensity(),
                binding_weight = binding.weight() * mbinding.getDensity(),
                shaft_weight = shaft.weight() * mshaft.getDensity();

        double effective_weight = head_weight + binding_weight + (0.25 * shaft_weight);
        double total_blunt_damage = (((effective_weight/100) + (head_hardness*0.25))*type.getDefaultAttack().bluntDamage())*0.8;
        target.damage(DamageSource.player((PlayerEntity) attacker), (float) (total_blunt_damage + total_piercing_damage));
        return true;
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        MiningSpeedProcessor msp = ToolTypes.TOOL_TYPES_REGISTRY.get(new Identifier(stack.getNbt().getString("sf_tool_type"))).getMiningSpeed();
        return msp.getMiningSpeed(state, SmithingMaterials.SMITHING_MATERIALS_REGISTRY.get(new Identifier(stack.getNbt().getCompound("sf_head").getString("material"))));
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!world.isClient && state.getHardness(world, pos) != 0.0f) {
            NbtCompound nbt = stack.getNbt();
            if (nbt != null) {
                if (!nbt.contains("sf_damage")) {
                    int head_dura = (int) (Objects.requireNonNull(ToolParts.TOOL_PARTS_REGISTRY.get(new Identifier(nbt.getCompound("sf_head").getString("type")))).durability() * Objects.requireNonNull(SmithingMaterials.SMITHING_MATERIALS_REGISTRY.get(new Identifier(nbt.getCompound("sf_head").getString("material")))).getDurability());
                    int binding_dura = (int) (Objects.requireNonNull(ToolParts.TOOL_PARTS_REGISTRY.get(new Identifier(nbt.getCompound("sf_binding").getString("type")))).durability() * Objects.requireNonNull(SmithingMaterials.SMITHING_MATERIALS_REGISTRY.get(new Identifier(nbt.getCompound("sf_binding").getString("material")))).getDurability());
                    int shaft_dura = (int) (Objects.requireNonNull(ToolParts.TOOL_PARTS_REGISTRY.get(new Identifier(nbt.getCompound("sf_shaft").getString("type")))).durability() * Objects.requireNonNull(SmithingMaterials.SMITHING_MATERIALS_REGISTRY.get(new Identifier(nbt.getCompound("sf_shaft").getString("material")))).getDurability());

                    nbt.getCompound("sf_head").putInt("max_damage", head_dura);
                    nbt.getCompound("sf_binding").putInt("max_damage", binding_dura);
                    nbt.getCompound("sf_shaft").putInt("max_damage", shaft_dura);

                    nbt.getCompound("sf_head").putInt("damage", head_dura);
                    nbt.getCompound("sf_binding").putInt("damage", binding_dura);
                    nbt.getCompound("sf_shaft").putInt("damage", shaft_dura);
                }
                int head_durability = nbt.getCompound("sf_head").getInt("damage");
                int head_max = nbt.getCompound("sf_head").getInt("max_damage");
                nbt.getCompound("sf_head").putInt("damage", head_durability - 1);
                int head_damage = (head_durability / head_max) * 256 - 1;

                int binding_durability = nbt.getCompound("sf_binding").getInt("damage");
                int binding_max = nbt.getCompound("sf_binding").getInt("max_damage");
                nbt.getCompound("sf_binding").putInt("damage", binding_durability - 1);
                int binding_damage = (binding_durability / binding_max) * 256 - 1;

                int shaft_durability = nbt.getCompound("sf_shaft").getInt("damage");
                int shaft_max = nbt.getCompound("sf_shaft").getInt("max_damage");
                nbt.getCompound("sf_shaft").putInt("damage", shaft_durability - 1);
                int shaft_damage = (shaft_durability / shaft_max) * 256 - 1;

                stack.setDamage(256 - Math.min(Math.min(shaft_damage, binding_damage), head_damage));
            }
        }
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
        return super.useOnBlock(context);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return false;
    }
}
