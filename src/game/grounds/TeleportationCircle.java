package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.TeleportAction;
import game.attributes.Ability;

import java.util.List;

/**
 * Class representing a gate where teleport is available.
 *
 * @author hewjiajing
 * @version 1.0
 */
public class TeleportationCircle extends Ground
{
    /**
     * The destinations that is available to be teleport to.
     */
    private List<Location> destinations;

    /**
     * A constant which represents Teleportation Circle's display character.
     */
    private static final char SYMBOL = 'A';

    /**
     * A constant which represents Teleportation Circle's name.
     */
    private static final String NAME = "Teleportation Circle";

    /**
     * Constructor.
     *
     * @param destinations destinations that is available to be teleport to.
     */
    public TeleportationCircle(List<Location> destinations)
    {
        super(SYMBOL, NAME);
        this.destinations = destinations;
    }

    /**
     * This method returns all actions can be performed on the teleportation circle.
     *
     * @param actor     the Actor acting
     * @param location  the current Location
     * @param direction the direction of the Ground from the Actor
     * @return all actions can be performed on the teleportation circle
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction)
    {
        ActionList actions = new ActionList();

        if (actor.hasCapability(Ability.TELEPORT_ABILITY))
        {
            for (Location destination : destinations)
            {
                if (destination != location)
                {
                    actions.add(new TeleportAction(destination));
                }
            }
        }

        return actions;
    }
}
