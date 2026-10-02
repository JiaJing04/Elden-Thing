package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.AttackAction;

/**
 * A class which represents a behaviour that allows an actor to attack other actors
 *
 * @author yujiaming
 * @version 1.0
 */
public class AttackBehaviour implements Behaviour {

    /**
     * NPC that is HOSTILE_TO_ENEMY
     */
    private HostileNPC hostileNPC;

    /**
     * Constructor
     *
     * @param hostileNPC NPC that is HOSTILE_TO_ENEMY
     */
    public AttackBehaviour(HostileNPC hostileNPC){
        this.hostileNPC = hostileNPC;
    }

    /**
     * A method which determines the AttackAction to be performed
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return the AttackAction to be performed
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        for (Exit exit : map.locationOf(actor).getExits()){
            if (exit.getDestination().containsAnActor()){
                Actor target = exit.getDestination().getActor();

                if (target.hasCapability(game.attributes.Status.CANNOT_BE_ATTACKED)) {
                    continue;
                }

                if (hostileNPC.satisfyAttackCondition(target, map)){
                    return new AttackAction(target, exit.getName());
                }
            }
        }
        return null;
    }
}
