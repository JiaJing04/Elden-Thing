package game.monologues;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.Listenable;
import game.conditions.Condition;

/**
 * This class represents a monologue
 *
 * @author yujiaming
 * @version 1.0
 */
public class Monologue {

    /**
     * The entity be listened to
     */
    private final Listenable listenable;

    /**
     * The monologue message
     */
    private final String monologueMessage;

    /**
     * The condition of the monologue be added to monologue pool
     */
    private final Condition monologueCondition;

    /**
     * Constructor
     *
     * @param listenable The entity be listened to
     * @param monologueMessage The monologue message
     * @param monologueCondition The condition of the monologue be added to
     *                           monologue pool
     */
    public Monologue(Listenable listenable, String monologueMessage, Condition monologueCondition){
        this.listenable = listenable;
        this.monologueMessage = monologueMessage;
        this.monologueCondition = monologueCondition;
    }

    /**
     * Checks if the condition of this monologue to be added to the monologue
     * pool is satisfied
     *
     * @param actor The actor who listen the listenable's monologue
     * @param gameMap The map the actor is on
     * @return True if condition satisfied, false otherwise
     */
    public boolean satisfyCondition(Actor actor, GameMap gameMap){
        return monologueCondition.satisfies(actor, gameMap);
    }

    /**
     * The description of this monologue
     *
     * @return the description of this monologue
     */
    @Override
    public String toString() {
        return listenable + ": " + monologueMessage;
    }
}
