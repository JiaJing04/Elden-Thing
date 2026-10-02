package game.offspring;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * This interface representing an EggHatchable (parent who is able to produce an Egg that can be consumed by other Actor)
 *
 * @author yujiaming
 * @version 1.0
 */
public interface EggHatchable {

    /**
     * Method for Actor to consume an Egg
     *
     * @param actor the actor consume the egg
     * @param gameMap the map the actor is on
     */
    void consumeEgg(Actor actor, GameMap gameMap);
}
