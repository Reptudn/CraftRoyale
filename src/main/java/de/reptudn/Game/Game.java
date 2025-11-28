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
import java.util.UUID;

import javax.swing.text.html.parser.Entity;

import de.reptudn.Arenas.AArena;
import de.reptudn.Entities.ATower;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;

public class Game {
	private final AArena arena;
	private final List<Player> players = new ArrayList<>();
	private GameState state;
	private Instant startTime;
	private Instant endTime;
	private final Duration maxGameDuration = Duration.ofMinutes(6);

	private final Map<Team, Set<EntityCreature>> teamTroops = new HashMap<>();
	private final Map<Team, Set<ATower>> teamTowers = new HashMap<>();
	private final Map<UUID, Team> playerTeams = new HashMap<>();

	private final Instance gameInstance;

	public Game(AArena arena, Player... players) {
		this.arena = arena;
		this.players.addAll(Arrays.asList(players));
		this.state = GameState.WAITING;

		this.teamTroops.put(Team.RED, new HashSet<>());
		this.teamTroops.put(Team.BLUE, new HashSet<>());

		this.teamTowers.put(Team.RED, new HashSet<>());
		this.teamTowers.put(Team.BLUE, new HashSet<>());

		int i = 0;
		for (Player player : players) {
			Team team = (i % 2 == 0) ? Team.RED : Team.BLUE;
			playerTeams.put(player.getUuid(), team);
			i++;
		}

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

	public void startGame() {
		this.state = GameState.IN_PROGRESS;
		this.startTime = Instant.now();

		this.players.forEach(player -> {
			player.setInstance(gameInstance);
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