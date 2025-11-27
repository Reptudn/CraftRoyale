package de.reptudn.Entities;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.parser.Entity;

import de.reptudn.Cards.TroopCard;
import de.reptudn.Entities.AI.Behavior;
import de.reptudn.Game.Team;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.component.DataComponents;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.ai.EntityAIGroup;
import net.minestom.server.entity.metadata.monster.zombie.ZombieMeta;
import net.minestom.server.instance.Instance;

public class TroopCreature extends EntityCreature{

	private final TroopCard card;
	private final List<Behavior> behaviors = new ArrayList<>();

	private final int attackDamage;
	private final Team team;

	public TroopCreature(TroopCard card, Instance instance, Team team, Pos spawnPos) {
		super(EntityType.ZOMBIE);

		this.card = card;
		this.team = team;

		this.attackDamage = card.getDamange();

		addAIGroup(new EntityAIGroup() {
			@Override
			public void tick(long time) {
				runBehaviors(time);
			}
		});

		editEntityMeta(ZombieMeta.class, meta -> {
			meta.setCustomNameVisible(true);
			meta.setHasGlowingEffect(true);
			meta.setHealth(card.getHitpoints());
		});

		updateHealthDisplay();

		this.setInstance(instance);
		this.teleport(spawnPos);
	}

	private void updateHealthDisplay() {
		Component nameComponent = Component.text(card.getName())
			.color(Team.getTeamColor(team))
			.append(Component.newline())
			.append(createHealthBar());
		
		this.set(DataComponents.CUSTOM_NAME, nameComponent);
	}

	private Component createHealthBar() {
		int maxHealth = card.getHitpoints();
		int barLength = 20; // Length of the health bar
		int filledBars = (int) ((double) this.getHealth() / maxHealth * barLength);
		
		StringBuilder healthBar = new StringBuilder();
		
		// Add filled portion (green/yellow/red based on health percentage)
		TextColor healthColor = getHealthColor();
		for (int i = 0; i < filledBars; i++) {
			healthBar.append("█");
		}
		
		Component filledPortion = Component.text(healthBar.toString()).color(healthColor);
		
		// Add empty portion (gray)
		StringBuilder emptyBar = new StringBuilder();
		for (int i = filledBars; i < barLength; i++) {
			emptyBar.append("█");
		}
		Component emptyPortion = Component.text(emptyBar.toString()).color(NamedTextColor.DARK_GRAY);
		
		// Add health numbers
		Component healthText = Component.text(" " + this.getHealth() + "/" + maxHealth)
			.color(NamedTextColor.WHITE);
		
		return filledPortion.append(emptyPortion).append(healthText);
	}
 
	private void runBehaviors(long time) {
		for(Behavior behavior : behaviors) {
			behavior.tick(this, time);
		}
	}

	public void damage(float damage) {
		float currentHealth = this.getHealth();
		currentHealth -= damage;
		this.setHealth(currentHealth);

		if(currentHealth <= 0) {
			// handle more death stuff later here like death particles/sounds etc
			this.remove();
		}
	}

	private TextColor getHealthColor() {
		double healthPercent = (double) this.getHealth() / card.getHitpoints();
		if (healthPercent > 0.6) return NamedTextColor.GREEN;
		if (healthPercent > 0.3) return NamedTextColor.YELLOW;
		return NamedTextColor.RED;
	}

	public void heal(float amount) {
		this.setHealth(Math.min(card.getHitpoints(), this.getHealth() + amount));
		updateHealthDisplay();
	}

	public void attack(EntityCreature target) {

		if (!(target instanceof TroopCreature || target == null || target.isRemoved()))
			return;
		if (target instanceof TroopCreature troop) { // currently we only attack other troops
			if (troop.team == this.team) {
				troop.heal(this.attackDamage);
			} else {
				troop.damage(this.attackDamage);
			}
			troop.updateHealthDisplay();
		}
	}

	public float getCurrentHealth() {
		return this.getHealth();
	}
}
