package de.reptudn.Game;

import de.reptudn.Cards.ACard;
import de.reptudn.Cards.CardManager;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.metadata.display.BlockDisplayMeta;
import net.minestom.server.entity.metadata.display.ItemDisplayMeta;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.timer.TaskSchedule;

import java.util.HashMap;
import java.util.Map;

public class CardPreviewHandler {

    private static final Map<Player, Entity> activePreview = new HashMap<>();
    private static final int PREVIEW_UPDATE_DELAY_TICKS = 5;

    public static void startPreview(Player player) {
        ItemStack item = player.getItemInMainHand();
        ACard card = CardManager.getCardByItem(item);

        if (card == null) {
            System.out.println("No valid card found for preview for player " + player.getUsername());
            return;
        }

        stopPreview(player);

        Entity previewEntity = createPreviewEntity(card, player);
        activePreview.put(player, previewEntity);
        System.out.println("Started preview for card " + card.getName() + " for player " + player.getUsername());

        previewEntity.scheduler().scheduleTask(() -> {

            if (!isHoldingCard(player)) {
                stopPreview(player);
                return TaskSchedule.stop();
            }

            Pos previewPos = CardPlacementHandler.getPlacementPosition(player);
            if (previewPos == null) {
                previewEntity.teleport(new Pos(0, -100, 0));
            } else {
                Pos alteredPreviewPos = previewPos.add(0, 1.2, 0);
                if (!previewEntity.getPosition().equals(alteredPreviewPos))
                    previewEntity.teleport(alteredPreviewPos);
            }

            return TaskSchedule.tick(PREVIEW_UPDATE_DELAY_TICKS);
        }, TaskSchedule.tick(1));
    }

    public static void stopPreview(Player player) {
        Entity previewEntity = activePreview.remove(player);
        if (previewEntity != null) {
            previewEntity.remove();
            System.out.println("Removed preview entity for player " + player.getUsername());
        }
    }

    // TODO: later maybe also add a text display with card info
    private static Entity createPreviewEntity(ACard card, Player player) {
        Entity previewItem = new Entity(EntityType.ITEM_DISPLAY);

        ItemDisplayMeta meta = (ItemDisplayMeta) previewItem.getEntityMeta();
        meta.setItemStack(ItemStack.of(Material.DIAMOND_BLOCK)); // later change it depending on the type maybe
        meta.setInvisible(false);
        meta.setSilent(true);
        meta.setHasGlowingEffect(true);
        meta.setScale(new Vec(0.5, 0.5, 0.5));
        meta.setTransformationInterpolationStartDelta(10);
        meta.setTransformationInterpolationDuration(2);

        // preview.getViewers().clear();
        // preview.addViewer(player);

        previewItem.updateViewableRule(p -> p.equals(player));

        Pos placementPos = CardPlacementHandler.getPlacementPosition(player);
        if (placementPos == null) {
            placementPos = new Pos(0, -100, 0);
        } else {
            placementPos = placementPos.add(0, 1.2, 0);
        }
        previewItem.setInstance(player.getInstance(), placementPos);

        return previewItem;
    }

    private static boolean isHoldingCard(Player player) {
        ItemStack currentItem = player.getItemInMainHand();
        ACard card = CardManager.getCardByItem(currentItem);
        return card != null;
    }

}
