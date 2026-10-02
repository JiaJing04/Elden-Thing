package game.conditions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * A class representing a condition that checks an actor's base attribute
 *
 * @author yujiaming
 * @version 1.0
 */
public class BaseAttributeCondition implements Condition {

    /**
     * The threshold of the base attribute value wants to compare.
     */
    private final Integer threshold;

    /**
     * The base attribute wanted to be compared.
     */
    private final BaseActorAttributes baseActorAttributes;

    /**
     * The operator used to compare the base attribute value and the threshold.
     */
    private final ComparisonOperator comparisonOperator;

    /**
     * Constructor
     *
     * @param threshold The threshold of the base attribute value wanted to compare.
     * @param baseActorAttributes The base attribute wanted to be compared.
     * @param comparisonOperator The operator used to compare the base attribute
     *                           value and the threshold.
     */
    public BaseAttributeCondition(Integer threshold, BaseActorAttributes baseActorAttributes, ComparisonOperator comparisonOperator){
        this.threshold = threshold;
        this.baseActorAttributes = baseActorAttributes;
        this.comparisonOperator = comparisonOperator;
    }

    /**
     * Checks if the actor's base attribute value satisfies the condition.
     *
     * @param actor The actor whose base attribute value is being checked.
     * @param gameMap The game map the actor is on.
     * @return True if the condition is satisfied, false otherwise.
     */
    @Override
    public boolean satisfies(Actor actor, GameMap gameMap) {
        Integer attributeValue = actor.getAttribute(baseActorAttributes);
        return switch (comparisonOperator){
            case E -> attributeValue == threshold;
            case GE -> attributeValue >= threshold;
            case LE -> attributeValue <= threshold;
            case G -> attributeValue > threshold;
            case L -> attributeValue < threshold;
        };
    }

}
