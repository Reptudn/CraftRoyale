package de.reptudn.Cards;

public abstract class ACard {

    public String name;
    public CardRarity rarity;
    public CardType type;

    public int elixirCost;

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
}
