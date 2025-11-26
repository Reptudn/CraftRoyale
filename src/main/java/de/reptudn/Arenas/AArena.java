package de.reptudn.Arenas;

import de.reptudn.Utils.WorldPreperation;
import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.anvil.AnvilLoader;

public abstract class AArena {

    public String name;
    public int minTrophies;
    public int maxTrophies;

    public double elixirPerSecond = 1.0;

    public InstanceContainer mapInstance;

    public AArena(String name, String mapPath, int minTrophies, int maxTrophies) {
        this.name = name;
        this.minTrophies = minTrophies;
        this.maxTrophies = maxTrophies;

        try {
            this.mapInstance = MinecraftServer.getInstanceManager().createInstanceContainer(new AnvilLoader(WorldPreperation.prepareWorld(mapPath)));
        } catch (Exception e) {
            System.out.println("Error while loading arena world: " + mapPath);
            e.printStackTrace();
        }
    }

    public AArena(String name, String mapPath, int minTrophies, int maxTrophies, double elixirPerSecond) {
        this.elixirPerSecond = elixirPerSecond;
        this.name = name;
        this.minTrophies = minTrophies;
        this.maxTrophies = maxTrophies;

        try {
            this.mapInstance = MinecraftServer.getInstanceManager().createInstanceContainer(new AnvilLoader(WorldPreperation.prepareWorld(mapPath)));
        } catch (Exception e) {
            System.out.println("Error while loading arena world: " + mapPath);
            e.printStackTrace();
        }
    }

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
