package online.maestoso.soulforgedcombatdebugger.gui;

import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiWindowFlags;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import online.maestoso.soulforged.item.SoulforgedItems;
import online.maestoso.soulforged.item.tool.ToolType;
import online.maestoso.soulforged.item.tool.ToolTypes;
import online.maestoso.soulforged.item.tool.combat.AttackProperties;
import online.maestoso.soulforged.item.tool.part.ToolPart;
import online.maestoso.soulforged.item.tool.part.ToolParts;
import online.maestoso.soulforged.material.Material;
import online.maestoso.soulforged.material.Materials;

@Environment(EnvType.CLIENT)
public class CombatDebuggerClientUI {
    private static boolean showToolTypeDropdown = false,
                            showHeadMaterialDropdown = false,
                            showBindingMaterialDropdown = false,
                            showHandleMaterialDropdown = false,
                            showHeadTypeDropdown = false,
                            showBindingTypeDropdown = false,
                            showHandleTypeDropdown = false;
    public static void render(float delta) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if(player != null) {
            ImGui.setNextWindowPos(10, 10, ImGuiCond.Once);
            ImGui.setNextWindowSizeConstraints(-1, -1, 500, 500);
            ImGui.begin("SCD v1 | Current Tool", ImGuiWindowFlags.AlwaysVerticalScrollbar);
            ItemStack stack = player.getMainHandStack();
            if(stack.getItem().equals(SoulforgedItems.TOOL)) {
                ImGui.text(stack.getName().getString());
                assert stack.getNbt() != null;
                NbtCompound nbt = stack.getNbt(),
                        head = nbt.getCompound("sf_head"),
                        binding = nbt.getCompound("sf_binding"),
                        handle = nbt.getCompound("sf_handle");

                if(ImGui.button(String.format("Type: %s", nbt.getString("sf_tool_type"))))
                    showToolTypeDropdown ^= true;
                if(showToolTypeDropdown) {
                    ToolType type = ToolTypes.TOOL_TYPES_REGISTRY.get(new Identifier(nbt.getString("sf_tool_type")));
                    assert type != null;
                    AttackProperties apDefault = type.defaultAttack();
                    ImGui.text(String.format(
                                    """
                                    Default Attack:
                                    \tPiercing Damage: %f
                                    \tBlunt Damage: %f
                                    \tFinesse: %f
                                    \tSpeed: %f
                                    \tPiercing: %f
                                    \tCategory: %s
                                    \tCrit Type: %s
                                    """,
                            apDefault.piercingDamage(),
                            apDefault.bluntDamage(),
                            apDefault.finesse(),
                            apDefault.speed(),
                            apDefault.piercing(),
                            apDefault.category().toString().toLowerCase(),
                            apDefault.type().toString().toLowerCase()));

                    AttackProperties apDc = type.dcAttack().orElse(null);
                    ImGui.text(apDc != null ? String.format(
                                    """
                                    DC Attack:
                                    \tPiercing Damage: %f
                                    \tBlunt Damage: %f
                                    \tFinesse: %f
                                    \tSpeed: %f
                                    \tPiercing: %f
                                    \tCategory: %s
                                    \tCrit Type: %s
                                    """,
                            apDc.piercingDamage(),
                            apDc.bluntDamage(),
                            apDc.finesse(), apDc.speed(),
                            apDc.piercing(),
                            apDc.category().toString().toLowerCase(),
                            apDc.type().toString().toLowerCase())
                            : "DC Attack: None");
                    AttackProperties apHc = type.hcAttack().orElse(null);
                    ImGui.text(apHc != null ? String.format(
                                    """
                                    DC Attack:
                                    \tPiercing Damage: %f
                                    \tBlunt Damage: %f
                                    \tFinesse: %f
                                    \tSpeed: %f
                                    \tPiercing: %f
                                    \tCategory: %s
                                    \tCrit Type: %s
                                    """,
                            apHc.piercingDamage(),
                            apHc.bluntDamage(),
                            apHc.finesse(),
                            apHc.speed(),
                            apHc.piercing(),
                            apHc.category().toString().toLowerCase(),
                            apHc.type().toString().toLowerCase())
                            : "HC Attack: None"
                    );
                }
                ImGui.text("Materials:");
                if(ImGui.button(String.format("Head: %s", head.getString("material"))))
                    showHeadMaterialDropdown ^= true;
                if(showHeadMaterialDropdown) {
                    Material headMat = Materials.MATERIAL_REGISTRY.get(new Identifier(head.getString("material")));
                    assert headMat != null;
                    ImGui.text(String.format(
                            """
                            \tHardness: %f
                            \tEdgeholding: %f
                            \tWorkability: %d
                            \tDensity: %d
                            \tHeat: %d
                            \tPadding: %d
                            \tMining Level: %d
                            \tMining Speed: %f
                            \tTool Material: %b
                            \tArmor Material: %b
                            \tCorundum Classifier: %s
                            """,
                            headMat.hardness(),
                            headMat.edgeholding(),
                            headMat.workability(),
                            headMat.density(), headMat.heat(),
                            headMat.padding(),
                            headMat.miningLevel(),
                            headMat.miningSpeed(),
                            headMat.canIntoTool(),
                            headMat.canIntoArmor(),
                            headMat.classifier().isPresent() ? headMat.classifier().get() : "None"
                    ));
                }
                if(ImGui.button(String.format("Binding: %s", binding.getString("material"))))
                    showBindingMaterialDropdown ^= true;
                if(showBindingMaterialDropdown) {
                    Material bindingMat = Materials.MATERIAL_REGISTRY.get(new Identifier(binding.getString("material")));
                    assert bindingMat != null;
                    ImGui.text(String.format(
                            """
                            \tHardness: %f
                            \tEdgeholding: %f
                            \tWorkability: %d
                            \tDensity: %d
                            \tHeat: %d
                            \tPadding: %d
                            \tMining Level: %d
                            \tMining Speed: %f
                            \tTool Material: %b
                            \tArmor Material: %b
                            \tCorundum Classifier: %s
                            """,
                            bindingMat.hardness(),
                            bindingMat.edgeholding(),
                            bindingMat.workability(),
                            bindingMat.density(),
                            bindingMat.heat(),
                            bindingMat.padding(),
                            bindingMat.miningLevel(),
                            bindingMat.miningSpeed(),
                            bindingMat.canIntoTool(),
                            bindingMat.canIntoArmor(),
                            bindingMat.classifier().isPresent() ? bindingMat.classifier().get() : "None"
                    ));
                }
                if(ImGui.button(String.format("Handle: %s", handle.getString("material"))))
                    showHandleMaterialDropdown ^= true;
                if(showHandleMaterialDropdown) {
                    Material handleMat = Materials.MATERIAL_REGISTRY.get(new Identifier(handle.getString("material")));
                    assert handleMat != null;
                    ImGui.text(String.format(
                            """
                            \tHardness: %f
                            \tEdgeholding: %f
                            \tWorkability: %d
                            \tDensity: %d
                            \tHeat: %d
                            \tPadding: %d
                            \tMining Level: %d
                            \tMining Speed: %f
                            \tTool Material: %b
                            \tArmor Material: %b
                            \tCorundum Classifier: %s
                            """,
                            handleMat.hardness(),
                            handleMat.edgeholding(),
                            handleMat.workability(),
                            handleMat.density(),
                            handleMat.heat(),
                            handleMat.padding(),
                            handleMat.miningLevel(),
                            handleMat.miningSpeed(),
                            handleMat.canIntoTool(),
                            handleMat.canIntoArmor(),
                            handleMat.classifier().isPresent() ? handleMat.classifier().get() : "None"
                    ));
                }
                ImGui.text("Part Types:");
                if(ImGui.button(String.format("Head: %s", head.getString("type"))))
                    showHeadTypeDropdown ^= true;
                if(showHeadTypeDropdown) {
                    ToolPart headPart = ToolParts.TOOL_PARTS_REGISTRY.get(new Identifier(head.getString("type")));
                    assert headPart != null;
                    ImGui.text(String.format(
                            """
                            \tWeight: %f
                            \tDurability: %f
                            """,
                            headPart.weight(),
                            headPart.durability()));
                }
                if(ImGui.button(String.format("Binding: %s", binding.getString("type"))))
                    showBindingTypeDropdown ^= true;
                if(showBindingTypeDropdown) {
                    ToolPart bindingPart = ToolParts.TOOL_PARTS_REGISTRY.get(new Identifier(binding.getString("type")));
                    assert bindingPart != null;
                    ImGui.text(String.format(
                            """
                            \tWeight: %f
                            \tDurability: %f
                            """,
                            bindingPart.weight(),
                            bindingPart.durability()));
                }
                if(ImGui.button(String.format("Handle: %s", handle.getString("type"))))
                    showHandleTypeDropdown ^= true;
                if(showHandleTypeDropdown) {
                    ToolPart part = ToolParts.TOOL_PARTS_REGISTRY.get(new Identifier(handle.getString("type")));
                    assert part != null;
                    ImGui.text(String.format(
                            """
                            \tWeight: %f
                            \tDurability: %f
                            """,
                            part.weight(),
                            part.durability()));
                }
            }
            ImGui.end();

            ImGui.setNextWindowPos(500, 10, ImGuiCond.Once);
            ImGui.setNextWindowSizeConstraints(-1.0f, -1.0f, 500.0f, -1.0f);
            ImGui.begin("SCD v1 | Melee", ImGuiWindowFlags.AlwaysAutoResize + ImGuiWindowFlags.AlwaysVerticalScrollbar);
            ImGui.text(String.format("Attack cooldown: %f", player.getAttackCooldownProgress(0.0f)));
            ImGui.text(String.format("Cooldown ratio: %f", Math.pow(player.getAttackCooldownProgress(0.0f), 4)));

            ImGui.end();
        }
    }
}
