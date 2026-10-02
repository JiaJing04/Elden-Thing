package game.offspring.producer;

import edu.monash.fit2099.engine.actors.Actor;
import game.conditions.Condition;

/**
 * Interface representing a producer to produce an Actor
 *
 * @author yujiaming
 * @version 1.0
 */
public interface Producer {

    /**
     * This method returns the actor be produced
     *
     * @return the actor be produced
     */
    Actor create();

    /**
     * This method returns the actor that can produce an Item that can
     * hatch into something
     *
     * @param itemCondition the Condition for Item to hatch into something
     * @return the actor with ability to produce an Item
     */
    Actor create(Condition itemCondition);
}
