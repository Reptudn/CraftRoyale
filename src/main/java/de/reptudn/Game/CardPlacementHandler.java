package de.reptudn.Game;

import de.reptudn.Cards.ACard;
import de.reptudn.Cards.CardManager;
import de.reptudn.Cards.TroopCard;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.item.ItemStack;

public class CardPlacementHandler {
	public static void handleCardPlacement(PlayerUseItemEvent event, Game game) {
		Player p = event.getPlayer();
		ItemStack item = event.getItemStack();

		ACard card = CardManager.getCardFromItem(item);
	}
}
