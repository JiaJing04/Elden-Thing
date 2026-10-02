package game.effects;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Interface representing a side effect
 *
 * @author ngziwei
 * @version 1.0
 */
public interface SideEffect {

    /**
     * Method to apply the side effect
     *
     * @param actor The actor affected by or involved in the side effect
     * @param map The map the affected or involved actor is on
     */
    void apply(Actor actor, GameMap map);
}
