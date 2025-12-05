package de.reptudn.Commands.Game;

import de.reptudn.Game.Exceptions.PlayerAlreadyInQueueException;
import de.reptudn.Game.Exceptions.PlayerNotInQueueException;
import de.reptudn.Game.GameManager;
import de.reptudn.Utils.MessageFormat;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;

public class SoloQueueCommand extends Command {
    public SoloQueueCommand() {
        super("soloqueue", "squeue", "sq");

        setDefaultExecutor((commandSender, commandContext) -> {
            if (!(commandSender instanceof Player)) {
                if (commandSender != null)
                    commandSender.sendMessage("This command can only be executed by a player.");
                return;
            }

            Player player = (Player) commandSender;

            if (GameManager.isPlayerInSoloQueue(player)) {
                commandSender.sendMessage(MessageFormat.getFormattedString("You are currently in the solo queue."));
            } else {
                commandSender.sendMessage(MessageFormat.getFormattedString("You are not in the solo queue."));
            }

        });

        var queueAction = ArgumentType.String("queueAction");
        addSyntax((sender, commandContext) -> {

            Player player = (Player) sender;

            switch (commandContext.get("queueAction").toString()) {
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
                    // Logic to remove player from solo queue
                    player.sendMessage(MessageFormat.getFormattedString("You have left the solo queue."));
                }
                default ->
                    player.sendMessage(MessageFormat.getFormattedString("Invalid action. Use 'join' or 'leave'."));
            }
        }, queueAction);
    }
}
