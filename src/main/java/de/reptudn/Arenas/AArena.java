package de.reptudn.Arenas;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Point;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.block.Block;

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

    // basic world for now... later load the corresponding arena map
    public InstanceContainer createInstance() {
        InstanceContainer instance = MinecraftServer.getInstanceManager().createInstanceContainer();
        instance.setChunkSupplier(LightingChunk::new);
        instance.setGenerator(unit -> {
            final Point start = unit.absoluteStart();
            final Point size = unit.size();
            for (int x = 0; x < size.blockX(); x++) {
                for (int z = 0; z < size.blockZ(); z++) {
                    for (int y = 0; y < Math.min(40 - start.blockY(), size.blockY()); y++) {
                        unit.modifier().setBlock(start.add(x, y, z), Block.DIAMOND_BLOCK);
                    }
                }
            }
        });

        // load the corresponding arena map here
        return instance;
    }

    public String getName() {
        return name;
    }
}
