package game.behaviours.controllers;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Interface for NPCController
 *
 * @author yujiaming
 * @version 1.0
 */
public interface NPCController {
    /**
     * Method defined to add behaviour into the Controller
     * @param behaviour the behaviour to be added
     * @param priority the priority of the behaviour
     */
    void addBehaviour(Behaviour behaviour, int priority);

    /**
     * Method defined to perform a turn of an NPC
     * @param actor the NPC to perform the turn
     * @param map the map that the NPC is currently stands on
     * @return the Action to be performed on the NPC
     */
    Action performTurn(Actor actor, GameMap map);

    /**
     * Method to create a new Controller
     * @return the new Controller created
     */
    NPCController createNew();
}
