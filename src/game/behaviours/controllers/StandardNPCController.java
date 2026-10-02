package game.behaviours.controllers;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;

import java.util.Map;
import java.util.TreeMap;

/**
 * Class for StandardNPCController
 * implements
 * - NPCController
 *
 *  @author yujiaming
 *  @version 1.0
 */
public class StandardNPCController implements NPCController
{
    /**
     * Array list to store the behaviours of an NPC
     */
    private final Map<Integer, Behaviour> behaviours = new TreeMap<>();

    /**
     * Constructor
     */
    public StandardNPCController()
    {

    }

    /**
     * Method to add behaviour of an NPC to the current controller
     * @param behaviour the behaviour to be added
     * @param priority the priority of the behaviour
     */
    @Override
    public void addBehaviour(Behaviour behaviour, int priority)
    {
        behaviours.put(priority, behaviour);
    }

    /**
     * Method to perform a turn of an NPC based on the controller
     * - in standard form (priority based)
     * @param actor the NPC to perform the turn
     * @param map the map of the NPC currently stands on
     * @return the Action to be performed
     */
    @Override
    public Action performTurn(Actor actor, GameMap map)
    {
        for (Behaviour behaviour : behaviours.values())
        {
            Action action = behaviour.getAction(actor, map);

            if (action != null)
            {
                return action;
            }
        }
        return new DoNothingAction();
    }

    /**
     * Method to create a new Controller for an NPC
     * - mainly used for offspring
     * @return the new Controller created
     */
    @Override
    public NPCController createNew()
    {
        return new StandardNPCController();
    }
}
