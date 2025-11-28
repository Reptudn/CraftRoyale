package de.reptudn.Resourcepack;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Stream;

import com.sun.net.httpserver.HttpServer;

public class ResourcePackServer {

	private static final int DEFAULT_PORT = 25566;
	private static final String DEFAULT_ADDRESS = "0.0.0.0";

	public static void start(int port) {
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
			server.createContext("/resourcepack", new ResourcePackHandler());
			server.setExecutor(null);
			server.start();
			System.out.println("Resource Pack Server started on port " + port);
		} catch (IOException e) {
			System.out.println("Failed to start Resource Pack Server on port " + port);
		}
	}

	private static String cashedResourcePackHash = null;
	public static String getResourcePackHash() {

		if (cashedResourcePackHash != null) {
			return cashedResourcePackHash;
		}

		cashedResourcePackHash = calculateResourcePackHash();
		return cashedResourcePackHash;
	}


	private static String resourcePackHash = null;
	public static String calculateResourcePackHash() {
		if (resourcePackHash != null) {
			return resourcePackHash;
		}

		resourcePackHash = calculateResourcePackHash(false);
		return resourcePackHash;
	}

	public static String calculateResourcePackHash(boolean recalculate) {
		if (resourcePackHash != null && !recalculate) {
			return resourcePackHash;
		}

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			URI resourceUri = ResourcePackServer.class.getResource("/resourcepack").toURI();
			Path resourcePath;

			if (resourceUri.getScheme().equals("jar")) {
				FileSystem fs = FileSystems.newFileSystem(resourceUri, Collections.emptyMap());
				resourcePath = fs.getPath("/resourcepack");
			} else {
				resourcePath = Paths.get(resourceUri);
			}

			try (Stream<Path> paths = Files.walk(resourcePath)) {
				paths.filter(Files::isRegularFile).sorted().forEach(path -> {
					try (InputStream is = Files.newInputStream(path)) {
						byte[] buffer = new byte[8192];
						int bytesRead;
						while ((bytesRead = is.read(buffer)) != -1) {
							digest.update(buffer, 0, bytesRead);
						}
					} catch (IOException e) {
						System.err.println("Error reading file for hash calculation: " + path);
					}
				});
			}

			byte[] hashBytes = digest.digest();
			StringBuilder hexString = new StringBuilder();
			for (byte b : hashBytes) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			resourcePackHash = hexString.toString();
			System.out.println("Calculated Resource Pack Hash: " + resourcePackHash);
			return resourcePackHash;

		} catch (IOException | URISyntaxException | NoSuchAlgorithmException e) {
			System.err.println("Error calculating resource pack hash: " + e.getMessage());
			return "";
		}
	}

	private static URI resourcePackURI = null;
	public static URI getResourcePackURI() {
		if (resourcePackURI != null) {
			return resourcePackURI;
		}

		resourcePackURI = URI.create("http://+" + DEFAULT_ADDRESS + ":" + DEFAULT_PORT + "/resourcepack/pack.zip");
		return resourcePackURI;
	}

	private static UUID resourcePackUUID = null;
	public static UUID getResourcePackUUID() {

		if (resourcePackUUID != null) {
			return resourcePackUUID;
		}
		// Return a fixed UUID for the resource pack
		resourcePackUUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
		return resourcePackUUID;
	}
}
