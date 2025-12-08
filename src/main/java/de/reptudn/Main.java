package de.reptudn;

import de.reptudn.Events.PlayerInventoryUpdate;
import de.reptudn.Events.PlayerItemChangeEvent;
import de.reptudn.Events.ServerList;
import net.minestom.server.Auth;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Point;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerChangeHeldSlotEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.event.server.ServerListPingEvent;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.block.Block;
import net.minestom.server.timer.TaskSchedule;
import de.reptudn.Commands.Cards.GiveCardCommand;
import de.reptudn.Commands.Cards.ListAllCardsCommands;
import de.reptudn.Commands.Debug.KillAllEntitiesCommand;
import de.reptudn.Commands.Debug.SpawnTowerCommand;
import de.reptudn.Commands.Debug.StartSoloGame;
import de.reptudn.Commands.Game.SoloQueueCommand;
import de.reptudn.Events.PlayerConnectionListener;
import de.reptudn.Game.CardPlacementHandler;
import de.reptudn.Instances.InstanceManager;
import de.reptudn.Resourcepack.ResourcePackServer;
import net.minestom.server.event.player.PlayerUseItemEvent;

public class Main {

    public static void main(String[] args) {
        System.out.println("Initializing server...");

        // MinecraftServer SERVER;
        // if (ONLINEMODE)
        // SERVER = MinecraftServer.init(new Auth.Online());
        // else
        // MinecraftServer.init();
        MinecraftServer SERVER;
        if (Settings.ONLINE_MODE) {
            SERVER = MinecraftServer.init(new Auth.Online());
        } else {
            SERVER = MinecraftServer.init();
        }

        var lobbyInstance = InstanceManager.createInstance("lobby");
        lobbyInstance.setGenerator(unit -> {
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
        lobbyInstance.setChunkSupplier(LightingChunk::new);
        lobbyInstance.scheduler().submitTask(() -> {
            for (var player : lobbyInstance.getPlayers()) {
                if (player.getInstance() != null) {
                    player.setFood(Math.min(player.getFood() + 1, 20));
                }
            }
            return TaskSchedule.tick(5);
        });

        registerEvents();
        registerCommands();

        System.out.println("Starting Minecraft Server on " + Settings.HOST_ADDRESS + ":" + Settings.PORT + "...");

        ResourcePackServer.start(Settings.PORT_RESOURCEPACK);

        MinecraftServer.setBrandName("CraftRoyale by Reptudn");

        SERVER.start(Settings.HOST_ADDRESS, Settings.PORT);
    }

    private static void registerEvents() {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();

        globalEventHandler.addListener(ServerListPingEvent.class, ServerList::Handler);
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class,
                PlayerConnectionListener::ConfigurationEventHandler);
        globalEventHandler.addListener(PlayerSpawnEvent.class, PlayerConnectionListener::PlayerSpawnHandler);
        globalEventHandler.addListener(PlayerUseItemEvent.class, CardPlacementHandler::handleCardPlacement);
        globalEventHandler.addListener(PlayerChangeHeldSlotEvent.class, PlayerItemChangeEvent::onItemChange);
        globalEventHandler.addListener(InventoryPreClickEvent.class, PlayerInventoryUpdate::onInventoryUpdate);

    }

    private static void registerCommands() {
        MinecraftServer.getCommandManager().register(new GiveCardCommand(), new ListAllCardsCommands(),
                new SoloQueueCommand(), new SpawnTowerCommand(), new KillAllEntitiesCommand(), new StartSoloGame());
    }
}