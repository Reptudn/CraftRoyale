package de.reptudn.Utils;

import org.jspecify.annotations.NonNull;

public class MessageFormat {

    public static final String PREFIX = "§8[§3CraftClash§8] §r";

    public static @NonNull String getFormattedString(String message) {
        return PREFIX + message;
    }

}
