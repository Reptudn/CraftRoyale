package de.reptudn.Entities.AI.Utility;

import de.reptudn.Entities.ATower;
import net.minestom.server.entity.EntityCreature;

public class FindTarget {
	public static EntityCreature closestEnemyTroop(EntityCreature origin) {
		EntityCreature closest = origin.getInstance().getEntities().stream()
				.filter(e -> e instanceof EntityCreature && e != origin)
				.map(e -> (EntityCreature) e)
				.min((e1, e2) -> {
					double dist1 = e1.getPosition().distance(origin.getPosition());
					double dist2 = e2.getPosition().distance(origin.getPosition());
					return Double.compare(dist1, dist2);
				})
				.orElse(null);
		return closest;
	}

	public static ATower closestEnemyTower(EntityCreature origin) {
		// Implementation for finding the closest enemy tower
		return null;
	}
}
