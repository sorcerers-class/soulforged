package online.maestoso.soulforged.item.tool;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import net.minecraft.block.BlockState;

import net.minecraft.client.item.TooltipContext;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;

import net.minecraft.entity.player.PlayerEntity;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;

import net.minecraft.nbt.NbtCompound;

import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;

import net.minecraft.world.World;

import online.maestoso.soulforged.item.tool.combat.AttackHandler;
import online.maestoso.soulforged.item.tool.combat.AttackProperties;
import online.maestoso.soulforged.item.tool.part.ToolPart;
import online.maestoso.soulforged.item.tool.part.ToolParts;

import online.maestoso.soulforged.material.Material;
import online.maestoso.soulforged.material.Materials;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ToolItem extends Item {
    static final int head = 0;
    static final int binding = 1;
    static final int handle = 2;
    public ToolItem() {
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
        assert stack.getNbt() != null;
        return 1 / ((getWeight(stack) / 800) / Objects.requireNonNull(ToolTypes.TOOL_TYPES_REGISTRY.get(new Identifier(stack.getNbt().getString("sf_tool_type")))).defaultAttack().speed());
    }
    public static double calcDamage(ItemStack stack, int attackType) {
        NbtCompound nbt = stack.getNbt();
        assert nbt != null;
        if(Arrays.stream(getDurabilities(stack)).anyMatch((i) -> i == 0))
            return 0;
        ToolPart head = ToolParts.TOOL_PARTS_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_head").getString("type"))),
                binding = ToolParts.TOOL_PARTS_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_binding").getString("type"))),
                handle = ToolParts.TOOL_PARTS_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_handle").getString("type")));
        Material mhead = Materials.MATERIAL_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_head").getString("material"))),
                mbinding = Materials.MATERIAL_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_binding").getString("material"))),
                mhandle = Materials.MATERIAL_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_handle").getString("material")));

        assert mhead != null;

        double head_edgeholding =  mhead.edgeholding();
        double head_hardness = mhead.hardness();

        ToolType type = ToolTypes.TOOL_TYPES_REGISTRY.get(Identifier.tryParse(nbt.getString("sf_tool_type")));
        assert type != null;

        AttackProperties ap = type.defaultAttack();
        if(attackType == 1) {
            if(type.dcAttack().isPresent())
                ap = type.dcAttack().get();
            else ap = type.defaultAttack();
        }
        if(attackType >= 3) {
            if(type.dcAttack().isPresent())
                ap = type.hcAttack().get();
            else ap = type.defaultAttack();
        }

        double piercing_damage = ap.piercingDamage();
        double total_piercing_damage = ((head_edgeholding + (head_hardness * 0.75)) / 2) * piercing_damage;

        assert head != null;
        double head_weight = head.weight() * mhead.density(),
                binding_weight = Objects.requireNonNull(binding).weight() * Objects.requireNonNull(mbinding).density(),
                handle_weight = Objects.requireNonNull(handle).weight() * Objects.requireNonNull(mhandle).density();

        double effective_weight = head_weight + binding_weight + (0.25 * handle_weight);
        double total_blunt_damage = (((effective_weight/100) + (head_hardness*0.25))*ap.bluntDamage())*0.8;
        return total_blunt_damage + total_piercing_damage;
    }
    public static int calcDurability(@NotNull ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        assert nbt != null;
        if (!nbt.contains("sf_damage")) {
            int head_dura = (int) (Objects.requireNonNull(ToolParts.TOOL_PARTS_REGISTRY.get(new Identifier(nbt.getCompound("sf_head").getString("type")))).durability() * Objects.requireNonNull(Materials.MATERIAL_REGISTRY.get(new Identifier(nbt.getCompound("sf_head").getString("material")))).durability());
            int binding_dura = (int) (Objects.requireNonNull(ToolParts.TOOL_PARTS_REGISTRY.get(new Identifier(nbt.getCompound("sf_binding").getString("type")))).durability() * Objects.requireNonNull(Materials.MATERIAL_REGISTRY.get(new Identifier(nbt.getCompound("sf_binding").getString("material")))).durability());
            int handle_dura = (int) (Objects.requireNonNull(ToolParts.TOOL_PARTS_REGISTRY.get(new Identifier(nbt.getCompound("sf_handle").getString("type")))).durability() * Objects.requireNonNull(Materials.MATERIAL_REGISTRY.get(new Identifier(nbt.getCompound("sf_handle").getString("material")))).durability());

            nbt.getCompound("sf_head").putInt("max_damage", head_dura);
            nbt.getCompound("sf_binding").putInt("max_damage", binding_dura);
            nbt.getCompound("sf_handle").putInt("max_damage", handle_dura);

            nbt.getCompound("sf_head").putInt("damage", head_dura);
            nbt.getCompound("sf_binding").putInt("damage", binding_dura);
            nbt.getCompound("sf_handle").putInt("damage", handle_dura);
            nbt.putBoolean("sf_damage", true);
        }
        int[] durability = getDurabilities(stack);
        int[] max_durability = getMaxDurabilities(stack);

        nbt.getCompound("sf_head").putInt("damage", durability[head] - 1);
        int head_damage = (int) (((float) durability[head] / (float) max_durability[head]) * 256);

        nbt.getCompound("sf_binding").putInt("damage", durability[binding] - 1);
        int binding_damage = (int) (((float) durability[binding] / (float) max_durability[binding]) * 256);

        nbt.getCompound("sf_handle").putInt("damage", durability[handle] - 1);
        int handle_damage = (int) (((float) durability[handle] / (float) max_durability[handle]) * 256);

        return 256 - Math.min(Math.min(handle_damage, binding_damage), head_damage);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        for(AttackHandler ah : AttackHandler.attackHandlers.values()) {
            ah.tick();
        }
    }

    @Contract("_ -> new")
    public static int @NotNull [] getDurabilities(@NotNull ItemStack stack) {
        assert stack.getNbt() != null;
        return new int[]{stack.getNbt().getCompound("sf_head").getInt("damage"), stack.getNbt().getCompound("sf_binding").getInt("damage"), stack.getNbt().getCompound("sf_handle").getInt("damage")};
    }
    @Contract("_ -> new")
    public static int @NotNull [] getMaxDurabilities(@NotNull ItemStack stack) {
        assert stack.getNbt() != null;
        return new int[]{stack.getNbt().getCompound("sf_head").getInt("max_damage"), stack.getNbt().getCompound("sf_binding").getInt("max_damage"), stack.getNbt().getCompound("sf_handle").getInt("max_damage")};
    }
    public void breakTool(ItemStack stack, @NotNull PlayerEntity user) {
        user.sendToolBreakStatus(Hand.MAIN_HAND);
    }
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(Arrays.stream(getDurabilities(stack)).anyMatch(i -> i < 0))
            breakTool(stack, (PlayerEntity) attacker);
        if(!stack.getAttributeModifiers(EquipmentSlot.MAINHAND).containsKey(EntityAttributes.GENERIC_ATTACK_SPEED))
            stack.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier("f106b032-3216-4ff6-9919-36cf09d350f5", calcAttackSpeed(stack) - 4, EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
        stack.setDamage(calcDurability(stack));
        return true;
    }
    public static double getWeight(@NotNull ItemStack stack) {
        NbtCompound nbt = stack.getNbt();

        assert nbt != null;
        ToolPart head = ToolParts.TOOL_PARTS_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_head").getString("type"))),
                binding = ToolParts.TOOL_PARTS_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_binding").getString("type"))),
                handle = ToolParts.TOOL_PARTS_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_handle").getString("type")));
        Material mhead = Materials.MATERIAL_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_head").getString("material"))),
                mbinding = Materials.MATERIAL_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_binding").getString("material"))),
                mhandle = Materials.MATERIAL_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_handle").getString("material")));
        assert head != null && mhead != null && binding != null && mbinding != null && handle != null && mhandle != null;
        return (head.weight() * mhead.density()) + (binding.weight() * mbinding.density()) + (0.25 * handle.weight() * mhandle.density());

    }
    @Override
    public float getMiningSpeedMultiplier(@NotNull ItemStack stack, BlockState state) {
        assert stack.getNbt() != null;
        if(Arrays.stream(getDurabilities(stack)).anyMatch((i) -> i == 0))
            return 0;
        MiningSpeedProcessor msp = Objects.requireNonNull(ToolTypes.TOOL_TYPES_REGISTRY.get(new Identifier(stack.getNbt().getString("sf_tool_type")))).miningSpeed();
        return msp.getMiningSpeed(state, Materials.MATERIAL_REGISTRY.get(new Identifier(stack.getNbt().getCompound("sf_head").getString("material"))));
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if(Arrays.stream(getDurabilities(stack)).anyMatch(i -> i < 0))
            breakTool(stack, (PlayerEntity) miner);
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
    public ActionResult useOnBlock(@NotNull ItemUsageContext context) {
        return Objects.requireNonNull(ToolTypes.TOOL_TYPES_REGISTRY.get(Identifier.tryParse(Objects.requireNonNull(Objects.requireNonNull(context.getPlayer()).getMainHandStack().getNbt()).getString("sf_tool_type")))).rightClick().onRightClick(context);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(@NotNull ItemStack stack, @Nullable World world, @NotNull List<Text> tooltip, TooltipContext context) {
        NbtCompound nbt = stack.getNbt();
        assert nbt != null;

        String head_material = new Identifier(nbt.getCompound("sf_head").getString("material")).getPath();
        String head_type = new Identifier(nbt.getCompound("sf_head").getString("type")).getPath();

        String binding_material = new Identifier(nbt.getCompound("sf_binding").getString("material")).getPath();
        String binding_type = new Identifier(nbt.getCompound("sf_binding").getString("type")).getPath();

        String handle_material = new Identifier(nbt.getCompound("sf_handle").getString("material")).getPath();
        String handle_type = new Identifier(nbt.getCompound("sf_handle").getString("type")).getPath();

        ToolType tool_type = ToolTypes.TOOL_TYPES_REGISTRY.get(new Identifier(nbt.getString("sf_tool_type")));
        assert tool_type != null;

        boolean hc = tool_type.hcAttack().isPresent();
        boolean dc = tool_type.dcAttack().isPresent();

        //tooltip.add(new TranslatableText("item.soulforged.tool.type." + type + ".desc").formatted(Formatting.GRAY, Formatting.ITALIC));
        tooltip.add(new TranslatableText("item.soulforged.part." + head_type, new TranslatableText("item.soulforged.tool.material." + head_material))
                .append(" + ").formatted(Formatting.RESET)
                .append(new TranslatableText("item.soulforged.part." + binding_type, new TranslatableText("item.soulforged.tool.material." + binding_material)))
                .append(" + ").formatted(Formatting.RESET)
                .append(new TranslatableText("item.soulforged.part." + handle_type, new TranslatableText("item.soulforged.tool.material." + handle_material)))
                .formatted(Formatting.GOLD)
        );
        tooltip.add(new LiteralText("")
                .append(new TranslatableText("item.soulforged.tool.tooltip.weight", Math.round(getWeight(stack) * 100.0) / 100.0).formatted(Formatting.BLUE, Formatting.BOLD))
                .append(" / ").formatted(Formatting.RESET)
                .append(new TranslatableText("item.soulforged.tool.tooltip.speed", Math.round((1 / calcAttackSpeed(stack)) * 100.0) / 100.0).formatted(Formatting.GREEN, Formatting.BOLD))
                .append(" / ").formatted(Formatting.RESET)
                .append(new TranslatableText("item.soulforged.tool.tooltip.attack", Math.round(calcDamage(stack, 0) * 100.0) / 100.0).formatted(Formatting.RED, Formatting.BOLD))
        );
        tooltip.add(new TranslatableText("item.soulforged.tool.tooltip.defaultattack", new TranslatableText("item.soulforged.tool.tooltip.attacktype." + tool_type.defaultAttack().category().name().toLowerCase()), new TranslatableText("item.soulforged.tool.tooltip.attackdirection." + tool_type.defaultAttack().type().name().toLowerCase())).formatted(Formatting.DARK_PURPLE));
        tooltip.add(new LiteralText("")
                .append(hc ? new TranslatableText("item.soulforged.tool.tooltip.hc.true", new TranslatableText("item.soulforged.tool.tooltip.attacktype." + tool_type.hcAttack().get().category().name().toLowerCase()), new TranslatableText("item.soulforged.tool.tooltip.attackdirection." + tool_type.hcAttack().get().type().name().toLowerCase())).formatted(Formatting.DARK_GREEN) : new TranslatableText("item.soulforged.tool.tooltip.hc.false").formatted(Formatting.DARK_RED, Formatting.BOLD))
                .append("; ")
                .append(dc ? new TranslatableText("item.soulforged.tool.tooltip.dc.true", new TranslatableText("item.soulforged.tool.tooltip.attacktype." + tool_type.dcAttack().get().category().name().toLowerCase()), new TranslatableText("item.soulforged.tool.tooltip.attackdirection." + tool_type.dcAttack().get().type().name().toLowerCase())).formatted(Formatting.DARK_GREEN) : new TranslatableText("item.soulforged.tool.tooltip.dc.false").formatted(Formatting.DARK_RED, Formatting.BOLD))
        );
    }

    @Override
    @Environment(EnvType.CLIENT)
    public Text getName(@NotNull ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        assert nbt != null;

        ToolType type = ToolTypes.TOOL_TYPES_REGISTRY.get(new Identifier(nbt.getString("sf_tool_type")));
        Material mat = Materials.MATERIAL_REGISTRY.get(new Identifier(nbt.getCompound("sf_head").getString("material")));
        TranslatableText matLocalized = new TranslatableText("item.soulforged.tool.material." + Objects.requireNonNull(Materials.MATERIAL_REGISTRY.getId(mat)).getPath());

        return new TranslatableText("item.soulforged.tool.type." + Objects.requireNonNull(ToolTypes.TOOL_TYPES_REGISTRY.getId(type)).getPath(), matLocalized);

    }
    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return false;
    }

}
