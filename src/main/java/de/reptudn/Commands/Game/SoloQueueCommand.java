package de.reptudn.Commands.Game;

import de.reptudn.Game.Exceptions.PlayerAlreadyInQueueException;
import de.reptudn.Game.Exceptions.PlayerNotInQueueException;
import de.reptudn.Game.GameManager;
import de.reptudn.Utils.MessageFormat;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.ArgumentString;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.entity.Player;

public class SoloQueueCommand extends Command {
    public SoloQueueCommand() {
        super("soloqueue", "squeue", "sq");

        setDefaultExecutor((commandSender, commandContext) -> {
            Player player = (Player) commandSender;

            if (GameManager.isPlayerInSoloQueue(player)) {
                System.out.println("Player is in solo queue");
                player.sendMessage(MessageFormat.getFormattedString("You are currently in the solo queue."));
            } else {
                System.out.println("Player is not in solo queue");
                player.sendMessage(MessageFormat.getFormattedString("You are not in the solo queue."));
            }
        });

        ArgumentString queueAction = ArgumentType.String("queueAction");

        queueAction.setSuggestionCallback((sender, context, suggestion) -> {
            suggestion.addEntry(new SuggestionEntry("join"));
            suggestion.addEntry(new SuggestionEntry("leave"));
        });

        addSyntax((sender, commandContext) -> {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(
                        MessageFormat.getFormattedString("This command can only be executed by a player."));
                return;
            }

            switch (commandContext.get(queueAction)) {
                case "join" -> {
                    try {
                        GameManager.addPlayerToSoloQueue(player);
                        player.sendMessage(MessageFormat.getFormattedString("You have joined the solo queue."));
                    } catch (PlayerAlreadyInQueueException e) {
                        player.sendMessage(MessageFormat.getFormattedString(e.getMessage()));
                    }
                }
                case "leave" -> {
                    try {
                        GameManager.removePlayerFromSoloQueue(player);
                        player.sendMessage(MessageFormat.getFormattedString("You have left the solo queue."));
                    } catch (PlayerNotInQueueException e) {
                        player.sendMessage(MessageFormat.getFormattedString(e.getMessage()));
                    }
                }
                default -> {
                    player.sendMessage(MessageFormat.getFormattedString("Invalid action. Use 'join' or 'leave'."));
                }
            }
        }, queueAction);
    }
}