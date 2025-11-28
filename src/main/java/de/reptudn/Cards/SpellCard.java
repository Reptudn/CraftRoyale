package de.reptudn.Cards;

import java.util.List;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.component.DataComponents;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

public class SpellCard extends ACard {

    private int normalDamage;
    private int towerDamage;
    private double radius;

    public SpellCard(String name, CardRarity rarity, int exlicirCost, int normalDamage, int towerDamage,
            double radius) {
        super(name, rarity, CardType.SPELL, exlicirCost);
        this.normalDamage = normalDamage;
        this.towerDamage = towerDamage;
        this.radius = radius;
    }

    public int getNormalDamage() {
        return normalDamage;
    }

    public int getTowerDamage() {
        return towerDamage;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public ItemStack createItemStack() {
        return createBaseItemStack(Material.BLAZE_POWDER) // Different material for spells
                .set(DataComponents.LORE, List.of(
                        Component.text("Type: Spell").color(NamedTextColor.GOLD),
                        Component.text("Damage: " + this.normalDamage).color(NamedTextColor.RED),
                        Component.text("Radius: " + radius).color(NamedTextColor.BLUE),
                        Component.text("Elixir Cost: " + getElixirCost()).color(NamedTextColor.LIGHT_PURPLE),
                        Component.empty(),
                        Component.text("Right-click to cast!").color(NamedTextColor.GRAY)))
                .build();
    }
}
