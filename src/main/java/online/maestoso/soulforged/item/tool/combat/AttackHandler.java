package online.maestoso.soulforged.item.tool.combat;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import online.maestoso.soulforged.item.tool.ToolItem;
import online.maestoso.soulforgedcombatdebugger.gui.CombatDebuggerClientUI;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AttackHandler {
    public static ConcurrentHashMap<UUID, AttackHandler> attackHandlers = new ConcurrentHashMap<>();

    private MinecraftServer server;
    private ServerPlayerEntity client;
    private ServerPlayNetworkHandler handler;
    private PacketByteBuf buf;
    private PacketSender responseSender;
    private int tickCounter = 0;
    private boolean finished = false;
    private float attackCooldown = 0.0f;
    private Entity target;
    private Vector<Integer> packets = new Vector<>();
    public AttackHandler(MinecraftServer server, ServerPlayerEntity client, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        this.server = server;
        this.client = client;
        this.handler = handler;
        this.buf = buf;
        this.responseSender = responseSender;
        this.addPacket(1);
        attackCooldown = client.getAttackCooldownProgress(0.0f);
    }
    public void tick() {
        tickCounter += 1;
        if(tickCounter == 60) {
            onFinish();
            finished = true;
        }
    }
    public void addPacket(int action) {
        packets.add(action);
    }
    public void onFinish() {
        if(target != null)
            target.damage(DamageSource.player(client), (float) ToolItem.calcDamage(client.getMainHandStack(), packets.size()));
        CombatDebuggerClientUI.attackType = packets.size();
    }
    public boolean getFinished() {
        return finished;
    }
    public void setTarget(Entity target) {
        this.target = target;
    }
    public static void addOrModifyAttackHandler(MinecraftServer server, ServerPlayerEntity client, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        if(attackHandlers.containsKey(client.getUuid()) && !attackHandlers.get(client.getUuid()).getFinished()) {
            AttackHandler attackHandler = attackHandlers.get(client.getUuid());
            int button = buf.readInt();
            int action = buf.readInt();
            attackHandler.addPacket(action);
        } else {
            attackHandlers.put(client.getUuid(), new AttackHandler(server, client, handler, buf, responseSender));
        }
    }
}
