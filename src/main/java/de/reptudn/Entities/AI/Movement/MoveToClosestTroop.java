package de.reptudn.Entities.AI.Movement;

import de.reptudn.Entities.AI.IBehavior;
import de.reptudn.Entities.AI.Utility.FindTarget;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.Player;

public class MoveToClosestTroop implements IBehavior {
    private final double movementSpeed;

    private EntityCreature target = null;
    private final double maxDetectionRange;
    private final double attackRange;

    public MoveToClosestTroop(double movementSpeed, double maxDetectionRange, double attackRange) {
        this.movementSpeed = movementSpeed;
        this.maxDetectionRange = maxDetectionRange;
        this.attackRange = attackRange;
    }

    @Override
    public void tick(EntityCreature entity, long time) {
        if (target == null || target.isRemoved() || target.getHealth() <= 0) {
            target = FindTarget.closestEntityWithinDistance(entity, maxDetectionRange);
        }

        if (target == null) {
            return; // No target found
        }

        Pos currentPos = entity.getPosition();
        Pos targetPos = target.getPosition();

        // Calculate direction vector
        double dx = targetPos.x() - currentPos.x();
        double dz = targetPos.z() - currentPos.z();
        double distance = Math.sqrt(dx * dx + dz * dz);

        if (distance > this.maxDetectionRange) {
            target = null; // Target is out of range
            return;
        }

        if (distance > this.attackRange) { // Move to target until in attack range
            // Normalize and apply speed
            dx = (dx / distance) * movementSpeed;
            dz = (dz / distance) * movementSpeed;

            Pos newPos = currentPos.add(dx, 0, dz);
            entity.teleport(newPos);
            entity.lookAt(targetPos.withY(targetPos.y() + target.getEyeHeight()));
        }
    }
}
