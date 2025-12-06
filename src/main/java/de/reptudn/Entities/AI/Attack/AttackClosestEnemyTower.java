package de.reptudn.Entities.AI.Attack;

import de.reptudn.Entities.AI.IBehavior;
import de.reptudn.Entities.AI.Utility.FindTarget;
import de.reptudn.Entities.KingTowerEntity;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.network.packet.server.play.EntityAnimationPacket;

public class AttackClosestEnemyTower implements IBehavior {

	private final double attackRange;
	private final float damage;
	private final long attackCooldownMillis;

	public AttackClosestEnemyTower(double attackRange, float damage, long attackCooldownMillis) {
		this.damage = damage;
		this.attackRange = attackRange;
		this.attackCooldownMillis = attackCooldownMillis;
	}

	private long lastAttackTime = 0L;

	@Override
	public void tick(EntityCreature entity, long time) {
		KingTowerEntity tower = FindTarget.closestEnemyTower(entity);

		if (tower == null)
			return;
		if (tower.getDistance(entity) > attackRange)
			return;
		long now = System.currentTimeMillis();
		if (now - lastAttackTime < attackCooldownMillis)
			return;

		tower.getViewers().forEach(player -> {
			player.sendPacket(
					new EntityAnimationPacket(entity.getEntityId(), EntityAnimationPacket.Animation.SWING_MAIN_ARM));
		});

		tower.damage(damage);

		lastAttackTime = now;

	}

}
