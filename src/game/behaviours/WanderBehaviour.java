package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.utilities.Utils;

import java.util.ArrayList;

/**
 * A class that represents a wander behaviour.
 *
 * @author hewjiajing
 * @version 1.0
 */
public class WanderBehaviour implements Behaviour
{

    /**
     * Returns a MoveAction to wander to a random location if possible.
     * Returns null if no movement is possible.
     *
     * @param actor the Actor having the behaviour
     * @param map the map that actor is currently on
     * @return an Action, or null if action is not possible
     */
    @Override
    public Action getAction(Actor actor, GameMap map)
    {
        ArrayList<Action> actions = new ArrayList<>();

        // check surrounding locations where actor can wander into
        for (Exit exit : map.locationOf(actor).getExits())
        {
            Location destination = exit.getDestination();

            if (destination.canActorEnter(actor))
            {
                actions.add(exit.getDestination().getMoveAction(actor, "around", exit.getHotKey()));
            }
        }

        // wander into a random available surrounding
        if (!actions.isEmpty())
        {
            int randomNumber = Utils.generateRandomNumber(actions.size());
            return actions.get(randomNumber);
        }
        else
        {
            return null;
        }
    }
}
