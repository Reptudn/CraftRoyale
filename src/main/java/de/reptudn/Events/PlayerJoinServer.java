package de.reptudn.Events;

import de.reptudn.Utils.MessageFormat;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;

public class PlayerJoinServer {

    public static void ConfigurationEventHandler(AsyncPlayerConfigurationEvent e) {
        final Player p = e.getPlayer();
        e.setSpawningInstance(MinecraftServer.getInstanceManager().getInstances().iterator().next());
        p.setRespawnPoint(new Pos(0, 4, 0));
    }

    public static void PlayerSpawnHandler(PlayerSpawnEvent e) {
        final Player p = e.getPlayer();
        p.sendMessage(MessageFormat.getFormattedString("&6Welcome &e" + p.getUsername() + "&6 to the server!"));
    }

}
