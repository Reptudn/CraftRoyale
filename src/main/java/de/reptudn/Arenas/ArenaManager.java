package de.reptudn.Arenas;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class ArenaManager {
	private static final Map<String, AArena> ARENAS = new HashMap<>();

	static {
		ARENAS.put("Goblin Stadium", new SingleArena("Goblin Stadium", 0, 300));
		ARENAS.put("Goblin Stadium Duo", new DuoArena("Goblin Stadium Duo", 0, 300));
	}

	public static AArena getArenaByName(String name) {
		return ARENAS.get(name);
	}

    public static AArena getArenaByTrophies(int trophies) {

        AtomicReference<AArena> arena = new AtomicReference<>();
        // return test arena when trophies is smaller than 0

        ARENAS.forEach((s, a) -> {
            if (a.minTrophies <= trophies && a.maxTrophies >= trophies) {
                arena.set(a);
                return;
            };
        });

        return  arena.get();
    }

	public static Map<String, AArena> getArenas() {
		return ARENAS;
	}
}
