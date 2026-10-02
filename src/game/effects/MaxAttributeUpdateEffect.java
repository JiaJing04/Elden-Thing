package game.effects;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * This class represents the side effect of updating an actor attribute's
 * maximum value
 *
 * @author ngziwei
 * @version 1.0
 */
public class MaxAttributeUpdateEffect implements SideEffect{

    private final ActorAttributeOperations operation;
    private final Enum updatedAttribute;
    private final int updatedAmount;

    /**
     * Constructor for updating an actor attribute's maximum value
     *
     * @param operation The operation used to update the actor attribute's
     *                  maximum value
     * @param updatedAttribute The targeted attribute to be updated
     * @param updatedAmount The amount of attribute to be updated
     */
    public MaxAttributeUpdateEffect(ActorAttributeOperations operation, Enum updatedAttribute, int updatedAmount) {
        this.operation = operation;
        this.updatedAttribute = updatedAttribute;
        this.updatedAmount = updatedAmount;
    }

    /**
     * This method apply the max attribute update side effect
     *
     * @param actor The actor affected by or involved in the max attribute
     *              update side effect
     * @param map The map the affected or involved actor is on
     */
    @Override
    public void apply(Actor actor, GameMap map) {
        if (actor.hasAttribute(getUpdatedAttribute())){
            actor.modifyAttributeMaximum(getUpdatedAttribute(),getOperation(),
                    getUpdatedAmount());
        }
    }

    /**
     * This method gets the operation of MaxAttributeUpdateEffect
     *
     * @return the operation of MaxAttributeUpdateEffect
     */
    public ActorAttributeOperations getOperation() {
        return operation;
    }

    /**
     * This method gets the updated attribute of MaxAttributeUpdateEffect
     *
     * @return the updated attribute of MaxAttributeUpdateEffect
     */
    public Enum getUpdatedAttribute() {
        return updatedAttribute;
    }

    /**
     * This method gets the updated amount of MaxAttributeUpdateEffect
     *
     * @return the updated amount of MaxAttributeUpdateEffect
     */
    public int getUpdatedAmount() {
        return updatedAmount;
    }
}
