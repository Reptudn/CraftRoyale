package de.reptudn.Events;

import de.reptudn.Game.CardPreviewHandler;
import net.minestom.server.entity.Player;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.timer.TaskSchedule;

public class PlayerInventoryUpdate {

    public static void onInventoryUpdate(InventoryPreClickEvent event) {
        Player player = event.getPlayer();

        player.scheduler().scheduleTask(() -> {
            CardPreviewHandler.startPreview(player);
            return TaskSchedule.stop();
        },  TaskSchedule.tick(2));
    }
}
