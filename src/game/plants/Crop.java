package game.plants;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Interface that represents a crop.
 *
 * @author hewjiajing
 * @version 1.0
 */
public interface Crop
{
    /**
     * Get the stamina cost required to plant the crop.
     *
     * @return the stamina cost
     */
    int getStaminaCost();

    /**
     * Get the time needed for the crop to grow.
     *
     * @return the grow time
     */
    int getGrowTime();

    /**
     * Check if the actor can plant at the current location.
     *
     * @param actor the actor trying to plant
     * @param map the game map
     * @return true if planting is allowed, false otherwise
     */
    boolean getPlantCondition(Actor actor, GameMap map);

    /**
     * Plant the crop.
     *
     * @param actor the actor planting
     * @param map the game map
     */
    void plant(Actor actor, GameMap map);

    /**
     * Grow the crop.
     *
     * @param location the location where the crop grows
     */
    void grow(Location location);

    /**
     * Bloom the crop.
     *
     * @param currentLocation the location of the crop
     */
    void bloom(Location currentLocation);
}
