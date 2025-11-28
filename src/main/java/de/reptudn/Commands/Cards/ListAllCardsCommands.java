package de.reptudn.Commands.Cards;

import de.reptudn.Cards.CardManager;
import net.minestom.server.command.builder.Command;

public class ListAllCardsCommands extends Command {
	public ListAllCardsCommands() {
		super("listallcards", "listcards", "lacards");

		setDefaultExecutor((sender, commandContext) -> {
			sender.sendMessage("Available Cards:");
			CardManager.getCards().values().forEach(card -> {
				sender.sendMessage("- " + card.getName() + " (Rarity: " + card.getCardRarity() + ", Cost: "
						+ card.getElixirCost() + ")");
			});
		});
	}
}
