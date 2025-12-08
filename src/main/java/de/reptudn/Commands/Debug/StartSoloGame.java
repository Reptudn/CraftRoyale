package de.reptudn.Commands.Debug;

import de.reptudn.Game.Game;
import de.reptudn.Game.GameManager;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentString;
import net.minestom.server.entity.Player;

public class StartSoloGame extends Command {
	public StartSoloGame() {
		super("startsologame");

		setDefaultExecutor((sender, context) -> {
			sender.sendMessage("Usage: /startsologame <arenaName>");
			GameManager.createGame(Game.createDebugGame((Player) sender));
		});

		var arenaNameArg = new ArgumentString("arenaName");
		addSyntax((sender, context) -> {
			final String arenaName = context.get("arenaName");
			sender.sendMessage("Starting solo game in arena: " + arenaName);

			GameManager.createGame(Game.createDebugGame((Player) sender));
		}, arenaNameArg);

		System.out.println("Debug Command /startsologame registered.");
	}

}
