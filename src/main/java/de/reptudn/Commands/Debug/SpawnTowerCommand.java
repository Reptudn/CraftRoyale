package de.reptudn.Commands.Debug;

import de.reptudn.Entities.TowerEntity;
import de.reptudn.Entities.TowerType;
import de.reptudn.Game.CardPlacementHandler;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;

public class SpawnTowerCommand extends Command {
    public SpawnTowerCommand() {
        super("spawntower", "st");

        setDefaultExecutor((sender, commandContext) -> {
            sender.sendMessage("SpawnTower command executed.");
        });

        var towerTypeArg = ArgumentType.String("towerType");
        addSyntax((sender, commandContext) -> {
            String towerType = commandContext.get(towerTypeArg);
            Player player = (Player) sender;
            Pos placementPos = CardPlacementHandler.getPlacementPosition(player);
            if (placementPos == null) {
                placementPos = player.getPosition();
            }

            switch (towerType) {
                case "king" -> {
                    sender.sendMessage("Spawning king tower...");
                    new TowerEntity(TowerType.KING, player, placementPos, 5000, 60);
                }
                case "princess" -> {
                    sender.sendMessage("Spawning princess tower...");
                    new TowerEntity(TowerType.PRINCESS, player, placementPos, 3000, 40);
                }
                default -> {
                    sender.sendMessage("Unknown tower type: " + towerType);
                }
            }

        }, towerTypeArg);
    }
}
