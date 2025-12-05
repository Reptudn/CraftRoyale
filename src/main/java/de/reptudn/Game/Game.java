package de.reptudn.Game;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.reptudn.Arenas.AArena;
import de.reptudn.Arenas.ArenaManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.advancements.FrameType;
import net.minestom.server.advancements.Notification;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.item.Material;
import net.minestom.server.scoreboard.Team;
import net.minestom.server.timer.TaskSchedule;

public class Game {
	private AArena arena;
	private final List<Player> players = new ArrayList<>();
	private GameState state;
	private Instant startTime;
	private Instant endTime;
	private final Duration maxGameDuration = Duration.ofMinutes(6);

	// private final Map<UUID, Team> playerTeams = new HashMap<>();

	private Instance gameInstance;

	private final int START_ELIXIR = 10; // is equal to 5 elixir
	private final int MAX_ELIXIR = 20;
	private final int PER_TICK_INCOME = 0;
	private float ELIXIR_MULTIPLIER = 1f;
	private final int TICKS_PER_SECOND = 10;

	public Game(Player... players) {

		if (players.length == 0) {
			throw new IllegalArgumentException("At least one player is required to start a game.");
		}

		if (players.length != 2 && players.length != 4) {
			throw new IllegalArgumentException("Only 2 or 4 players are allowed to start a game.");
		}

		if (players.length == 2) {
			setupSoloGame(players[0], players[1]);
		} else {
			// assign teams for 2v2
			// e.g., players[0], players[1] -> RED, players[2], players[3] -> BLUE
		}

		this.players.addAll(Arrays.asList(players));
		this.state = GameState.WAITING;

	}

	private void setupSoloGame(Player team1, Player team2) {
		this.arena = ArenaManager.getArenaByName("Goblin Stadium");
		// this.arena = ArenaManager.getArenaByTrophies();
		this.gameInstance = arena.createInstance();

		// assign teams
		Team scoreboardTeam1 = MinecraftServer.getTeamManager().createTeam(team1.getName().toString());
		scoreboardTeam1.setTeamColor(NamedTextColor.RED);
		Team scoreboardTeam2 = MinecraftServer.getTeamManager().createTeam(team2.getName().toString());
		scoreboardTeam1.setTeamColor(NamedTextColor.BLUE);
		team1.setTeam(scoreboardTeam1);
		team2.setTeam(scoreboardTeam2);

		// teleport players to spawn points
		team1.setInstance(gameInstance);
		team2.setInstance(gameInstance);
	}

	private void setupDuoGame(Player team1Player1, Player team1Player2, Player team2Player1, Player team2Player2) {
		throw new IllegalArgumentException("Duo not implemented yet.");
	}

	public AArena getArena() {
		return arena;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void startGame() {
		this.state = GameState.IN_PROGRESS;
		this.startTime = Instant.now();

		this.players.forEach(player -> {
			player.setInstance(gameInstance);
			player.setFoodSaturation(START_ELIXIR);
			player.sendNotification(
					new Notification(Component.text("Game started!"), FrameType.GOAL, Material.SADDLE));
		});

		// Scheduler gameScheduler = MinecraftServer.getSchedulerManager();
		// gameScheduler.submitTask(() -> {
		// if (this.state != GameState.IN_PROGRESS) {
		// return TaskSchedule.stop();
		// }
		// tickGame(Duration.between(this.startTime, Instant.now()).toMinutes());
		// return TaskSchedule.millis(1000 / TICKS_PER_SECOND);
		// });

		this.gameInstance.scheduler().submitTask(() -> {
			if (this.state != GameState.IN_PROGRESS) {
				return TaskSchedule.stop();
			}
			tickGame(Duration.between(this.startTime, Instant.now()).toMinutes());
			return TaskSchedule.millis(1000 / TICKS_PER_SECOND);
		});
	}

	private void tickGame(long timeElapsedInMinutes) {
		// game stuff in here

		// give players elixir with multiplier (change multiplier on specific times)
		// check win condition
		// end game when its over

		if (timeElapsedInMinutes >= 6) {
			endGame();
			return;
		} else if (timeElapsedInMinutes >= 3) {
			ELIXIR_MULTIPLIER = 3;
		} else if (timeElapsedInMinutes >= 2) {
			ELIXIR_MULTIPLIER = 1.5f;
		}

		this.players.forEach(player -> {
			player.setFood(
					(int) Math.max(player.getFood() + (PER_TICK_INCOME * ELIXIR_MULTIPLIER), MAX_ELIXIR));
		});
	}

	public void endGame() {
		this.state = GameState.ENDED;
		this.endTime = Instant.now();
		// save game into db or something
		// put players back into the normal instance (lobby)
	}

	public GameState getGameState() {
		return state;
	}

	public Duration getMaxGameDuration() {
		return maxGameDuration;
	}

	public Instant getStartTime() {
		return startTime;
	}

	public Instant getEndTime() {
		return endTime;
	}
}