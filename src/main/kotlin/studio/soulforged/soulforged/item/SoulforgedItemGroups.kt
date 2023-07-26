package studio.soulforged.soulforged.item

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.text.Text
import studio.soulforged.soulforged.Soulforged.id

object SoulforgedItemGroups {
    val GROUP: ItemGroup = FabricItemGroup.builder()
        .icon { ItemStack(SoulforgedItems.MERCURY_BOTTLE) }
        .name(Text.translatable("soulforged.name"))
        .entries {_, content ->
            content.addItem(SoulforgedItems.LEAD_ORE)
            content.addItem(SoulforgedItems.CINNABAR_ORE)
            content.addItem(SoulforgedItems.OSMIUM_ORE)
            content.addItem(SoulforgedItems.ALUMINUM_ORE)
            content.addItem(SoulforgedItems.END_ALUMINUM_ORE)
            content.addItem(SoulforgedItems.TITANIUM_ORE)
            content.addItem(SoulforgedItems.END_TITANIUM_ORE)
            content.addItem(SoulforgedItems.CORUNDUM_ORE)
            content.addItem(SoulforgedItems.ENDICITE_ORE)

            content.addItem(SoulforgedItems.RAW_LEAD)
            content.addItem(SoulforgedItems.RAW_CINNABAR)
            content.addItem(SoulforgedItems.RAW_OSMIUM)
            content.addItem(SoulforgedItems.RAW_ALUMINUM)
            content.addItem(SoulforgedItems.RAW_TITANIUM)

            content.addItem(SoulforgedItems.SAPPHIRE)
            content.addItem(SoulforgedItems.RUBY)
            content.addItem(SoulforgedItems.PADPARADSCHA)
            content.addItem(SoulforgedItems.STAR_GEM)
            content.addItem(SoulforgedItems.ENDICITE)

            content.addItem(SoulforgedItems.LEAD_INGOT)
            content.addItem(SoulforgedItems.OSMIUM_INGOT)
            content.addItem(SoulforgedItems.ALUMINUM_INGOT)
            content.addItem(SoulforgedItems.TITANIUM_INGOT)

            content.addItem(SoulforgedItems.CINNABAR_DUST_BOTTLE)
            content.addItem(SoulforgedItems.MERCURY_BOTTLE)

            content.addItem(SoulforgedItems.WITHER_BONE)
            content.addItem(SoulforgedItems.MONSTER_LEATHER)
            content.addItem(SoulforgedItems.SCALES)
            content.addItem(SoulforgedItems.SPIDER_WEAVE)
            content.addItem(SoulforgedItems.SLIME_WEAVE)

            content.addItem(SoulforgedItems.WORKSTATION)
            content.addItem(SoulforgedItems.DEEPSLATE_FORGE_CONTROLLER)
            content.addItem(SoulforgedItems.DEEPSLATE_FORGE_BURNER)
            content.addItem(SoulforgedItems.DEEPSLATE_FORGE_BUNKER)
        }
        .build()
    internal fun init() {
        Registry.register(Registries.ITEM_GROUP, "soulforged".id(), GROUP)
    }
}