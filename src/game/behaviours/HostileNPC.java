package game.behaviours;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * This interface represent a hostile NPC
 *
 * @author yujiaming
 * @version 1.0
 */
public interface HostileNPC {

    /**
     *This method checks if attack condition for the HostileNPC is satisfied
     *
     * @param gameMap map of hostile NPC currently stands on
     * @return True if condition is satisfied, false otherwise
     */
    boolean satisfyAttackCondition(Actor targetActor, GameMap gameMap);
}
