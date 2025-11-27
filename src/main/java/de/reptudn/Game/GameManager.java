package de.reptudn.Game;

import java.util.HashMap;
import java.util.UUID;

public class GameManager {
	
	private static HashMap<String, Game> games;

	public static void createGame(Game game) {
		games.put(generateGameUUID(), game);
	}

	public static HashMap<String, Game> getGames() {
		return games;
	}

	public static Game getGame(String gameId) {
		return games.get(gameId);
	}

	private static String generateGameUUID() {
		return UUID.randomUUID().toString();
	}

	public static void removeGame(String gameId) {
		games.get(gameId).endGame();
		games.remove(gameId);
	}
}
