package de.reptudn.Arenas;

import java.util.HashMap;
import java.util.Map;

public class ArenaManager {
	private static final Map<String, AArena> ARENAS = new HashMap<>();

	static {
		ARENAS.put("Goblin Stadium", new SingleArena("Goblin Stadium", 0, 300));
		ARENAS.put("Goblin Stadium Duo", new DuoArena("Goblin Stadium Duo", 0, 300));
	}

	public static AArena getArenaByName(String name) {
		return ARENAS.get(name);
	}

	public static Map<String, AArena> getArenas() {
		return ARENAS;
	}
}
