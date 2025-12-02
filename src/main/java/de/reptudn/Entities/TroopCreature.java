package de.reptudn.Entities;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import de.reptudn.Cards.TroopCard;
import de.reptudn.Entities.AI.IBehavior;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.component.DataComponents;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.ai.EntityAIGroup;
import net.minestom.server.entity.attribute.Attribute;
import net.minestom.server.entity.metadata.monster.zombie.ZombieMeta;
import net.minestom.server.instance.Instance;
import net.minestom.server.network.packet.server.play.EntityAnimationPacket;

public class TroopCreature extends EntityCreature {

	private final TroopCard card;
	private final List<IBehavior> behaviors = new ArrayList<>();

	private final int attackDamage;

	private float customCurrentHealth;
	private final float customMaxHealth;

	private EntityCreature targetEntity;

	public TroopCreature(TroopCard card, Instance instance, Pos spawnPos) {
		super(EntityType.ZOMBIE);

		this.targetEntity = null;

		this.card = card;

		this.attackDamage = card.getDamage();

		behaviors.addAll(card.getDefaultTroopBehaviors());

		addAIGroup(new EntityAIGroup() {
			@Override
			public void tick(long time) {
				if (getHealth() <= 0) {
					// Death callback here later
					despawn();
					getViewersAsAudience().sendMessage(Component.text("Troop " + card.getName() + " has been defeated!")
							.color(NamedTextColor.RED));
					return;
				}
				runBehaviors(time);
			}
		});

		editEntityMeta(ZombieMeta.class, meta -> {
			meta.setCustomNameVisible(true);
			meta.setHasGlowingEffect(true);
		});

		this.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(card.getMovementSpeed() / 20.0);

		this.customMaxHealth = card.getHitpoints();
		this.customCurrentHealth = (int) customMaxHealth;

		updateHealthDisplay();

		this.setInstance(instance, spawnPos);
	}

	private void runBehaviors(long time) {
		for (IBehavior behavior : behaviors) {
			behavior.tick(this, time);
		}
	}

	@Override
	public void setHealth(float health) {

		if (card == null) {
			super.setHealth(health);
			return;
		}

		this.customCurrentHealth = Math.max(0, Math.min(customMaxHealth, health));

		// Update visual health bar (scaled to Minecraft's range)
		float visualHealth = (customCurrentHealth / customMaxHealth) * 40.0f;
		super.setHealth(visualHealth);

		editEntityMeta(ZombieMeta.class, meta -> {
			meta.setHealth(visualHealth);
		});

		updateHealthDisplay();
	}

	@Override
	public float getHealth() {
		return customCurrentHealth;
	}

	@Override
	public EntityCreature getTarget() {
		return targetEntity;
	}

	public void setTargetEntityCreature(@Nullable EntityCreature target) {
		this.targetEntity = target;
	}

	private void updateHealthDisplay() {
		Component nameComponent;
		if (this.getTeam() != null)
			nameComponent = Component.text(card.getName())
					.color(this.getTeam().getTeamColor())
					.append(Component.newline())
					.append(createHealthBar());
		else
			nameComponent = Component.text(card.getName())
					.color(NamedTextColor.WHITE)
					.append(Component.newline())
					.append(createHealthBar());

		this.set(DataComponents.CUSTOM_NAME, nameComponent);
	}

	private Component createHealthBar() {
		int maxHealth = (int) this.customMaxHealth;
		int barLength = 20; // Length of the health bar
		int filledBars = (int) ((double) this.getHealth() / maxHealth * barLength);

		StringBuilder healthBar = new StringBuilder();

		// Add filled portion (green/yellow/red based on health percentage)
		TextColor healthColor = getHealthColor();
		healthBar.append("|".repeat(Math.max(0, filledBars)));

		Component filledPortion = Component.text(healthBar.toString()).color(healthColor);

		// Add empty portion (gray)
		StringBuilder emptyBar = new StringBuilder();
		emptyBar.append(" ".repeat(Math.max(0, barLength - filledBars)));
		Component emptyPortion = Component.text(emptyBar.toString()).color(NamedTextColor.DARK_GRAY);

		// Add health numbers
		Component healthText = Component.text(" " + (int) this.getHealth() + "/" + maxHealth)
				.color(NamedTextColor.WHITE);

		return filledPortion.append(emptyPortion).append(healthText);
	}

	public void damage(float damage) {
		float currentHealth = this.getHealth();
		currentHealth -= damage;
		this.setHealth(currentHealth);

		this.getViewers().forEach(player -> {
			player.sendPacket(
					new EntityAnimationPacket(this.getEntityId(), EntityAnimationPacket.Animation.TAKE_DAMAGE));
		});

		this.updateHealthDisplay();

		// TODO: display damage effect here

		if (currentHealth <= 0) {
			// handle more death stuff later here like death particles/sounds etc
			this.remove();
		}
	}

	private TextColor getHealthColor() {
		double healthPercent = (double) this.getHealth() / card.getHitpoints();
		if (healthPercent > 0.6)
			return NamedTextColor.GREEN;
		if (healthPercent > 0.3)
			return NamedTextColor.YELLOW;
		return NamedTextColor.RED;
	}

	public void heal(float amount) {
		this.setHealth(Math.min(card.getHitpoints(), this.getHealth() + amount));
		updateHealthDisplay();
	}

	public float getCurrentHealth() {
		return this.getHealth();
	}
}
