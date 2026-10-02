package game.effects;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;

/**
 * Reduces the actor's health by a fixed amount.
 * If health drops to 0 or below, the actor becomes unconscious.
 * @author Ooi Pei Shuen
 * @version 1.0
 */
public class HealthLossEffect implements SideEffect {

    /**
     * Amount of health to reduce.
     */
    private final int damageAmount;

    /**
     * Creates a health loss effect.
     *
     * @param damageAmount how much health to reduce
     */
    public HealthLossEffect(int damageAmount) {
        this.damageAmount = damageAmount;
    }

    /**
     * Applies health reduction to the actor.
     * If health drops to 0, calls unconscious().
     *
     * @param actor the actor affected
     * @param map the game map
     */
    @Override
    public void apply(Actor actor, GameMap map) {
        new AttributeUpdateEffect(
                ActorAttributeOperations.DECREASE,
                BaseActorAttributes.HEALTH,
                damageAmount
        ).apply(actor, map);

        if (!actor.isConscious()) {
            actor.unconscious(map);
        }
    }
}
