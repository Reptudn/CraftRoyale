package de.reptudn.Entities.AI;

import net.minestom.server.entity.EntityCreature;

public interface Behavior {
	void tick(EntityCreature entity, long time);
}
