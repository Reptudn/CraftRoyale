package de.reptudn.Cards;

import java.util.List;

import de.reptudn.Entities.AI.IBehavior;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.component.DataComponents;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

public class BuildingCard extends ACard {

    private final int hitpoints;
    private final double lifetime;

    public BuildingCard(String name, CardRarity rarity, int elixirCost, int hitpoints, double lifetime) {
        super(name, rarity, CardType.BUILDING, elixirCost);
        this.hitpoints = hitpoints;
        this.lifetime = lifetime;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public double getLifetime() {
        return lifetime;
    }

    @Override
    public ItemStack createItemStack() {
        return createBaseItemStack(Material.BRICKS) // Different material for buildings
                .set(DataComponents.LORE, List.of(
                        Component.text("Type: Building").color(NamedTextColor.GOLD),
                        Component.text("HP: " + hitpoints).color(NamedTextColor.GREEN),
                        Component.text("Lifetime: " + lifetime + "s").color(NamedTextColor.YELLOW),
                        Component.text("Elixir Cost: " + getElixirCost()).color(NamedTextColor.LIGHT_PURPLE),
                        Component.empty(),
                        Component.text("Right-click to place!").color(NamedTextColor.GRAY)))
                .build();
    }

    @Override
    public List<IBehavior> getDefaultTroopBehaviors() {
        throw new UnsupportedOperationException("BuildingCard does not have troop behaviors.");
    }
}
