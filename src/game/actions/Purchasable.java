package game.actions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * An interface representing entities that can be purchased.
 *
 * @author ngziwei
 * @version 1.0
 */
public interface Purchasable {

    /**
     * This method shows the effects when the purchasable entities be purchased
     * successfully.
     *
     * @param actor The actor who purchase the purchasable entity
     * @param map The map the actor currently on
     * @return a string showing the effects when the purchasable entities be
     *          purchased
     */
    String purchased(Actor actor, GameMap map);
}
