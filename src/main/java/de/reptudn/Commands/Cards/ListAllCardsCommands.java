package de.reptudn.Commands.Cards;

import de.reptudn.Cards.CardManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.minestom.server.command.builder.Command;

public class ListAllCardsCommands extends Command {
	public ListAllCardsCommands() {
		super("listallcards", "listcards", "lacards");

		setDefaultExecutor((sender, commandContext) -> {
			sender.sendMessage("Available Cards:");
			CardManager.getCards().values().forEach(card -> {
				sender.sendMessage(Component.text("- " + card.getName() + " (Rarity: " + card.getCardRarity() + ", Cost: "
						+ card.getElixirCost() + ")")
                        .clickEvent(ClickEvent.runCommand("/givecard " + card.getName()))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to get card."))));
			});
		});
	}
}
