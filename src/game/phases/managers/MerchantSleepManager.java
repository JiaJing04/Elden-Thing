package game.phases.managers;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages sleeping merchants by storing and restoring their locations.
 * @author Ooi Pei Shuen
 * @version 1.0
 */
public class MerchantSleepManager {

    /**
     * Stores the locations of merchants who are currently asleep.
     */
    private final Map<Actor, Location> sleepingMerchants = new HashMap<>();

    /**
     * Puts a merchant to sleep by recording their current location.
     *
     * @param merchant the merchant actor
     * @param location the location where the merchant is sleeping
     */
    public void put(Actor merchant, Location location) {
        sleepingMerchants.put(merchant, location);
    }

    /**
     * Returns a copy of the map containing all sleeping merchants and their locations.
     *
     * @return a map of sleeping merchants and their locations
     */
    public Map<Actor, Location> getAll() {
        return new HashMap<>(sleepingMerchants);
    }

    /**
     * Clears all stored sleeping merchants.
     */
    public void clear() {
        sleepingMerchants.clear();
    }
}