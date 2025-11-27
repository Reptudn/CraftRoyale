package de.reptudn.Game;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

public enum Team {
	RED,
	BLUE;

	public static TextColor getTeamColor(Team team) {
		return switch (team) {
			case RED -> NamedTextColor.RED;
			case BLUE -> NamedTextColor.BLUE;
		};
	}
}