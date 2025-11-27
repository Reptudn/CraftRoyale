package de.reptudn.Cards;

public class TroopCard extends ACard {

    private final int hitpoints;
    private final int damange;
    private final double attackPeriod;

    public TroopCard(String name, CardRarity rarity, int exlicirCost, int hitpoints, int damage, double attackPeriod) {
        super(name, rarity, CardType.TROOP, exlicirCost);
        this.hitpoints = hitpoints;
        this.damange = damage;
        this.attackPeriod = attackPeriod;
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
}
