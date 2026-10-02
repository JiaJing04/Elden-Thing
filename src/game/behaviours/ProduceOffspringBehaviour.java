package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import game.offspring.OffspringParent;

/**
 * A class which represents a behaviour that allows an actor to produce an
 * offspring
 *
 * @author yujiaming
 * @version 1.0
 */
public class ProduceOffspringBehaviour extends Action implements Behaviour {

    /**
     * An offspring parent
     */
    private OffspringParent offspringParent;

    /**
     * Constructor
     *
     * @param offspringParent An offspring parent
     */
    public ProduceOffspringBehaviour(OffspringParent offspringParent) {
        this.offspringParent = offspringParent;
    }

    /**
     * This method get the action results from ProduceOffspringBehaviour
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return the action results from ProduceOffspringBehaviour
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        return offspringParent.canProduceOffspring(actor, map) ? this : null;
    }

    /**
     * This method controls the flow of producing an offspring
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return the output of producing an offspring
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Actor childToProduce = offspringParent.produceOffspring(actor, map);
        for (Exit exit : map.locationOf(actor).getExits()){
            if (exit.getDestination().canActorEnter(actor) && !exit.getDestination().containsAnActor()){
                exit.getDestination().addActor(childToProduce);
                break;
            }
        }
        return offspringParent + " produce " + childToProduce;
    }

    /**
     * This method describes the produce offspring action
     *
     * @param actor The actor performing the action.
     * @return the description of producing off spring
     */
    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
