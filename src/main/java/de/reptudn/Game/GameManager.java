package de.reptudn.Game;

import de.reptudn.Game.Exceptions.PlayerAlreadyInQueueException;
import de.reptudn.Game.Exceptions.PlayerNotInQueueException;
import net.minestom.server.entity.Player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class GameManager {

	private static HashMap<String, Game> games = new  HashMap<>();
	private static Queue<Player> waitingQueueSolo = new LinkedList<>();
	private static Queue<Player> waitingQueueDuo = new LinkedList<>();

	public static void createGame(Game game) {
		games.put(generateGameUUID(), game);
	}

	public static void addPlayerToSoloQueue(Player player) throws PlayerAlreadyInQueueException {
		if (waitingQueueSolo.contains(player)) {
			throw new PlayerAlreadyInQueueException("Player is already in the solo queue.");
		}
		waitingQueueSolo.add(player);
	}

	public static void removePlayerFromSoloQueue(Player player) throws PlayerNotInQueueException {
		if (!waitingQueueSolo.contains(player)) {
			throw new PlayerNotInQueueException("Player is not in the solo queue.");
		}
		waitingQueueSolo.remove(player);
	}

	public static boolean isPlayerInSoloQueue(Player player) {
        return waitingQueueSolo.contains(player);
	}

	public static boolean isPlayerInDuoQueue(Player player) {
		return waitingQueueDuo.contains(player);
	}

	public static void addPlayerToDuoQueue(Player player) {
		waitingQueueDuo.add(player);
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
