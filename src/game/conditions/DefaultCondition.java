package game.conditions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * A class representing a default condition
 *
 * @author yujiaming
 * @version 1.0
 */
public class DefaultCondition implements Condition {

    /**
     * Constructor
     */
    public DefaultCondition(){
    }

    /**
     * This method always return true by default
     *
     * @param actor the Actor involved in the Condition checking
     * @param gameMap gameMap of the Actor involved in the Condition checking
     * @return true
     */
    @Override
    public boolean satisfies(Actor actor, GameMap gameMap) {
        return true;
    }
}
