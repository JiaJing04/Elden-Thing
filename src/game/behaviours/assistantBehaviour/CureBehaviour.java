package game.behaviours.assistantBehaviour;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * A behavioural class to allow NPC to perform curing behaviour on map.
 * Responsibility: check curing condition, apply curing effect, check actor consciousness, display message on map.
 * Role: an Action subclass and Behaviour implementer
 *
 * @author Goh Jia Xuan
 * @version 1.0
 */
public class CureBehaviour extends Action implements Behaviour {

    /**
     * the farmer assistant to perform this behaviour.
     */
    private final CureAssistant assistant;

    /**
     * Constructor.
     *
     * @param assistant farmer assistant
     */
    public CureBehaviour(CureAssistant assistant) {
        this.assistant = assistant;
    }

    /**
     * The action carried out when CureBehaviour instance is invoked at every game tick.
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return this class if canCure return true otherwise false.
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        return assistant.canCure(map.locationOf(actor)) ? this : null;
    }

    /**
     * Execute cure effect and display appropriate message on console after action.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return message indicate target has perform curing behaviour.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        assistant.cureEffect(map.locationOf(actor));
        if (assistant.actorConsciousness(map.locationOf(actor))){
            return menuDescription(null);
        }
        else {
            return null;
        }
    }

    /**
     * A message display on console to indicate target cure a ground/creature.
     *
     * @param actor The actor performing the action.
     * @return the message
     */
    @Override
    public String menuDescription(Actor actor) {
        return assistant + " perform curing behaviour.";
    }

}
