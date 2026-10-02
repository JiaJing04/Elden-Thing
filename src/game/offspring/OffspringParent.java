package game.offspring;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * This interface representing the offspring parent
 * Actor who can produce an Offspring
 *
 * @author yujiaming
 * @version 1.0
 */
public interface OffspringParent {
    /**
     * Method to check whether the offspring can be produced
     *
     * @param actor the actor involved in checking
     * @param map the actor is currently on
     * @return True if can produce, false otherwise
     */
    boolean canProduceOffspring(Actor actor, GameMap map);

    /**
     * Method to produce the off spring
     *
     * @param actor the actor involved in offspring producing
     * @param map the map the actor is on
     */
    Actor produceOffspring(Actor actor, GameMap map);
}
