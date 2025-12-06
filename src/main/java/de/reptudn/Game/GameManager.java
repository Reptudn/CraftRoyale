package de.reptudn.Game;

import de.reptudn.Game.Exceptions.PlayerAlreadyInQueueException;
import de.reptudn.Game.Exceptions.PlayerNotInQueueException;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.timer.TaskSchedule;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class GameManager {

	private static HashMap<String, Game> games = new  HashMap<>();
	private static Queue<Player> waitingQueueSolo = new LinkedList<>();
	private static Queue<Player> waitingQueueDuo = new LinkedList<>();

    static {
        MinecraftServer.getSchedulerManager().scheduleTask(() -> {
            // Solo Queue Matchmaking
            while (waitingQueueSolo.size() >= 2) {
                Player player1 = waitingQueueSolo.poll();
                Player player2 = waitingQueueSolo.poll();
                Game newGame = new Game(player1, player2);
                createGame(newGame);
                newGame.startGame();
            }

            // Duo Queue Matchmaking
            while (waitingQueueDuo.size() >= 4) {
                Player player1 = waitingQueueDuo.poll();
                Player player2 = waitingQueueDuo.poll();
                Player player3 = waitingQueueDuo.poll();
                Player player4 = waitingQueueDuo.poll();
                Game newGame = new Game(player1, player2, player3, player4);
                createGame(newGame);
                newGame.startGame();
            }
        }, TaskSchedule.tick(20), TaskSchedule.tick(10)); // Run every second (20 ticks)});
    }

	public static void createGame(Game game) {
		games.put(generateGameUUID(), game);
	}

	public static void addPlayerToSoloQueue(Player player) throws PlayerAlreadyInQueueException {

        if (GameManager.isPlayerInGame(player)) {
            throw new PlayerAlreadyInQueueException("Player is already in a game.");
        }

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

    public static boolean isPlayerInGame(Player player) {
        for (Game game : games.values()) {
            if (game.getPlayers().contains(player)) {
                return true;
            }
        }
        return false;
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
