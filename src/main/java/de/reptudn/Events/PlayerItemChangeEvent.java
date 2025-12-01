package de.reptudn.Events;

import de.reptudn.Game.CardPreviewHandler;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerChangeHeldSlotEvent;
import net.minestom.server.timer.TaskSchedule;

public class PlayerItemChangeEvent {

    public static void onItemChange(PlayerChangeHeldSlotEvent event) {

        Player player = event.getPlayer();

        player.sendMessage("Held slot changed to: " + event.getNewSlot());

        player.scheduler().scheduleTask(() -> {
            CardPreviewHandler.startPreview(player);
            return TaskSchedule.stop();
        }, TaskSchedule.tick(1));

    }

}
