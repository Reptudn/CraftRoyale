package de.reptudn.Entities.AI.Movement;

import de.reptudn.Entities.AI.IBehavior;
import de.reptudn.Entities.AI.Utility.FindTarget;
import net.minestom.server.entity.EntityCreature;

public class MoveToTower implements IBehavior {

	@Override
	public void tick(EntityCreature entity, long time) {
		EntityCreature targetEntity = FindTarget.closestEnemyTroop(entity);
	}

}
