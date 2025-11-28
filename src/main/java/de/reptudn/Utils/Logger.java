package de.reptudn.Utils;

public class Logger {
	public static void logInfo(String message) {
		System.out.println("[INFO] " + message);
	}

	public static void logWarning(String message) {
		System.out.println("[WARNING] " + message);
	}

	public static void logError(String message) {
		System.err.println("[ERROR] " + message);
	}
}
