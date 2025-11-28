package de.reptudn.Cards;

import java.util.HashMap;
import java.util.Map;

import net.minestom.server.item.ItemStack;

public class CardManager {
	private static final Map<String, ACard> CARDS = new HashMap<>();

	static {
		CARDS.put("Archers", new TroopCard("Archers", CardRarity.COMMON, 3, 304, 112, 0.9, 1, 10, 3));
		CARDS.put("Baby Dragon", new TroopCard("Baby Dragon", CardRarity.RARE, 4, 1152, 161, 1.5, 1, 15, 3));
		CARDS.put("Barbarians", new TroopCard("Barbarians", CardRarity.COMMON, 5, 670, 192, 1.3, 1, 20, 1));
		CARDS.put("Giant", new TroopCard("Giant", CardRarity.COMMON, 5, 4090, 253, 1.5, 1, 30, 5));
		CARDS.put("Fireball", new SpellCard("Fireball", CardRarity.RARE, 4, 688, 207, 2.5));
		CARDS.put("Arrows", new SpellCard("Arrows", CardRarity.COMMON, 3, 366, 93, 3.5));
		CARDS.put("Mini P.E.K.K.A", new TroopCard("Mini P.E.K.K.A", CardRarity.RARE, 4, 1433, 755, 1.6, 0.1, 25, 1));
		CARDS.put("Elixir Collector", new BuildingCard("Elixir Collector", CardRarity.RARE, 6, 1164, 30.0));
		CARDS.put("Golem", new TroopCard("Golem", CardRarity.EPIC, 8, 5120, 312, 2.5, 0.2, 40, 1));
	}

	public static ACard getCardByItem(ItemStack item) {
		String cardId = item.getTag(ACard.CARD_ID_TAG);
		return cardId != null ? CARDS.get(cardId) : null;
		// Implementation to retrieve a card based on the ItemStack
	}

	public static void registerCard(ACard card) {
		CARDS.put(card.getName(), card);
	}

	public static ACard getCardByName(String name) {
		return CARDS.get(name);
	}

	public static Map<String, ACard> getCards() {
		return CARDS;
	}
}
