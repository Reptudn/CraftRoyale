package de.reptudn.Cards;

public class SpellCard extends ACard{

    private int normalDamage;
    private int towerDamage;
    private double radius;

    public SpellCard(String name, CardRarity rarity, int exlicirCost, int normalDamage, int towerDamage, double radius) {
        super(name, rarity, CardType.SPELL, exlicirCost);
        this.normalDamage = normalDamage;
        this.towerDamage = towerDamage;
        this.radius = radius;
    }
}
