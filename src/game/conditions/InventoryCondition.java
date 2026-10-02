package game.conditions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * A class representing a condition that checks an actor's inventory size.
 *
 * @author hewjiajing
 * @version 1.0
 */
public class InventoryCondition implements Condition
{
    /**
     * The threshold of the inventory size wanted to compare.
     */
    private final Integer threshold;

    /**
     * The operator used to compare the inventory size and the threshold.
     */
    private final ComparisonOperator comparisonOperator;

    /**
     * Constructor.
     *
     * @param threshold The threshold of the inventory size wanted to compare.
     * @param comparisonOperator The operator used to compare the inventory size.
     */
    public InventoryCondition(int threshold, ComparisonOperator comparisonOperator)
    {
        this.threshold = threshold;
        this.comparisonOperator = comparisonOperator;
    }

    /**
     * Checks if the actor's inventory size satisfies the condition.
     *
     * @param actor The actor whose inventory is being checked.
     * @param gameMap The game map the actor is on.
     * @return True if the condition is satisfied, false otherwise.
     */
    @Override
    public boolean satisfies(Actor actor, GameMap gameMap)
    {
        int inventorySize = actor.getItemInventory().size();

        return switch (comparisonOperator)
        {
            case E -> inventorySize == threshold;
            case GE -> inventorySize >= threshold;
            case LE -> inventorySize <= threshold;
            case G -> inventorySize > threshold;
            case L -> inventorySize < threshold;
        };
    }

}
