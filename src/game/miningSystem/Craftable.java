package game.miningSystem;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;

/**
 * An interface represent a trap is craftable on ground.
 * Ground crafting logic defined within this class.
 *
 * @author Goh Jia Xuan
 * @version 1.0
 */
public interface Craftable {

    /**
     * Retrieve the location to craft.
     * @param actor the actor performing craft action.
     * @param location the location actor is standing
     * @return the location to craft.
     */
    Location locationToCraft(Actor actor, Location location);

    /**
     * Set the ground type to craftable implementer instance.
     * @param location the location to change ground type.
     */
    void setNewGround(Location location);
}
