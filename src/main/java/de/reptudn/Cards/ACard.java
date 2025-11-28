package de.reptudn.Cards;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.component.DataComponents;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.tag.Tag;

public abstract class ACard {

    protected String name;
    protected CardRarity rarity;
    protected CardType type;

    public static final Tag<String> CARD_ID_TAG = Tag.String("card_id");

    protected int elixirCost;

    public ACard(String name, CardRarity rarity, CardType type, int elixirCost) {
        this.name = name;
        this.rarity = rarity;
        this.type = type;
        this.elixirCost = elixirCost;
    }

    public String getName() {
        return name;
    }

    public CardRarity getCardRarity() {
        return rarity;
    }

    public int getElixirCost() {
        return elixirCost;
    }

    public CardType getCardType() {
        return type;
    }

    public abstract ItemStack createItemStack();

    protected ItemStack.Builder createBaseItemStack(Material material) {
        return ItemStack.builder(material).set(CARD_ID_TAG, this.name).set(DataComponents.CUSTOM_NAME, Component
                .text(this.name).color(CardRarity.getColorByRarity(rarity)).decoration(TextDecoration.ITALIC, false));
    }
}
