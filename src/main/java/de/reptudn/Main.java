package de.reptudn;

import de.reptudn.Arenas.AArena;
import de.reptudn.Arenas.DuoArena;
import de.reptudn.Arenas.SingleArena;
import de.reptudn.Cards.*;
import de.reptudn.Commands.GiveCardCommand;
import de.reptudn.Events.PlayerJoinServer;
import de.reptudn.Events.ServerList;
import net.minestom.server.Auth;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.event.server.ServerListPingEvent;
import net.minestom.server.instance.LightingChunk;
import java.util.HashMap;

public class Main {

    public static HashMap<String, ACard> CARDS;
    public static HashMap<String, AArena> ARENAS;

    public static void main(String[] args) {
        System.out.println("Initializing server...");
        registerCards();
        registerArenas();

        MinecraftServer SERVER = MinecraftServer.init(new Auth.Online());
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();

        var instanceManager = MinecraftServer.getInstanceManager();
        var lobbyInstance = instanceManager.createInstanceContainer();
        lobbyInstance.setChunkSupplier(LightingChunk::new);

        globalEventHandler.addListener(ServerListPingEvent.class, ServerList::Handler);
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, PlayerJoinServer::ConfigurationEventHandler);
        globalEventHandler.addListener(PlayerSpawnEvent.class, PlayerJoinServer::PlayerSpawnHandler);

        registerCommands();
        System.out.println("Starting server...");
        SERVER.start("0.0.0.0", 25565);
    }

    private static void registerArenas() {
        ARENAS = new HashMap<>();

        ARENAS.put("Goblin Stadium", new SingleArena("Goblin Stadium", 0, 300));
        ARENAS.put("Goblin Stadium Duo", new DuoArena("Goblin Stadium Duo", 0, 300));
    }

    private static void registerCards() {
        CARDS = new HashMap<>();

        CARDS.put("Archers", new TroopCard("Archers", CardRarity.COMMON, 3, 304, 112, 0.9));
        CARDS.put("Baby-Dragon", new TroopCard("Baby Dragon", CardRarity.RARE, 4, 1152, 161, 1.5));
        CARDS.put("Barbarians", new TroopCard("Barbarians", CardRarity.COMMON, 5, 670, 192, 1.3));
        CARDS.put("Giant", new TroopCard("Giant", CardRarity.COMMON, 5, 4090, 253, 1.5));
        CARDS.put("Fireball", new SpellCard("Fireball", CardRarity.RARE, 4, 688, 207, 2.5));
        CARDS.put("Arrows", new SpellCard("Arrows", CardRarity.COMMON, 3, 366, 93, 3.5));
        CARDS.put("Mini-P.E.K.K.A", new TroopCard("Mini P.E.K.K.A", CardRarity.RARE, 4, 1433, 755, 1.6));
        CARDS.put("Elixir-Collector", new BuildingCard("Elixir Collector", CardRarity.RARE, 6, 1164, 30.0));
    }

    private static void registerCommands() {
        MinecraftServer.getCommandManager().register(new GiveCardCommand());
    }
}