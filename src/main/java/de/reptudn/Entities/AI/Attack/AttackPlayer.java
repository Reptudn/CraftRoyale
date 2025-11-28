package de.reptudn.Entities.AI.Attack;

import de.reptudn.Entities.AI.IBehavior;
import de.reptudn.Entities.AI.Utility.FindTarget;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.Player;

public class AttackPlayer implements IBehavior {

    private final double attackRange;
    private final float damage;
    public AttackPlayer(double attackRange, float damage) {
        this.damage = damage;
        this.attackRange = attackRange;
    }

    @Override
    public void tick(EntityCreature entity, long time) {
        Player p = FindTarget.closestPlayer(entity);
        if (p == null) return;
        if (p.getDistance(entity) > attackRange) return;

        p.setHealth(p.getHealth() - damage);

    }
}
