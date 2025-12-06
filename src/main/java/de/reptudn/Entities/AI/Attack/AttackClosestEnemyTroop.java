package de.reptudn.Entities.AI.Attack;

import de.reptudn.Entities.AI.IBehavior;
import de.reptudn.Entities.AI.Utility.FindTarget;
import de.reptudn.Entities.TroopCreature;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.network.packet.server.play.EntityAnimationPacket;

public class AttackClosestEnemyTroop implements IBehavior {
	private final double attackRange;
	private final float damage;
	private final long attackCooldownMillis;

	public AttackClosestEnemyTroop(double attackRange, float damage, long attackCooldownMillis) {
		this.damage = damage;
		this.attackRange = attackRange;
		this.attackCooldownMillis = attackCooldownMillis;
	}

	private long lastAttackTime = 0L;

	@Override
	public void tick(EntityCreature entity, long time) {
		TroopCreature troop = FindTarget.closestEnemyTroop(entity);

		if (troop == null)
			return;
		if (troop.getDistance(entity) > attackRange)
			return;
		long now = System.currentTimeMillis();
		if (now - lastAttackTime < attackCooldownMillis)
			return;

		troop.getViewers().forEach(player -> {
			player.sendPacket(
					new EntityAnimationPacket(entity.getEntityId(), EntityAnimationPacket.Animation.SWING_MAIN_ARM));
		});

		troop.damage(damage);

		lastAttackTime = now;

	}

}
