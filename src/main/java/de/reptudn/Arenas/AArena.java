package de.reptudn.Arenas;

public abstract class AArena {

    public String name;
    public int minTrophies;
    public int maxTrophies;

    public double elixirPerSecond = 1.0;

    public AArena(String name, int minTrophies, int maxTrophies) {
        this.name = name;
        this.minTrophies = minTrophies;
        this.maxTrophies = maxTrophies;
    }

    public AArena(String name, int minTrophies, int maxTrophies, double elixirPerSecond) {
        this.elixirPerSecond = elixirPerSecond;
        this.name = name;
        this.minTrophies = minTrophies;
        this.maxTrophies = maxTrophies;
    }

}
