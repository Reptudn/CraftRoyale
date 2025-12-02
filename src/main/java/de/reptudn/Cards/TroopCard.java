package de.reptudn.Cards;

import java.util.List;

import de.reptudn.Entities.AI.Attack.AttackClosestEnemyTower;
import de.reptudn.Entities.AI.Attack.AttackEntityCreature;
import de.reptudn.Entities.AI.IBehavior;
import de.reptudn.Entities.AI.CollisionBehavior;
import de.reptudn.Entities.AI.Movement.MoveToClosestDefensive;
import de.reptudn.Entities.AI.Movement.MoveToClosestTroop;
import de.reptudn.Entities.AI.Movement.MoveToTower;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.component.DataComponents;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

public class TroopCard extends ACard {

    private final int hitpoints;
    private final int damage;
    private final long attackPeriod;
    private final double movementSpeed;

    private final double maxDetectionRange;
    private final double attackRange;
    private final CardBehaviorType behaviorType;

    public TroopCard(String name, CardRarity rarity, CardBehaviorType behaviorType, int exlicirCost, int hitpoints,
            int damage, double attackPeriod,
            double movementSpeed, double maxDetectionRange, double attackRange) {
        super(name, rarity, CardType.TROOP, exlicirCost);
        this.hitpoints = hitpoints;
        this.damage = damage;
        this.attackPeriod = (long) (attackPeriod * 1000);
        this.movementSpeed = movementSpeed;
        this.maxDetectionRange = maxDetectionRange;
        this.attackRange = attackRange;
        this.behaviorType = behaviorType;
    }

    // TODO: adjust behaviors based on behaviorType and troopType
    @Override
    public List<IBehavior> getDefaultTroopBehaviors() {
        switch (behaviorType) {
            case DEFENSIVE -> {
                return List.of(new MoveToClosestDefensive(maxDetectionRange, attackRange), new MoveToTower(attackRange),
                        new CollisionBehavior(),
                        new AttackClosestEnemyTower(attackRange, this.damage, this.attackPeriod));
            }
            case AIR_ONLY -> {
                return List.of(new MoveToClosestTroop(maxDetectionRange, attackRange), new MoveToTower(attackRange),
                        new CollisionBehavior(), new AttackEntityCreature(attackRange, this.damage, this.attackPeriod),
                        new AttackClosestEnemyTower(attackRange, this.damage, this.attackPeriod));
            }
            case GROUND_ONLY -> {
                return List.of(new MoveToClosestTroop(maxDetectionRange, attackRange), new MoveToTower(attackRange),
                        new CollisionBehavior(), new AttackEntityCreature(attackRange, this.damage, this.attackPeriod),
                        new AttackClosestEnemyTower(attackRange, this.damage, this.attackPeriod));
            }
            default -> {
                return List.of(new MoveToClosestTroop(maxDetectionRange, attackRange), new MoveToTower(attackRange),
                        new CollisionBehavior(), new AttackEntityCreature(attackRange, this.damage, this.attackPeriod),
                        new AttackClosestEnemyTower(attackRange, this.damage, this.attackPeriod));
            }
        }

    }

    public double getMovementSpeed() {
        return movementSpeed;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public int getDamage() {
        return damage;
    }

    public double getAttackPeriod() {
        return attackPeriod;
    }

    @Override
    public ItemStack createItemStack() {
        return createBaseItemStack(Material.PAPER) // Use paper or a custom material
                .set(DataComponents.LORE, List.of(
                        Component.text("Type: Troop").color(NamedTextColor.GOLD),
                        Component.text("HP: " + hitpoints).color(NamedTextColor.GREEN),
                        Component.text("Attack Speed: " + attackPeriod + "s").color(NamedTextColor.YELLOW),
                        Component.text("Elixir Cost: " + getElixirCost()).color(NamedTextColor.LIGHT_PURPLE),
                        Component.empty(),
                        Component.text("Right-click to place!").color(NamedTextColor.GRAY)))
                .build();
    }
}
