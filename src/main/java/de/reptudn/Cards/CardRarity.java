package de.reptudn.Cards;

import net.kyori.adventure.text.format.TextColor;

public enum CardRarity {
    COMMON,
    RARE,
    EPIC,
    LEGENDARY,
    CHAMPION;

    public static TextColor getColorByRarity(CardRarity rarity) {
        return switch (rarity) {
            case COMMON -> TextColor.fromCSSHexString("#484242ff"); // White
            case RARE -> TextColor.fromCSSHexString("#0000FF"); // Blue
            case EPIC -> TextColor.fromCSSHexString("#800080"); // Purple
            case LEGENDARY -> TextColor.fromCSSHexString("#FFA500"); // Orange
            case CHAMPION -> TextColor.fromCSSHexString("#FFD700"); // Gold
        };
    }
}
