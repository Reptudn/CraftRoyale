package de.reptudn.Entities;

import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;

public abstract class ATower extends EntityCreature {
	protected float health;
	protected float damage;

	public ATower(float health, float damage, EntityType type) {
		super(type);
		this.health = health;
		this.damage = damage;
	}

	public float getHealth() {
		return health;
	}

	public float getDamage() {
		return damage;
	}

	public void takeDamage(float damage) {
		this.health -= damage;
		if (this.health < 0) {
			this.health = 0;
		}
	}

	public boolean isDestroyed() {
		return this.health <= 0;
	}
}
