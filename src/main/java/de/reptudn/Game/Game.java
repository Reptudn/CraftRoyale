package de.reptudn.Game;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
// import java.util.UUID;

import de.reptudn.Arenas.AArena;
import de.reptudn.Entities.ATower;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.advancements.FrameType;
import net.minestom.server.advancements.Notification;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.item.Material;
import net.minestom.server.timer.Scheduler;
import net.minestom.server.timer.TaskSchedule;

public class Game {
	private final AArena arena;
	private final List<Player> players = new ArrayList<>();
	private GameState state;
	private Instant startTime;
	private Instant endTime;
	private final Duration maxGameDuration = Duration.ofMinutes(6);

	private final Map<Team, Set<EntityCreature>> teamTroops = new HashMap<>();
	private final Map<Team, Set<ATower>> teamTowers = new HashMap<>();
	// private final Map<UUID, Team> playerTeams = new HashMap<>();

	private final Instance gameInstance;

	private final float START_ELIXIR = 10f; // is equal to 5 elixir
	private final float MAX_ELIXIR = 18f;
	private final float PER_TICK_INCOME = 0.2f;
	private float ELIXIR_MULTIPLIER = 1f;
	private final int TICKS_PER_SECOND = 10;

	public Game(AArena arena, Player... players) {
		this.arena = arena;
		this.players.addAll(Arrays.asList(players));
		this.state = GameState.WAITING;

		this.teamTroops.put(Team.RED, new HashSet<>());
		this.teamTroops.put(Team.BLUE, new HashSet<>());

		this.teamTowers.put(Team.RED, new HashSet<>());
		this.teamTowers.put(Team.BLUE, new HashSet<>());

		// int i = 0;
		// for (Player player : players) {
		// Team team = (i % 2 == 0) ? Team.RED : Team.BLUE;
		// playerTeams.put(player.getUuid(), team);
		// i++;
		// }

		// create game instance here
		gameInstance = arena.createInstance();
	}

	public void addTroopToTeam(Team team, EntityCreature troop) {
		Set<EntityCreature> troops = teamTroops.get(team);
		if (troops != null) {
			troops.add(troop);
		}
	}

	public Set<EntityCreature> getTroopsOfTeam(Team team) {
		return teamTroops.get(team);
	}

	public Set<ATower> getTowersOfTeam(Team team) {
		return teamTowers.get(team);
	}

	public Set<EntityCreature> getEnemyTroops(Team team) {
		Team enemyTeam = (team == Team.RED) ? Team.BLUE : Team.RED;
		return teamTroops.get(enemyTeam);
	}

	public AArena getArena() {
		return arena;
	}

	public List<Player> getPlayers() {
		return players;
	}

	private Scheduler gameScheduler;

	public void startGame() {
		this.state = GameState.IN_PROGRESS;
		this.startTime = Instant.now();

		this.players.forEach(player -> {
			player.setInstance(gameInstance);
			player.setFoodSaturation(START_ELIXIR);
			player.sendNotification(
					new Notification(Component.text("Game started!"), FrameType.GOAL, Material.SADDLE));
		});

		// add game tick task
		gameScheduler = MinecraftServer.getSchedulerManager();
		gameScheduler.submitTask(() -> {
			tickGame();
			return TaskSchedule.millis(1000 / TICKS_PER_SECOND);
		});
		// add task to automatically change the elixir multiplier after a certain time
	}

	private void tickGame() {
		// game stuff in here

		// give players elixir with multiplier (change multiplier on specific times)
		// check win condition
		// end game when its over

		this.players.forEach(player -> {
			// give players elixir
			player.setFoodSaturation(
					Math.max(player.getFoodSaturation() + (PER_TICK_INCOME * ELIXIR_MULTIPLIER), MAX_ELIXIR));
		});
	}

	public void endGame() {
		this.state = GameState.ENDED;
		this.endTime = Instant.now();
		// this.gameScheduler.cancel();

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