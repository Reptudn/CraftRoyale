package de.reptudn;

public class Settings {

	// Minecraft Server
	public static String HOST_ADDRESS;
	public static int PORT;
	public static int MAX_PLAYERS;
	public static boolean ONLINE_MODE;

	// Resourcepack Server
	public static int PORT_RESOURCEPACK;

	static {
		HOST_ADDRESS = "0.0.0.0";
		PORT = 25565;
		MAX_PLAYERS = 100;
		ONLINE_MODE = true;

		PORT_RESOURCEPACK = 25566;
	}

	public static void loadConfigFile(String path) {
		// Load settings from file or set defaults
	}
}
