package online.maestoso.soulforged.item.tool;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.MiningToolItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import online.maestoso.soulforged.material.SmithingMaterial;
import online.maestoso.soulforged.material.SmithingMaterials;
import online.maestoso.soulforged.util.BlockUtil;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ToolItem extends Item {
    public ToolItem() {
        super(new FabricItemSettings()
                .rarity(Rarity.RARE)
                .fireproof()
                .maxCount(1)
                .customDamage((stack, amount, entity, breakCallback) -> {
                    NbtCompound nbt = stack.getNbt();
                    if (nbt != null) {
                        int durability = nbt.getInt("sf_damage");
                        int max = nbt.getInt("sf_max_damage");
                        int damage = (durability / max) * 256 - 1;
                        nbt.putInt("sf_damage", damage);
                        return damage;
                    }
                    return 0;
                })
        );
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return super.postHit(stack, target, attacker);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        return super.postMine(stack, world, state, pos, miner);
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        SmithingMaterial mat = SmithingMaterials.SMITHING_MATERIALS_REGISTRY.get(new Identifier(miner.getActiveItem().getNbt().getCompound("sf_head").getString("sf_material")));
        return BlockUtil.isMineable(state, mat.getMiningLevel());
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
