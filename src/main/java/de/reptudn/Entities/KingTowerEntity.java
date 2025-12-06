package de.reptudn.Entities;

import java.util.ArrayList;
import java.util.List;

import de.reptudn.Entities.AI.IBehavior;
import de.reptudn.Entities.AI.Attack.AttackClosestEnemyTroop;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.component.DataComponents;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.*;
import net.minestom.server.entity.ai.EntityAIGroup;
import net.minestom.server.entity.metadata.golem.IronGolemMeta;
import net.minestom.server.entity.metadata.water.GlowSquidMeta;
import net.minestom.server.instance.Instance;

public class KingTowerEntity extends ATower {

    private final float maxHealth = 1000f;
    private final double attackRange = 5.0;
    private final long attackCooldownMillis = 2000;

    private final List<IBehavior> behaviors = new ArrayList<>();

    private float health;
    private float attackDamage;

    public KingTowerEntity(Player owner, Pos pos, float health, float attackDamage, Instance instace){
        super(health, attackDamage);
        this.setTeam(owner.getTeam());
        this.setInstance(instace, pos);
        this.health = health;
        this.attackDamage = attackDamage;

        behaviors.add(new AttackClosestEnemyTroop(this.attackRange, this.attackDamage, this.attackCooldownMillis));

        addAIGroup(new EntityAIGroup() {
            @Override
            public void tick(long time) {
                if (getHealth() <= 0) {
                    // Death callback here later
                    despawn();
                    getViewersAsAudience()
                            .sendMessage(Component.text("King Tower has been destroyed!").color(NamedTextColor.RED));
                    return;
                }
                runBehaviors(time);
            }
        });
    }

    private void runBehaviors(long time) {
        for (IBehavior behavior : behaviors) {
            behavior.tick(this, time);
        }
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

}
