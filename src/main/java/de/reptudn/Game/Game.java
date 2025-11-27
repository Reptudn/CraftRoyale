package de.reptudn.Game;

import java.time.Duration;
import java.time.Instant;

import de.reptudn.Arenas.AArena;
import jdk.jfr.Timestamp;
import net.minestom.server.entity.Player;

public class Game {

	private AArena arena;
	private Player blue;
	private Player red;
	private GameState state;
	private Instant startTime;
	private Instant endTime;
	private Duration maxGameDuration = Duration.ofMinutes(6);

	public Game(AArena arena, Player blue, Player red) {
		this.arena = arena;
		this.blue = blue;
		this.red = red;
	}

	public AArena getArena() {
		return arena;
	}

	public Player getBluePlayer() {
		return blue;
	}
	public Player getRedPlayer() {
		return red;
	}

	public void startGame() {
		this.state = GameState.IN_PROGRESS;
		this.startTime = Instant.now();
	}

	public void endGame() {
		this.state = GameState.ENDED;
		this.endTime = Instant.now();
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