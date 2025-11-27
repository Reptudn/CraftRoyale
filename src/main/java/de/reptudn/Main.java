package de.reptudn;

import de.reptudn.Commands.GiveCardCommand;
import de.reptudn.Events.ServerList;
import net.minestom.server.Auth;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.event.server.ServerListPingEvent;
import net.minestom.server.instance.LightingChunk;

import de.reptudn.Events.PlayerConnectionListener;
import de.reptudn.Resourcepack.ResourcePackServer;

public class Main {

    public static void main(String[] args) {
        System.out.println("Initializing server...");

        MinecraftServer SERVER = MinecraftServer.init(new Auth.Online());
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();

        var instanceManager = MinecraftServer.getInstanceManager();
        var lobbyInstance = instanceManager.createInstanceContainer();
        lobbyInstance.setChunkSupplier(LightingChunk::new);

        globalEventHandler.addListener(ServerListPingEvent.class, ServerList::Handler);
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, PlayerConnectionListener::ConfigurationEventHandler);
        globalEventHandler.addListener(PlayerSpawnEvent.class, PlayerConnectionListener::PlayerSpawnHandler);

        registerCommands();
        System.out.println("Starting server...");

        ResourcePackServer.start(25566);
        SERVER.start("0.0.0.0", 25565);
    }

    private static void registerCommands() {
        MinecraftServer.getCommandManager().register(new GiveCardCommand());
    }
}