package de.reptudn.Game;

import de.reptudn.Cards.ACard;
import de.reptudn.Cards.BuildingCard;
import de.reptudn.Cards.CardManager;
import de.reptudn.Cards.SpellCard;
import de.reptudn.Cards.TroopCard;
import de.reptudn.Entities.TroopCreature;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.item.ItemStack;

public class CardPlacementHandler {
	public static void handleCardPlacement(PlayerUseItemEvent event, Game game) {
		Player p = event.getPlayer();
		ItemStack item = event.getItemStack();

		ACard card = CardManager.getCardByItem(item);
		if (card == null
				|| !(card instanceof TroopCard || !(card instanceof SpellCard) || !(card instanceof BuildingCard))) {
			return; // Not a valid troop card
		}

		Pos placementPos = getPlacementPosition(p);

		switch (card) {
			case TroopCard troopCard -> {
				TroopCreature tc = new TroopCreature(troopCard, p.getInstance(), Team.BLUE, placementPos);
				System.out.println("Placed troop: " + card.getName() + " at " + placementPos + " for player "
						+ p.getUsername() + " as " + tc.toString());
			}
			case SpellCard spellCard -> System.out.println("SpellCards not implemented yet.");
			case BuildingCard buildingCard -> System.out.println("BuildingCards not implemented yet.");
			default -> System.out.println("Unknown card type: " + card.getName());
		}

	}

	private static Pos getPlacementPosition(Player p) {
		return p.getPosition().add(0, 0, 5); // Example: 5 blocks in front of the player
	}
}
