package de.reptudn.Entities.AI.Attack;

import de.reptudn.Entities.AI.IBehavior;
import de.reptudn.Entities.AI.Utility.FindTarget;
import de.reptudn.Entities.KingTowerEntity;
import de.reptudn.Entities.TroopCreature;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.network.packet.server.play.EntityAnimationPacket;

public class AttackEnemyCreature implements IBehavior {
    private final double attackRange;
    private final float damage;
    private final long attackCooldownMillis;

    public AttackEnemyCreature(double attackRange, float damage, long attackCooldownMillis) {
        this.damage = damage;
        this.attackRange = attackRange;
        this.attackCooldownMillis = attackCooldownMillis;
    }

    private long lastAttackTime = 0L;

    @Override
    public void tick(EntityCreature entity, long time) {
        EntityCreature ec = FindTarget.closestEnemyEntity(entity);

        if (ec == null)
            return;
        if (ec.getDistance(entity) > attackRange)
            return;
        long now = System.currentTimeMillis();
        if (now - lastAttackTime < attackCooldownMillis)
            return;

        ec.getViewers().forEach(player -> {
            player.sendPacket(
                    new EntityAnimationPacket(entity.getEntityId(), EntityAnimationPacket.Animation.SWING_MAIN_ARM));
        });
        if (ec instanceof TroopCreature tc) {
            tc.damage(damage);
        } else if (ec instanceof KingTowerEntity te) {
            te.damage(damage);
        } else {
            ec.setHealth(ec.getHealth() - damage);
            if (ec.getHealth() <= 0) {
                ec.remove();
            }
        }
        lastAttackTime = now;
    }
}
