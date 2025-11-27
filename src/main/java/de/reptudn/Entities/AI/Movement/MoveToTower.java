package de.reptudn.Entities.AI.Movement;

import javax.swing.text.html.parser.Entity;

import de.reptudn.Entities.AI.Behavior;
import de.reptudn.Entities.AI.Utility.FindTarget;
import net.minestom.server.entity.EntityCreature;

public class MoveToTower implements Behavior {
	
	@Override
	public void tick(EntityCreature entity, long time) {
		EntityCreature targetEntity = FindTarget.closestEnemyTroop(entity);
	}
	
}
