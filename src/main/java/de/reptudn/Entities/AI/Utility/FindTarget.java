package de.reptudn.Entities.AI.Utility;

import de.reptudn.Entities.ATower;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.Player;

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

	public static Player closestPlayer(EntityCreature origin) {
		Player closest = origin.getInstance().getPlayers().stream()
				.min((p1, p2) -> {
					double dist1 = p1.getPosition().distance(origin.getPosition());
					double dist2 = p2.getPosition().distance(origin.getPosition());
					return Double.compare(dist1, dist2);
				})
				.orElse(null);
		return closest;
	}

	public static Player closestPlayerWithinDistance(EntityCreature origin, double maxDistance) {
		Player closest = origin.getInstance().getPlayers().stream()
				.filter(p -> p.getPosition().distance(origin.getPosition()) <= maxDistance)
				.min((p1, p2) -> {
					double dist1 = p1.getPosition().distance(origin.getPosition());
					double dist2 = p2.getPosition().distance(origin.getPosition());
					return Double.compare(dist1, dist2);
				})
				.orElse(null);
		return closest;
	}
}
