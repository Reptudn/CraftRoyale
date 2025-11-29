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
                commandSender.sendMessage("This command can only be executed by a player.");
                return;
            }

            Player player = (Player) commandSender;


        });

        var queueAction = ArgumentType.String("queueAction");
        addSyntax((sender, commandContext) -> {

            Player player = (Player) sender;

            switch (commandContext.get("queueAction").toString()) {
                case "join" -> {
                    try {
                        GameManager.addPlayerToSoloQueue(player);
                        sender.sendMessage(MessageFormat.getFormattedString("You have joined the solo queue."));
                    } catch (PlayerAlreadyInQueueException e) {
                        sender.sendMessage(MessageFormat.getFormattedString(e.getMessage()));
                    }
                }
                case "leave" -> {
                    try {
                        GameManager.removePlayerFromSoloQueue(player);
                        sender.sendMessage(MessageFormat.getFormattedString("You have left the solo queue."));
                    } catch (PlayerNotInQueueException e) {
                        sender.sendMessage(MessageFormat.getFormattedString(e.getMessage()));
                    }
                    // Logic to remove player from solo queue
                    sender.sendMessage(MessageFormat.getFormattedString("You have left the solo queue."));
                }
                default -> sender.sendMessage(MessageFormat.getFormattedString("Invalid action. Use 'join' or 'leave'."));
            }
            sender.sendMessage("Solo Queue command with syntax executed.");
        }, queueAction);
    }
}
