package de.reptudn.Entities.AI.Utility;

import de.reptudn.Entities.TowerEntity;
import de.reptudn.Entities.TroopCreature;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.Player;

public class FindTarget {
    public static TroopCreature closestEnemyTroop(EntityCreature origin) {
        return origin.getInstance().getEntities().stream()
                .filter(e -> e instanceof TroopCreature && e != origin)
                .map(e -> (TroopCreature) e)
                .min((e1, e2) -> {
                    double dist1 = e1.getPosition().distance(origin.getPosition());
                    double dist2 = e2.getPosition().distance(origin.getPosition());
                    return Double.compare(dist1, dist2);
                })
                .orElse(null);
    }

    public static TowerEntity closestEnemyTower(EntityCreature origin) {
        return origin.getInstance().getEntities().stream()
                .filter(e -> e instanceof TowerEntity && e != origin
                        && ((EntityCreature) e).getTeam() != origin.getTeam())
                .map(e -> (TowerEntity) e)
                .min((e1, e2) -> {
                    double dist1 = e1.getPosition().distance(origin.getPosition());
                    double dist2 = e2.getPosition().distance(origin.getPosition());
                    return Double.compare(dist1, dist2);
                })
                .orElse(null);
    }

    public static Player closestPlayer(EntityCreature origin) {
        return origin.getInstance().getPlayers().stream()
                .min((p1, p2) -> {
                    double dist1 = p1.getPosition().distance(origin.getPosition());
                    double dist2 = p2.getPosition().distance(origin.getPosition());
                    return Double.compare(dist1, dist2);
                })
                .orElse(null);
    }

    public static Player closestPlayerWithinDistance(EntityCreature origin, double maxDistance) {
        return origin.getInstance().getPlayers().stream()
                .filter(p -> p.getPosition().distance(origin.getPosition()) <= maxDistance)
                .min((p1, p2) -> {
                    double dist1 = p1.getPosition().distance(origin.getPosition());
                    double dist2 = p2.getPosition().distance(origin.getPosition());
                    return Double.compare(dist1, dist2);
                })
                .orElse(null);
    }

    public static EntityCreature closestEntity(EntityCreature origin) {
        return (EntityCreature) origin.getInstance().getEntities().stream()
                .filter(p -> p != origin)
                .filter(p -> p instanceof EntityCreature)
                .min((p1, p2) -> {
                    double dist1 = p1.getPosition().distance(origin.getPosition());
                    double dist2 = p2.getPosition().distance(origin.getPosition());
                    return Double.compare(dist1, dist2);
                })
                .orElse(null);
    }

    public static EntityCreature closestEntityWithinDistance(EntityCreature origin, double maxDistance) {
        return (EntityCreature) origin.getInstance().getEntities().stream()
                .filter(p -> p.getPosition().distance(origin.getPosition()) <= maxDistance)
                .filter(p -> p != origin)
                .filter(p -> p instanceof EntityCreature)
                .min((p1, p2) -> {
                    double dist1 = p1.getPosition().distance(origin.getPosition());
                    double dist2 = p2.getPosition().distance(origin.getPosition());
                    return Double.compare(dist1, dist2);
                })
                .orElse(null);
    }

    public static TroopCreature closestTroop(EntityCreature origin) {
        return (TroopCreature) origin.getInstance().getEntities().stream()
                .filter(p -> p != origin)
                .filter(p -> p instanceof TroopCreature)
                .min((p1, p2) -> {
                    double dist1 = p1.getPosition().distance(origin.getPosition());
                    double dist2 = p2.getPosition().distance(origin.getPosition());
                    return Double.compare(dist1, dist2);
                })
                .orElse(null);
    }

    public static TroopCreature closestTroopWithinDistance(EntityCreature origin, double maxDistance) {
        return (TroopCreature) origin.getInstance().getEntities().stream()
                .filter(p -> p.getPosition().distance(origin.getPosition()) <= maxDistance)
                .filter(p -> p != origin)
                .filter(p -> p instanceof TroopCreature)
                .min((p1, p2) -> {
                    double dist1 = p1.getPosition().distance(origin.getPosition());
                    double dist2 = p2.getPosition().distance(origin.getPosition());
                    return Double.compare(dist1, dist2);
                })
                .orElse(null);
    }

    public static TowerEntity closestEnemyDefense(EntityCreature origin) {
        return (TowerEntity) origin.getInstance().getEntities().stream()
                .filter(p -> p != origin)
                .filter(p -> p instanceof TowerEntity)
                .filter(p -> ((EntityCreature) p).getTeam() != origin.getTeam())
                .min((p1, p2) -> {
                    double dist1 = p1.getPosition().distance(origin.getPosition());
                    double dist2 = p2.getPosition().distance(origin.getPosition());
                    return Double.compare(dist1, dist2);
                })
                .orElse(null);
    }

    public static TowerEntity closestEnemyDefenseWithinDistance(EntityCreature origin, double maxDistance) {
        return null;
    }

    public static TroopCreature closestEnemyTroopWithinDistance(EntityCreature origin, double maxDistance) {
        return (TroopCreature) origin.getInstance().getEntities().stream()
                .filter(p -> p.getPosition().distance(origin.getPosition()) <= maxDistance)
                .filter(p -> p != origin)
                .filter(p -> p instanceof TroopCreature)
                .filter(p -> ((EntityCreature) p).getTeam() != origin.getTeam())
                .min((p1, p2) -> {
                    double dist1 = p1.getPosition().distance(origin.getPosition());
                    double dist2 = p2.getPosition().distance(origin.getPosition());
                    return Double.compare(dist1, dist2);
                })
                .orElse(null);
    }

    public static TroopCreature closestFriendlyTroopWithinDistance(EntityCreature origin, double maxDistance) {
        return (TroopCreature) origin.getInstance().getEntities().stream()
                .filter(p -> p.getPosition().distance(origin.getPosition()) <= maxDistance)
                .filter(p -> p != origin)
                .filter(p -> p instanceof TroopCreature)
                .filter(p -> ((EntityCreature) p).getTeam() == origin.getTeam())
                .min((p1, p2) -> {
                    double dist1 = p1.getPosition().distance(origin.getPosition());
                    double dist2 = p2.getPosition().distance(origin.getPosition());
                    return Double.compare(dist1, dist2);
                })
                .orElse(null);
    }

}
