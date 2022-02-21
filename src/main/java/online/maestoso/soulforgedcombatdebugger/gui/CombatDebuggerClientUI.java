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
import online.maestoso.soulforged.item.tool.ToolItem;
import online.maestoso.soulforged.item.tool.ToolType;
import online.maestoso.soulforged.item.tool.ToolTypes;
import online.maestoso.soulforged.item.tool.combat.AttackProperties;
import online.maestoso.soulforged.item.tool.part.ToolPart;
import online.maestoso.soulforged.item.tool.part.ToolParts;
import online.maestoso.soulforged.material.Material;
import online.maestoso.soulforged.material.Materials;

import java.util.Arrays;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public class CombatDebuggerClientUI {
    private static boolean showToolTypeDropdown = false,
                            showHeadMaterialDropdown = false,
                            showBindingMaterialDropdown = false,
                            showHandleMaterialDropdown = false,
                            showHeadTypeDropdown = false,
                            showBindingTypeDropdown = false,
                            showHandleTypeDropdown = false,
                            showDamageCalcDropdown = false,
                            showDcAttackCalc = false,
                            showHcAttackCalc = false;
    public static int lastPacket = -1;
    public static int packetCounter = 0;
    public static int attackType = 0;
    public static void render(float delta) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            ImGui.setNextWindowSizeConstraints(-1, -1, 500, 500);
            ItemStack stack = player.getMainHandStack();
            if (stack.getItem().equals(SoulforgedItems.TOOL)) {
                ImGui.begin("SCD v1 | Current Tool", ImGuiWindowFlags.AlwaysVerticalScrollbar);
                ImGui.text(stack.getName().getString());
                assert stack.getNbt() != null;
                NbtCompound nbt = stack.getNbt(),
                        head = nbt.getCompound("sf_head"),
                        binding = nbt.getCompound("sf_binding"),
                        handle = nbt.getCompound("sf_handle");

                if (ImGui.button(String.format("Type: %s", nbt.getString("sf_tool_type"))))
                    showToolTypeDropdown ^= true;
                if (showToolTypeDropdown) {
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
                if (ImGui.button(String.format("Head: %s", head.getString("material"))))
                    showHeadMaterialDropdown ^= true;
                if (showHeadMaterialDropdown) {
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
                if (ImGui.button(String.format("Binding: %s", binding.getString("material"))))
                    showBindingMaterialDropdown ^= true;
                if (showBindingMaterialDropdown) {
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
                if (ImGui.button(String.format("Handle: %s", handle.getString("material"))))
                    showHandleMaterialDropdown ^= true;
                if (showHandleMaterialDropdown) {
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
                if (ImGui.button(String.format("Head: %s", head.getString("type"))))
                    showHeadTypeDropdown ^= true;
                if (showHeadTypeDropdown) {
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
                if (ImGui.button(String.format("Binding: %s", binding.getString("type"))))
                    showBindingTypeDropdown ^= true;
                if (showBindingTypeDropdown) {
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
                if (ImGui.button(String.format("Handle: %s", handle.getString("type"))))
                    showHandleTypeDropdown ^= true;
                if (showHandleTypeDropdown) {
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
                ImGui.end();
                ImGui.setNextWindowSizeConstraints(-1.0f, -1.0f, 500.0f, 500f);
                ImGui.begin("SCD v1 | Melee", ImGuiWindowFlags.AlwaysHorizontalScrollbar);
                ImGui.text(String.format("Attack cooldown: %f", player.getAttackCooldownProgress(0.0f)));
                ImGui.text(String.format("Cooldown ratio: %f", Math.pow(player.getAttackCooldownProgress(0.0f), 4)));

                if (ImGui.button("Damage calc: "))
                    showDamageCalcDropdown ^= true;

                if (Arrays.stream(ToolItem.getDurabilities(stack)).anyMatch((i) -> i == 0))
                    ImGui.text("Broken tool will have 0 damage!");
                ToolPart phead = ToolParts.TOOL_PARTS_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_head").getString("type"))),
                        pbinding = ToolParts.TOOL_PARTS_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_binding").getString("type"))),
                        phandle = ToolParts.TOOL_PARTS_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_handle").getString("type")));
                Material mhead = Materials.MATERIAL_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_head").getString("material"))),
                        mbinding = Materials.MATERIAL_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_binding").getString("material"))),
                        mhandle = Materials.MATERIAL_REGISTRY.get(Identifier.tryParse(nbt.getCompound("sf_handle").getString("material")));
                assert mhead != null;
                double head_edgeholding = mhead.edgeholding();
                double head_hardness = mhead.hardness();

                ToolType type = ToolTypes.TOOL_TYPES_REGISTRY.get(Identifier.tryParse(nbt.getString("sf_tool_type")));
                assert type != null;
                AttackProperties ap = type.defaultAttack();
                if (showDcAttackCalc)
                    ap = type.dcAttack().orElse(type.defaultAttack());
                else if (showHcAttackCalc)
                    ap = type.hcAttack().orElse(type.defaultAttack());

                double piercing_damage = ap.piercingDamage();
                double total_piercing_damage = ((head_edgeholding + (head_hardness * 0.75)) / 2) * piercing_damage;

                assert phead != null;
                double head_weight = phead.weight() * mhead.density(),
                        binding_weight = Objects.requireNonNull(pbinding).weight() * Objects.requireNonNull(mbinding).density(),
                        handle_weight = Objects.requireNonNull(phandle).weight() * Objects.requireNonNull(mhandle).density();

                double effective_weight = head_weight + binding_weight + (0.25 * handle_weight);
                double total_blunt_damage = (((effective_weight / 100) + (head_hardness * 0.25)) * ap.bluntDamage()) * 0.8;
                double total_damage = total_piercing_damage + total_blunt_damage;
                if (showDamageCalcDropdown) {
                    if (ImGui.button(showDcAttackCalc ? "Hide DC Attack" : "Show DC Attack"))
                        showDcAttackCalc ^= true;
                    if (ImGui.button(showHcAttackCalc ? "Hide HC Attack" : "Show HC Attack"))
                        showHcAttackCalc ^= true;
                    ImGui.text(String.format("""
                                    Total Piercing Damage:
                                    \t((Edgeholding + (Head Hardness * 0.75)) / 2) * Piercing Damage
                                    \t((%f + (%f * 0.75)) / 2) * %f = %f
                                    Total Blunt Damage:
                                    \t((((Head Weight + Binding Weight + (0.25 * Handle Weight))/100) + (Head Hardness * 0.25))*Blunt Damage Multiplier) * 0.8
                                    \t((((%f + %f + (0.25 * %f))/100) + (%f * 0.25)) * %f) * 0.8 = %f
                                    Total Damage: %f
                                    With cooldown: Total Damage * Cooldown ^ 4
                                    %f * %f^4 = %f
                                    """,
                            head_edgeholding,
                            head_hardness,
                            piercing_damage,
                            total_piercing_damage,
                            head_weight,
                            binding_weight,
                            handle_weight,
                            head_hardness,
                            ap.bluntDamage(),
                            total_blunt_damage,
                            total_damage,
                            total_damage,
                            Math.pow(player.getAttackCooldownProgress(0.0f), 4),
                            total_damage * Math.pow(player.getAttackCooldownProgress(0.0f), 4)
                    ));
                } else {
                    ImGui.text(String.format("""
                                    Total Damage: %f
                                    With Cooldown: %f
                                    """,
                            total_damage,
                            total_damage * Math.pow(player.getAttackCooldownProgress(0.0f), 4)
                    ));
                }
                ImGui.text(String.format("Attack type: %d", attackType));
                ImGui.end();
                ImGui.begin("SCD v1 | Mouse");
                ImGui.text(String.format("""
                        Last Packet Action: %d
                        Packet Count: %d
                        """,
                        lastPacket,
                        packetCounter
                ));
                ImGui.end();
                ImGui.begin("SCD v1 | Move Crits");
                ImGui.text(String.format("""
                        Facing: %s
                        Moving: %s
                        Adjusted Velocity: %s
                        """,
                        player.getYaw() % 360.0f,
                        player.getVelocity(),
                        player.getVelocity().rotateY((float) Math.toRadians(player.getYaw() % 360.0f))
                ));
                ImGui.end();
            }
        }
    }
}
