package de.reptudn.Entities.AI.Utility;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.Player;

public class Pathfind {
	public static void navigateToTargetEntityCreature(EntityCreature entity, double maxDetectionRange, double attackRange, double movementSpeed) {
        if (entity == null || entity.isDead()) return;

        EntityCreature target = (EntityCreature) entity.getTarget();
        if (target == null|| target.isRemoved() || target.getHealth() <= 0) {
            return; // No target found
        }

        Pos currentPos = entity.getPosition();
        Pos targetPos = target.getPosition();

        // Calculate direction vector
        double dx = targetPos.x() - currentPos.x();
        double dz = targetPos.z() - currentPos.z();
        double distance = Math.sqrt(dx * dx + dz * dz);

        if (distance > maxDetectionRange) {
            entity.setTarget(null);
            return;
        }

        if (distance > attackRange) { // Move to target until in attack range
            // Normalize and apply speed
            dx = (dx / distance) * movementSpeed;
            dz = (dz / distance) * movementSpeed;

            Pos newPos = currentPos.add(dx, 0, dz);
            entity.teleport(newPos);
            entity.lookAt(targetPos.withY(targetPos.y() + target.getEyeHeight()));
        }
    }

    public static void navigateToTargetPlayer(EntityCreature entity, double maxDetectionRange, double attackRange, double movementSpeed) {
        if (entity == null || entity.isDead()) return;

        Player target = (Player) entity.getTarget();
        if (target == null|| target.isRemoved() || target.getHealth() <= 0) {
            return; // No target found
        }

        Pos currentPos = entity.getPosition();
        Pos targetPos = target.getPosition();

        // Calculate direction vector
        double dx = targetPos.x() - currentPos.x();
        double dz = targetPos.z() - currentPos.z();
        double distance = Math.sqrt(dx * dx + dz * dz);

        if (distance > maxDetectionRange) {
            entity.setTarget(null);
            return;
        }

        if (distance > attackRange) { // Move to target until in attack range
            // Normalize and apply speed
            dx = (dx / distance) * movementSpeed;
            dz = (dz / distance) * movementSpeed;

            Pos newPos = currentPos.add(dx, 0, dz);
            entity.teleport(newPos);
            entity.lookAt(targetPos.withY(targetPos.y() + target.getEyeHeight()));
        }
    }

    public static boolean isValidTarget(Entity origin, Entity target) {
        if (target == null || target.isRemoved()) {
            return false;
        }
        return target != origin;
    }
}
