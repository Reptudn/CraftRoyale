package de.reptudn.Entities.AI.Attack;

import de.reptudn.Entities.AI.IBehavior;
import de.reptudn.Entities.AI.Utility.FindTarget;
import de.reptudn.Entities.TroopCreature;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.Player;
import net.minestom.server.network.packet.server.play.EntityAnimationPacket;

import java.time.Instant;

public class AttackEntityCreature implements IBehavior {
    private final double attackRange;
    private final float damage;
    private final long attackCooldownMillis;
    public AttackEntityCreature(double attackRange, float damage, long attackCooldownMillis) {
        this.damage = damage;
        this.attackRange = attackRange;
        this.attackCooldownMillis = attackCooldownMillis;
    }

    private long lastAttackTime = 0L;

    @Override
    public void tick(EntityCreature entity, long time) {
        EntityCreature ec = FindTarget.closestEntity(entity);
        if (ec == null) return;
        if (ec.getDistance(entity) > attackRange) return;
        long now = System.currentTimeMillis();
        if (now - lastAttackTime < attackCooldownMillis) return;

        ec.getViewers().forEach(player -> {
            player.sendPacket(new EntityAnimationPacket(entity.getEntityId(), EntityAnimationPacket.Animation.SWING_MAIN_ARM));
        });
        if (ec instanceof TroopCreature tc) {
            tc.damage(damage);
        } else {
            ec.setHealth(ec.getHealth() - damage);
            if (ec.getHealth() <= 0) {
                ec.remove();
            }
        }
        lastAttackTime = now;
    }
}
