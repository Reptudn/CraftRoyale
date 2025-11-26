package de.reptudn.Cards;

public class BuildingCard extends ACard{

    private int hitpoints;
    private double lifetime;

    public BuildingCard(String name, CardRarity rarity, int elixirCost, int hitpoints, double lifetime) {
        super(name, rarity, CardType.BUILDING, elixirCost);
        this.hitpoints = hitpoints;
        this.lifetime = lifetime;
    }
}
