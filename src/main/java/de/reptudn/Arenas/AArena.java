package de.reptudn.Arenas;

import java.nio.file.Path;

import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.anvil.AnvilLoader;

public abstract class AArena {

    public String name;
    public int minTrophies;
    public int maxTrophies;

    public double elixirPerSecond = 1.0;

    // private static final Path WORLDS_DIR = Paths.get("worlds");

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
        // Path worldPath = ensureWorldExtracted("arenas/" + this.name);

        if (!Path.of("arenas/" + this.name).toFile().exists())
            throw new RuntimeException("Arena world not found: " + this.name);

        instance.setChunkLoader(new AnvilLoader("arenas/" + this.name));
        // instance.setGenerator(unit -> {
        // final Point start = unit.absoluteStart();
        // final Point size = unit.size();
        // for (int x = 0; x < size.blockX(); x++) {
        // for (int z = 0; z < size.blockZ(); z++) {
        // for (int y = 0; y < Math.min(40 - start.blockY(), size.blockY()); y++) {
        // unit.modifier().setBlock(start.add(x, y, z), Block.DIAMOND_BLOCK);
        // }
        // }
        // }
        // });

        // load the corresponding arena map here
        return instance;
    }

    public String getName() {
        return name;
    }

    // private Path ensureWorldExtracted(String resourcePath) {
    // try {
    // // Create worlds directory if it doesn't exist
    // Files.createDirectories(WORLDS_DIR);

    // Path targetPath = WORLDS_DIR.resolve(name);

    // // If world doesn't exist, extract it
    // if (!Files.exists(targetPath)) {
    // System.out.println("Extracting world: " + name);
    // extractWorld(resourcePath, targetPath);
    // }

    // return targetPath;
    // } catch (IOException e) {
    // throw new RuntimeException("Failed to extract world: " + name, e);
    // }
    // }

    // private void extractWorld(String resourcePath, Path targetPath) throws
    // IOException {
    // // For Anvil worlds, you need to copy region files, level.dat, etc.
    // // This is a simplified example - adjust based on your world structure

    // String[] worldFiles = {
    // "region",
    // "level.dat",
    // "data"
    // };

    // for (String file : worldFiles) {
    // String resourceFile = resourcePath + "/" + file;
    // Path targetFile = targetPath.resolve(file);

    // // Create parent directories
    // Files.createDirectories(targetFile.getParent());

    // // Copy file from resources
    // try (InputStream is =
    // getClass().getClassLoader().getResourceAsStream(resourceFile)) {
    // if (is != null) {
    // Files.copy(is, targetFile, StandardCopyOption.REPLACE_EXISTING);
    // }
    // }
    // }
    // }
}
