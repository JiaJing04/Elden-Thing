package game.behaviours.controllers;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;
import game.utilities.Utils;

import java.util.List;
import java.util.ArrayList;

/**
 * Class of RandomNPCController
 * implements
 * - NPCController
 *
 *  @author yujiaming
 *  @version 1.0
 */
public class RandomNPCController implements NPCController
{
    /**
     * Array list to store the behaviours of an NPC
     */
    private final List<Behaviour> behaviours = new ArrayList<>();

    /**
     * Constructor
     */
    public RandomNPCController()
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
        behaviours.add(behaviour);
    }

    /**
     * Method to perform a turn of an NPC based on the controller
     * - in randomized form
     * @param actor the NPC to perform the turn
     * @param map the map of the NPC currently stands on
     * @return the Action to be performed
     */
    @Override
    public Action performTurn(Actor actor, GameMap map)
    {
        int index = Utils.generateRandomNumber(behaviours.size());

        Behaviour behaviourChosen = behaviours.get(index);

        Action action = behaviourChosen.getAction(actor, map);

        if (action != null)
        {
            return action;
        }
        else
        {
            return new DoNothingAction();
        }
    }

    /**
     * Method to create a new Controller for an NPC
     * - mainly used for offspring
     * @return the new Controller created
     */
    @Override
    public NPCController createNew()
    {
        return new RandomNPCController();
    }
}
