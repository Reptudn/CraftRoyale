package de.reptudn.Entities.AI;

import net.minestom.server.entity.EntityCreature;

public interface IBehavior {
	void tick(EntityCreature entity, long time);
}
