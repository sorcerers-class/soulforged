package online.maestoso.soulforged.item.tool.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import online.maestoso.soulforged.item.tool.ToolItem;
import online.maestoso.soulforgedcombatdebugger.gui.CombatDebuggerClientUI;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AttackHandler {
    public static ConcurrentHashMap<UUID, AttackHandler> attackHandlers = new ConcurrentHashMap<>();

    private final ServerPlayerEntity client;
    private int tickCounter = 0;
    private boolean finished = false;
    private final float attackCooldown;
    private Entity target;
    private final Vector<Integer> packets = new Vector<>();
    public AttackHandler(ServerPlayerEntity client) {
        this.client = client;
        this.addPacket(1);
        attackCooldown = client.getAttackCooldownProgress(0.0f);
    }
    public void tick() {
        tickCounter += 1;
        if(tickCounter == 30) {
            onFinish();
            finished = true;
        }
    }
    public void addPacket(int action) {
        packets.add(action);

    }
    public void onFinish() {
        if(target != null)
            target.damage(DamageSource.player(client), (float) ToolItem.calcDamage(client.getMainHandStack(), packets.size(), client, target, attackCooldown));
        CombatDebuggerClientUI.attackType = packets.size();
        client.getMainHandStack().postHit((LivingEntity) target, client);
    }
    public boolean getFinished() {
        return finished;
    }
    public void setTarget(Entity target) {
        this.target = target;
    }
    public static void addOrModifyAttackHandler(ServerPlayerEntity client, PacketByteBuf buf) {
        if(attackHandlers.containsKey(client.getUuid()) && !attackHandlers.get(client.getUuid()).getFinished()) {
            AttackHandler attackHandler = attackHandlers.get(client.getUuid());
            int button = buf.readInt();
            if(button == 0) {
                int action = buf.readInt();
                attackHandler.addPacket(action);
            }
        } else {
            int button = buf.readInt();
            if(button == 0)
                attackHandlers.put(client.getUuid(), new AttackHandler(client));
        }
    }
}
