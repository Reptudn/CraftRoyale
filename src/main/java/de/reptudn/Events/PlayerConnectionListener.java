package de.reptudn.Events;

import de.reptudn.Cards.ACard;
import de.reptudn.Cards.CardManager;
import de.reptudn.Instances.InstanceManager;
import de.reptudn.Resourcepack.ResourcePackServer;
import net.kyori.adventure.resource.ResourcePackInfo;
import net.kyori.adventure.resource.ResourcePackRequest;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.scoreboard.Team;

import java.time.Duration;

public class PlayerConnectionListener {

        private static final boolean REQUIRE_RESOURCE_PACK = false; // TODO: make resourcepacks work in the future

        public static void ConfigurationEventHandler(AsyncPlayerConfigurationEvent e) {
                final Player p = e.getPlayer();
                e.setSpawningInstance(InstanceManager.getInstanceById("lobby"));
                p.setRespawnPoint(new Pos(0, 42, 0));

                ResourcePackRequest resourcePackRequest = ResourcePackRequest.resourcePackRequest()
                                .packs(ResourcePackInfo.resourcePackInfo(ResourcePackServer.getResourcePackUUID(),
                                                ResourcePackServer.getResourcePackURI(),
                                                ResourcePackServer.getResourcePackHash()))
                                .prompt(Component.text("This Resourcepack is required to play CraftRoyale!")).required(
                                                REQUIRE_RESOURCE_PACK)
                                .build();
                p.sendResourcePacks(resourcePackRequest);
        }

        public static void PlayerSpawnHandler(PlayerSpawnEvent e) {
                final Player p = e.getPlayer();
                p.showTitle(Title.title(
                                Component.text("Welcome ").color(NamedTextColor.GOLD)
                                                .append(Component.text(p.getUsername()).color(NamedTextColor.YELLOW)),
                                Component.text("to CraftRoyale!").color(NamedTextColor.GOLD),
                                Title.Times.times(Duration.ofMillis(500), Duration.ofSeconds(1),
                                                Duration.ofMillis(500))));

                Team team = MinecraftServer.getTeamManager().createTeam(p.getUsername());
                team.setTeamColor(NamedTextColor.DARK_GREEN);
                p.setTeam(team);

                p.getInventory()
                                .addItemStack(CardManager.getCardByName("Golem").createItemStack());

                p.getInventory()
                                .addItemStack(CardManager.getCardByName("Archers").createItemStack());
        }

}
