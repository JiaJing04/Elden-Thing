package game.actions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Interface representing an object that can be listened to.
 *
 * @author hewjiajing
 * @version 1.0
 */
public interface Listenable
{
    /**
     * Adds monologue to the actor.
     */
    void addMonologue();

    /**
     * Chooses a monologue based on the condition met by actor and the game map.
     *
     * @param actor The actor interacting with the listenable object.
     * @param gameMap The game map where the interaction happens.
     * @return The selected monologue in a string format.
     */
    String chooseMonologue(Actor actor, GameMap gameMap);
}
