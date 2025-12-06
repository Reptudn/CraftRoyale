package de.reptudn.Entities.AI.Utility;

import de.reptudn.Entities.KingTowerEntity;
import de.reptudn.Entities.TroopCreature;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.Player;

public class FindTarget {
    public static TroopCreature closestEnemyTroop(EntityCreature origin) {
        return origin.getInstance().getEntities().stream()
                .filter(e -> e instanceof TroopCreature && e != origin && ((EntityCreature) e).getTeam() != origin.getTeam())
                .map(e -> (TroopCreature) e)
                .min((e1, e2) -> {
                    double dist1 = e1.getPosition().distance(origin.getPosition());
                    double dist2 = e2.getPosition().distance(origin.getPosition());
                    return Double.compare(dist1, dist2);
                })
                .orElse(null);
    }

    public static EntityCreature closestEnemyEntity(EntityCreature origin) {
        return (EntityCreature) origin.getInstance().getEntities().stream()
                .filter(e -> e instanceof EntityCreature && e != origin && ((EntityCreature) e).getTeam() != origin.getTeam())
                .min((e1, e2) -> {
                    double dist1 = e1.getPosition().distance(origin.getPosition());
                    double dist2 = e2.getPosition().distance(origin.getPosition());
                    return Double.compare(dist1, dist2);
                })
                .orElse(null);
    }

    public static KingTowerEntity closestEnemyTower(EntityCreature origin) {
        return origin.getInstance().getEntities().stream()
                .filter(e -> e instanceof KingTowerEntity && e != origin
                        && ((EntityCreature) e).getTeam() != origin.getTeam())
                .map(e -> (KingTowerEntity) e)
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
                .map(p -> (TroopCreature) p)
                .min((p1, p2) -> {
                    double dist1 = p1.getPosition().distance(origin.getPosition());
                    double dist2 = p2.getPosition().distance(origin.getPosition());
                    return Double.compare(dist1, dist2);
                })
                .orElse(null);
    }

    public static TroopCreature closestTroopWithinDistance(EntityCreature origin, double maxDistance) {
        return origin.getInstance().getEntities().stream()
                .filter(p -> p != origin)
                .filter(p -> p instanceof TroopCreature)
                .map(p -> (TroopCreature) p)
                .filter(p -> p.getPosition().distance(origin.getPosition()) <= maxDistance)
                .min((p1, p2) -> {
                    double dist1 = p1.getPosition().distance(origin.getPosition());
                    double dist2 = p2.getPosition().distance(origin.getPosition());
                    return Double.compare(dist1, dist2);
                })
                .orElse(null);
    }

    public static KingTowerEntity closestEnemyDefense(EntityCreature origin) {
        return (KingTowerEntity) origin.getInstance().getEntities().stream()
                .filter(p -> p != origin)
                .filter(p -> p instanceof KingTowerEntity)
                .filter(p -> ((EntityCreature) p).getTeam() != origin.getTeam())
                .min((p1, p2) -> {
                    double dist1 = p1.getPosition().distance(origin.getPosition());
                    double dist2 = p2.getPosition().distance(origin.getPosition());
                    return Double.compare(dist1, dist2);
                })
                .orElse(null);
    }

    public static KingTowerEntity closestEnemyDefenseWithinDistance(EntityCreature origin, double maxDistance) {
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
