package de.reptudn.Entities.AI.Movement;

import de.reptudn.Entities.AI.IBehavior;
import de.reptudn.Entities.AI.Utility.FindTarget;
import de.reptudn.Entities.AI.Utility.Pathfind;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.pathfinding.Navigator;

public class MoveToClosestTroop implements IBehavior {

    private final double maxDetectionRange;
    private final double attackRange;

    public MoveToClosestTroop(double maxDetectionRange, double attackRange) {
        this.maxDetectionRange = maxDetectionRange;
        this.attackRange = attackRange;
    }

    @Override
    public void tick(EntityCreature entity, long time) {
        Entity target = entity.getTarget();
        if (target == null || target.isRemoved()) {
            entity.setTarget(FindTarget.closestEntityWithinDistance(entity, maxDetectionRange));
        }

        if (!Pathfind.isValidTarget(entity, target)) {
            return;
        }

        Navigator navigator = entity.getNavigator();
        Pos targetPos = target.getPosition();
        navigator.setPathTo(targetPos);


        if (entity.getPosition().distance(targetPos) <= attackRange) {
            navigator.setPathTo(null);
        }
    }
}
