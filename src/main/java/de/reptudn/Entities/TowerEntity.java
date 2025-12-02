package de.reptudn.Entities;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.metadata.display.TextDisplayMeta;
import net.minestom.server.timer.TaskSchedule;

public class TowerEntity extends EntityCreature {

    private final TowerType towerType;
    private final float maxHealth = 1000f;
    private final double attackRange = 5.0;
    private final long attackCooldownMillis = 2000;

    private float health;
    private float attackDamage;

    public TowerEntity(TowerType type, Player owner, Pos pos, float health, float attackDamage) {
        super(getEntityTypeForTowerType(type));
        this.setTeam(owner.getTeam());
        this.towerType = type;
        this.setInstance(owner.getInstance(), pos);

        Entity nameDisplay = new Entity(EntityType.TEXT_DISPLAY);

        TextDisplayMeta meta = (TextDisplayMeta) nameDisplay.getEntityMeta();
        meta.setText(Component.text(type == TowerType.KING ? "King Tower" : "Princess Tower"));

        nameDisplay.setNoGravity(true);

        nameDisplay.setInstance(owner.getInstance(), pos.add(0, 3, 0));

        this.scheduler().scheduleTask(() -> {

            if (this.getHealth() <= 0 || isRemoved() || isDead()) {
                nameDisplay.remove();
                getInstance().sendMessage(Component.text("The " + (towerType == TowerType.KING ? "King" : "Princess")
                        + " Tower of Team " + (this.getTeam() != null ? this.getTeam().getTeamName() : "(no team)")
                        + " has been destroyed!").color(NamedTextColor.RED));
                remove();
                return TaskSchedule.stop();
            }

            return TaskSchedule.tick(5);
        }, TaskSchedule.tick(1));
    }

    private static EntityType getEntityTypeForTowerType(TowerType towerType) {
        return switch (towerType) {
            case KING -> EntityType.ELDER_GUARDIAN;
            case PRINCESS -> EntityType.GLOW_SQUID;
        };
    }

    @Override
    public float getHealth() {
        return health;
    }

    @Override
    public void setHealth(float health) {
        this.health = Math.min(health, maxHealth);
    }

    public void damage(float damage) {
        setHealth(getHealth() - damage);
        if (getHealth() <= 0) {
            remove();
        }
    }

    public TowerType getTowerType() {
        return towerType;
    }
}
