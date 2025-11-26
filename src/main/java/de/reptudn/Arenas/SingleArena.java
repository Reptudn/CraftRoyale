package de.reptudn.Arenas;

import net.minestom.server.entity.Player;

public class SingleArena extends AArena{

    public Player player1;
    public Player player2;

    public SingleArena(String name, String worldPath, int minTrophies, int maxTrophies, double elixirPerSecond) {
        super(name, worldPath, minTrophies, maxTrophies, elixirPerSecond);
    }

    public SingleArena(String name, String worldPath, int minTrophies, int maxTrophies) {
        super(name, worldPath, minTrophies, maxTrophies);
    }
}
