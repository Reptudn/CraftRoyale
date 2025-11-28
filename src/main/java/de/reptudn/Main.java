package de.reptudn;

import de.reptudn.Events.ServerList;
import net.minestom.server.Auth;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Point;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.event.server.ServerListPingEvent;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.block.Block;
import de.reptudn.Commands.Cards.GiveCardCommand;
import de.reptudn.Commands.Cards.ListAllCardsCommands;
import de.reptudn.Events.PlayerConnectionListener;
import de.reptudn.Game.CardPlacementHandler;
import de.reptudn.Instances.InstanceManager;
import de.reptudn.Resourcepack.ResourcePackServer;
import net.minestom.server.event.player.PlayerUseItemEvent;

public class Main {

    // private static final boolean ONLINEMODE = false;

    public static void main(String[] args) {
        System.out.println("Initializing server...");

        // MinecraftServer SERVER;
        // if (ONLINEMODE)
        // SERVER = MinecraftServer.init(new Auth.Online());
        // else
        // MinecraftServer.init();
        MinecraftServer SERVER = MinecraftServer.init();

        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();

        InstanceManager.createInstance("lobby").setGenerator(unit -> {
            final Point start = unit.absoluteStart();
            final Point size = unit.size();
            for (int x = 0; x < size.blockX(); x++) {
                for (int z = 0; z < size.blockZ(); z++) {
                    for (int y = 0; y < Math.min(40 - start.blockY(), size.blockY()); y++) {
                        unit.modifier().setBlock(start.add(x, y, z), Block.STONE);
                    }
                }
            }
        });
        InstanceManager.getInstanceById("lobby").setChunkSupplier(LightingChunk::new);

        globalEventHandler.addListener(ServerListPingEvent.class, ServerList::Handler);
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class,
                PlayerConnectionListener::ConfigurationEventHandler);
        globalEventHandler.addListener(PlayerSpawnEvent.class, PlayerConnectionListener::PlayerSpawnHandler);
        globalEventHandler.addListener(PlayerUseItemEvent.class, CardPlacementHandler::handleCardPlacement);

        registerCommands();
        System.out.println("Starting server...");

        ResourcePackServer.start(25566);
        SERVER.start("0.0.0.0", 25565);
    }

    private static void registerCommands() {
        MinecraftServer.getCommandManager().register(new GiveCardCommand(), new ListAllCardsCommands());
    }
}