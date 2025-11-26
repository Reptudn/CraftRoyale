package de.reptudn.Cards;

import java.util.function.Function;

public class TroopCard extends ACard {

    private Function<Void, Void> onDeploy;
    private Function<Void, Void> onDeath;

    private int hitpoints;
    private int damange;
    private double attackPeriod;

    public TroopCard(String name, CardRarity rarity, int exlicirCost, int hitpoints, int damage, double attackPeriod) {
        super(name, rarity, CardType.TROOP, exlicirCost);
        this.hitpoints = hitpoints;
        this.damange = damage;
        this.attackPeriod = attackPeriod;
    }

}
