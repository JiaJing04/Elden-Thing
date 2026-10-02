package game.offspring;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * This interface representing the item parent
 * Actor who can produce an Item
 *
 * @author yujiaming
 * @version 1.0
 */
public interface ItemParent {

    /**
     * Method to check whether the Item can be produced
     *
     * @param actor the actor involved in checking
     * @param map the actor is currently on
     * @return True if can produce, false otherwise
     */
    boolean canProduceItem(Actor actor, GameMap map);

    /**
     * Method to produce the Item
     *
     * @param actor the actor involved in item producing
     * @param map the map the actor is on
     */
    Item produceItem(Actor actor, GameMap map);

}
