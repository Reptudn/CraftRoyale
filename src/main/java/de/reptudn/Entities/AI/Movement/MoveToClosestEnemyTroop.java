package de.reptudn.Entities.AI.Movement;

import de.reptudn.Entities.AI.IBehavior;
import de.reptudn.Entities.AI.Utility.FindTarget;
import de.reptudn.Entities.AI.Utility.Pathfind;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.pathfinding.Navigator;

public class MoveToClosestEnemyTroop implements IBehavior {

    private final double maxDetectionRange;
    private final double attackRange;

    public MoveToClosestEnemyTroop(double movementSpeed, double maxDetectionRange, double attackRange) {
        this.maxDetectionRange = maxDetectionRange;
        this.attackRange = attackRange;
    }

    @Override
    public void tick(EntityCreature entity, long time) {
        EntityCreature target = (EntityCreature) entity.getTarget();
        if (target == null || target.isRemoved() || target.getHealth() <= 0) {
            entity.setTarget(FindTarget.closestEnemyTroopWithinDistance(entity, maxDetectionRange));
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