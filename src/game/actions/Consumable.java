package game.actions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * An interface for objects that can be consumed by an Actor.
 *
 * @author ooipeishuen
 * @version 1.0
 */
public interface Consumable {

    /**
     * A method used to define the effect when the object is consumed by an actor.
     *
     * @param actor The actor consuming the object.
     * @param display The display used to print messages.
     * @param map The game map where the consumption occurs.
     */
    void consume(Actor actor, Display display, GameMap map);
}
