package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Class representing an action to attack.
 *
 * @author hewjiajing
 * @version 1.0
 */
public class TeleportAction extends Action
{
    /**
     * The destination that is to be teleport to.
     */
    private Location teleportDestination;

    /**
     * Constructor.
     *
     * @param traverseDestination destination that is to be teleport to
     */
    public TeleportAction(Location traverseDestination)
    {
        this.teleportDestination = traverseDestination;
    }

    /**
     * Executes the teleport action.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return the output of teleport action
     */
    @Override
    public String execute(Actor actor, GameMap map)
    {
        if (teleportDestination.containsAnActor())
        {
            return "The gate is blocked by someone";
        }
        else
        {
            map.moveActor(actor, teleportDestination);
            String result = "You have teleported from " + map.toString() + " to " + teleportDestination.map().toString();
            return result;
        }
    }

    /**
     * This method describes the teleport action.
     *
     * @param actor The actor performing the action.
     * @return the description of the teleport action
     */
    @Override
    public String menuDescription(Actor actor)
    {
        return actor + " enters the gate to " + teleportDestination.map().toString();
    };
}
