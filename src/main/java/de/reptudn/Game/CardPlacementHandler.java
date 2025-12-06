package de.reptudn.Game;

import de.reptudn.Cards.ACard;
import de.reptudn.Cards.BuildingCard;
import de.reptudn.Cards.CardManager;
import de.reptudn.Cards.SpellCard;
import de.reptudn.Cards.TroopCard;
import de.reptudn.Entities.TroopCreature;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.item.ItemStack;

public class CardPlacementHandler {
	public static void handleCardPlacement(PlayerUseItemEvent event) {
		Player p = event.getPlayer();
		p.sendMessage("UseItemEvent");
		ItemStack item = event.getItemStack();

		ACard card = CardManager.getCardByItem(item);
		if (card == null
				|| !(card instanceof TroopCard || !(card instanceof SpellCard) || !(card instanceof BuildingCard))) {
			System.out.println("Invalid card placement attempt by player " + p.getUsername());
			return; // Not a valid troop card
		}

		Pos placementPos = getPlacementPosition(p);
		if (placementPos == null) {
			p.sendMessage("Cannot place card here.");
			System.out.println("Invalid placement position for player " + p.getUsername());
			return;
		}
		CardPreviewHandler.startPreview(p);

		switch (card) {
			case TroopCard troopCard -> {
				if (!canAffordCard(card, p)) {
					p.sendMessage("Can't afford that card!");
					return;
				}
				TroopCreature tc = new TroopCreature(troopCard, p.getInstance(), placementPos);
				tc.setTeam(p.getTeam());
				p.setFood(Math.max(p.getFood() - card.getElixirCost() * 2, 0));
				// System.out.println("Placed troop: " + card.getName() + " at " + placementPos
				// + " for player "
				// + p.getUsername() + " as " + tc);
			}
			case SpellCard spellCard -> System.out.println("SpellCards not implemented yet.");
			case BuildingCard buildingCard -> System.out.println("BuildingCards not implemented yet.");
			default -> System.out.println("Unknown card type: " + card.getName());
		}

	}

	public static Pos getPlacementPosition(Player p) {

		Point lookPos = p.getTargetBlockPosition(30);

		if (lookPos == null) {
			return null;
		}

		return lookPos.asPos().add(0.5, 1, 0.5);
	}

	public static boolean canAffordCard(ACard card, Player player) {
		return card.getElixirCost() <= (player.getFood() / 2);
	}
}
