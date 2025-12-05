package de.reptudn.Commands.Debug;

import de.reptudn.Entities.TowerEntity;
import de.reptudn.Entities.TroopCreature;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;

public class KillAllEntitiesCommand extends Command {
	public KillAllEntitiesCommand() {
		super("killallentities", "killall");

		setDefaultExecutor((sender, commandContext) -> {
			Player player = (Player) sender;
			player.getInstance().getEntities().stream()
					.filter(entity -> entity instanceof TowerEntity || entity instanceof TroopCreature)
					.forEach(entity -> entity.remove());
			sender.sendMessage("KillAllEntities command executed.");
		});

		var entityType = ArgumentType.String("entityType");
		addSyntax((sender, commandContext) -> {
			String type = commandContext.get(entityType);
			Player player = (Player) sender;

			switch (type.toLowerCase()) {
				case "tower" -> {
					player.getInstance().getEntities().stream()
							.filter(entity -> entity instanceof TowerEntity)
							.forEach(entity -> entity.remove());
					player.sendMessage("All towers have been killed.");
				}
				case "troop" -> {
					player.getInstance().getEntities().stream()
							.filter(entity -> entity instanceof TroopCreature)
							.forEach(entity -> entity.remove());
					player.sendMessage("All troops have been killed.");
				}
				default -> {
					player.sendMessage("Please specify a valid entity type: tower, troop");
				}
			}

		}, entityType);
	}

}
