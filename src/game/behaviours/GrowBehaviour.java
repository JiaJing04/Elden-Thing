package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.GrowAction;
import game.actors.boss.TreePart;

/**
 * A class which represents a behaviour that an actor is growing
 *
 * @author ngziwei
 * @version 1.0
 */
public class GrowBehaviour implements Behaviour {

    private final TreePart growingEntity;

    /**
     * Constructor for GrowBehaviour
     *
     * @param growingEntity the growing entity
     */
    public GrowBehaviour(TreePart growingEntity) {
        this.growingEntity = growingEntity;
    }

    /**
     * A method to return action for performing GrowBehaviour
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return the GrowAction to be performed
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        return new GrowAction(growingEntity);
    }
}
