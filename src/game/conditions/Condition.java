package game.conditions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

/**
 * This interface represents condition
 *
 * @author yujiaming
 * @version 1.0
 */
public interface Condition {

    /**
     * Method to determine whether the condition is satisfied
     *
     * @param actor the Actor involved in condition checking
     * @param gameMap gameMap of the Actor is currently on
     * @return boolean value whether the condition is satisfied
     */
    boolean satisfies(Actor actor, GameMap gameMap);

    /**
     * Method to determine whether the condition is satisfied
     *
     * @param location the location involved in condition checking
     * @return boolean value whether the condition is satisfied
     */
    default boolean satisfies(Location location) {
        return false;
    }
}
