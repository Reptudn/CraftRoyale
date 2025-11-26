package de.reptudn.Events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.server.ServerListPingEvent;
import net.minestom.server.ping.Status;

public class ServerList {

    public static void Handler(ServerListPingEvent e) {
        int onlinePlayers = MinecraftServer.getConnectionManager().getOnlinePlayers().size();

        e.setStatus(Status.builder()
                .description(Component.text("Welcome to CraftRoyale by Reptudn!", NamedTextColor.GOLD))
                .playerInfo(onlinePlayers, 500)
                .versionInfo(new Status.VersionInfo("CraftRoyale", 775))
                .build());
    }

}
