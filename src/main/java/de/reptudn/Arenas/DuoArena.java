package de.reptudn.Arenas;

import net.minestom.server.entity.Player;

public class DuoArena extends AArena{

    Player[] teamRed = new Player[2];
    Player[] teamBlue = new Player[2];

    public DuoArena(String name, int minTrophies, int maxTrophies, double elixirPerSecond) {
        super(name, minTrophies, maxTrophies, elixirPerSecond);
    }

    public DuoArena(String name, int minTrophies, int maxTrophies) {
        super(name, minTrophies, maxTrophies);
    }
}
