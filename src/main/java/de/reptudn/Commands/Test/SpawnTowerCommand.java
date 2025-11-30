package de.reptudn.Commands.Test;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;

public class SpawnTowerCommand extends Command {
    public SpawnTowerCommand() {
        super("spawntower", "st");

        setDefaultExecutor((sender, commandContext) -> {
            sender.sendMessage("SpawnTower command executed.");
        });

        var towerTypeArg = ArgumentType.String("towerType");
        addSyntax((sender, commandContext) -> {
            String towerType = commandContext.get(towerTypeArg);

            switch (towerType) {
                case "king" -> {
                    sender.sendMessage("Spawning king tower...");
                }
                case "queen" -> {
                    sender.sendMessage("Spawning queen tower...");
                }
                default -> {
                    sender.sendMessage("Unknown tower type: " + towerType);
                }
            }

        }, towerTypeArg);
    }
}
