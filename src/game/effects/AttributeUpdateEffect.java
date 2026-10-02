package game.effects;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * This class represents the side effect of updating an actor attribute
 *
 * @author ngziwei
 * @version 1.0
 */
public class AttributeUpdateEffect implements SideEffect{

    private final ActorAttributeOperations operation;
    private final Enum updatedAttribute;
    private final int updatedAmount;

    /**
     * Constructor for AttributeUpdateEffect object
     *
     * @param operation The operation used to update the actor attribute
     * @param updatedAttribute The targeted attribute to be updated
     * @param updatedAmount The amount of attribute to be updated
     */
    public AttributeUpdateEffect(ActorAttributeOperations operation, Enum updatedAttribute, int updatedAmount) {
        this.operation = operation;
        this.updatedAttribute = updatedAttribute;
        this.updatedAmount = updatedAmount;
    }

    /**
     * This method apply the attribute update side effect
     *
     * @param actor The actor affected by or involved in the attribute update
     *              side effect
     * @param map The map the affected or involved actor is on
     */
    @Override
    public void apply(Actor actor, GameMap map) {
        if (actor.hasAttribute(updatedAttribute)){
            actor.modifyAttribute(updatedAttribute,operation,updatedAmount);
        }
    }
}
