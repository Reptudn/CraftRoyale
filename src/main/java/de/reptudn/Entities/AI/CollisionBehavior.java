package de.reptudn.Entities.AI;

import de.reptudn.Entities.TroopCreature;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityCreature;

import java.util.List;

public class CollisionBehavior implements IBehavior {
	private final double collisionRadius;
	private final double pushForce;

	public CollisionBehavior(double collisionRadius, double pushForce) {
		this.collisionRadius = collisionRadius;
		this.pushForce = pushForce;
	}

	public CollisionBehavior() {
		this.collisionRadius = 0.8;
		this.pushForce = 1.0;
	}

	@Override
	public void tick(EntityCreature entity, long time) {

		Pos currentPos = entity.getPosition();
		List<Entity> nearbyEntities = (List<Entity>) entity.getInstance().getNearbyEntities(currentPos,
				collisionRadius * 2);

		Vec pushVector = Vec.ZERO;

		for (Entity nearbyEntity : nearbyEntities) {
			if (nearbyEntity == entity || !(nearbyEntity instanceof TroopCreature))
				continue;

			Pos otherPos = nearbyEntity.getPosition();
			double distance = currentPos.distance(otherPos);

			if (distance < collisionRadius && distance > 0) {
				Vec direction = currentPos.sub(otherPos).asVec().normalize();
				double pushStrength = (collisionRadius - distance) / collisionRadius * pushForce;
				pushVector = pushVector.add(direction.mul(pushStrength));
			}
		}

		if (!pushVector.isZero()) {
			Pos newPos = currentPos.add(pushVector);
			if (isValidPosition(entity, newPos)) {
				entity.teleport(newPos);
			}
		}
	}

	private boolean isValidPosition(EntityCreature entity, Pos pos) {
		return entity.getInstance().getBlock(pos).isAir() &&
				entity.getInstance().getBlock(pos.add(0, 1, 0)).isAir();
	}
}