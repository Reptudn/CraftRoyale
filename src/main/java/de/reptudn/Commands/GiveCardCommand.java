package de.reptudn.Commands;

import de.reptudn.Main;
import de.reptudn.Utils.MessageFormat;
import net.kyori.adventure.text.Component;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

public class GiveCardCommand extends Command {
    public GiveCardCommand() {
        super("givecard", "give", "card");

        setDefaultExecutor((sender, commandContext) -> {

            if (!(sender instanceof Player player)){
                sender.sendMessage(MessageFormat.getFormattedString("This command can only be executed by a player."));
                return;
            }

            sender.sendMessage(MessageFormat.getFormattedString("Usage: /givecard <cardName>"));
        });

        var cardNameArg = ArgumentType.String("cardName");
        addSyntax((sender, commandContext) -> {

            if (!(sender instanceof Player player)){
                sender.sendMessage(MessageFormat.getFormattedString("This command can only be executed by a player."));
                return;
            }

            final String cardName = commandContext.get(cardNameArg);

            var card = Main.CARDS.get(cardName.replace(" ", "-"));

            if (card == null) {
                player.sendMessage(MessageFormat.getFormattedString("Card not found: " + cardName));
                return;
            }

            player.getInventory().addItemStack(ItemStack.of(Material.PAPER).withCustomName(Component.text("§r§b" + card.getName())));

            player.sendMessage(MessageFormat.getFormattedString("Added Card Name: " + cardName));
        }, cardNameArg);

    }
}
