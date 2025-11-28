package de.reptudn.Events;

import de.reptudn.Instances.InstanceManager;
import de.reptudn.Resourcepack.ResourcePackServer;
import de.reptudn.Utils.MessageFormat;
import net.kyori.adventure.resource.ResourcePackInfo;
import net.kyori.adventure.resource.ResourcePackRequest;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;

public class PlayerConnectionListener {

    private static final boolean REQUIRE_RESOURCE_PACK = false; // TODO: make resourcepacks work in the future

    public static void ConfigurationEventHandler(AsyncPlayerConfigurationEvent e) {
        final Player p = e.getPlayer();
        e.setSpawningInstance(InstanceManager.getInstanceById("lobby"));
        p.setRespawnPoint(new Pos(0, 42, 0));

        ResourcePackRequest resourcePackRequest = ResourcePackRequest.resourcePackRequest()
                .packs(ResourcePackInfo.resourcePackInfo(ResourcePackServer.getResourcePackUUID(),
                        ResourcePackServer.getResourcePackURI(), ResourcePackServer.getResourcePackHash()))
                .prompt(Component.text("This Resourcepack is required to play CraftRoyale!")).required(
                        REQUIRE_RESOURCE_PACK)
                .build();
        p.sendResourcePacks(resourcePackRequest);
    }

    public static void PlayerSpawnHandler(PlayerSpawnEvent e) {
        final Player p = e.getPlayer();
        p.sendMessage(MessageFormat.getFormattedString("&6Welcome &e" + p.getUsername() + "&6 to the server!"));
    }

}
