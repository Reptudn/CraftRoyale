package de.reptudn.Utils;

import de.reptudn.Main;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.Map;

public class WorldPreperation {

    public static void copyResourceFolder(String resourceFolder, Path target) throws IOException, URISyntaxException {
        var url = Main.class.getClassLoader().getResource(resourceFolder);
        if (url == null) throw new IllegalArgumentException("Missing: " + resourceFolder);

        URI uri = url.toURI();
        FileSystem fs = uri.getScheme().equals("jar")
                ? FileSystems.newFileSystem(uri, Map.of())
                : FileSystems.getDefault();

        Path jarPath = fs.getPath(resourceFolder);

        try (var stream = Files.walk(jarPath)) {
            for (Path src : stream.toList()) {
                Path dest = target.resolve(jarPath.relativize(src).toString());
                if (Files.isDirectory(src)) {
                    Files.createDirectories(dest);
                } else {
                    Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }


    public static Path prepareWorld(String worldName) throws IOException, URISyntaxException {
        Path tempDir = Paths.get("temp-worlds").resolve(worldName);
        if (!Files.exists(tempDir)) {
            Files.createDirectories(tempDir);
            copyResourceFolder("arenas/" + worldName, tempDir);
        }
        return tempDir;
    }

}
