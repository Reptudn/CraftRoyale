package de.reptudn.Cards;

import java.util.List;

import de.reptudn.Entities.AI.IBehavior;
import de.reptudn.Entities.AI.CollisionBehavior;
import de.reptudn.Entities.AI.Movement.MoveToClosestPlayerBehavior;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.component.DataComponents;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

public class TroopCard extends ACard {

    private final int hitpoints;
    private final int damange;
    private final double attackPeriod;
    private final double movementSpeed;

    private final double maxDetectionRange;
    private final double attackRange;

    // TODO: add troop type and behavior type later
    // private final BehaviorType behaviorType = BehaviorType.ALL;
    // private final TroupType troopType = TroupType.GROUND;

    public TroopCard(String name, CardRarity rarity, int exlicirCost, int hitpoints, int damage, double attackPeriod,
            double movementSpeed, double maxDetectionRange, double attackRange) {
        super(name, rarity, CardType.TROOP, exlicirCost);
        this.hitpoints = hitpoints;
        this.damange = damage;
        this.attackPeriod = attackPeriod;
        this.movementSpeed = movementSpeed;
        this.maxDetectionRange = maxDetectionRange;
        this.attackRange = attackRange;
    }

    @Override
    public List<IBehavior> getDefaultTroopBehaviors() {
        return List.of(new MoveToClosestPlayerBehavior(movementSpeed, maxDetectionRange, attackRange),
                new CollisionBehavior());
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public int getDamange() {
        return damange;
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
