package de.reptudn.Commands.Cards;

import de.reptudn.Cards.CardManager;
import de.reptudn.Utils.MessageFormat;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.entity.Player;

public class GiveCardCommand extends Command {
    public GiveCardCommand() {
        super("givecard", "give", "card");

        setDefaultExecutor((sender, commandContext) -> {

            if (!(sender instanceof Player)) {
                if (sender != null)
                    sender.sendMessage(
                            MessageFormat.getFormattedString("This command can only be executed by a player."));
                return;
            }

            sender.sendMessage(MessageFormat.getFormattedString("Usage: /givecard <cardName>"));
        });

        var cardNameArg = ArgumentType.StringArray("cardName");
        cardNameArg.setSuggestionCallback((sender, context, suggestion) -> {
            String currentInput = context.getInput().toLowerCase();

            CardManager.getCards().keySet().stream()
                    .filter(cardName -> cardName.toLowerCase().startsWith(currentInput))
                    .forEach(cardName -> suggestion.addEntry(new SuggestionEntry(cardName)));
        });

        addSyntax((sender, commandContext) -> {

            if (!(sender instanceof Player player)) {
                if (sender != null) {
                    sender.sendMessage(
                            MessageFormat.getFormattedString("This command can only be executed by a player."));
                }
                return;
            }

            final String[] cardNameArray = commandContext.get(cardNameArg);
            final String cardName = String.join(" ", cardNameArray);

            var card = CardManager.getCardByName(cardName);

            if (card == null) {
                player.sendMessage(MessageFormat.getFormattedString("Card not found: " + cardName));
                return;
            }

            player.getInventory()
                    .addItemStack(card.createItemStack());

            player.sendMessage(MessageFormat.getFormattedString("Added Card Name: " + cardName));
        }, cardNameArg);

    }
}
