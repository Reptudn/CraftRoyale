package de.reptudn.Entities;

public abstract class ATower {
	protected int health;
	protected int damage;
	protected TowerType towerType;

	public ATower(TowerType towerType, int health, int damage) {
		this.towerType = towerType;
		this.health = health;
		this.damage = damage;
	}

	public int getHealth() {
		return health;
	}

	public int getDamage() {
		return damage;
	}

	public void takeDamage(int damage) {
		this.health -= damage;
		if (this.health < 0) {
			this.health = 0;
		}
	}

	public boolean isDestroyed() {
		return this.health <= 0;
	}
}
