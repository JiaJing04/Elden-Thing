package game.conditions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

/**
 * A class representing a tick based condition
 *
 * @author yujiaming
 * @version 1.0
 */
public class TickCondition implements Condition{

    /**
     * The tick rate
     */
    private Integer tickRate;

    /**
     * The tick threshold
     */
    private Integer tickThreshold;

    /**
     * The current tick count
     */
    private Integer tickCount;

    /**
     * Constructor
     *
     * @param tickRate The tick rate
     * @param tickThreshold The tick threshold
     */
    public TickCondition(Integer tickRate, Integer tickThreshold) {
        this.tickRate = tickRate;
        this.tickThreshold = tickThreshold;
        this.tickCount = 0;
    }

    /**
     * This method checks if the current tick count fulfills the tick threshold
     *
     * @param actor the Actor involved in condition checking
     * @param gameMap gameMap of the Actor is currently on
     * @return True if the condition is satisfied, false otherwise.
     */
    @Override
    public boolean satisfies(Actor actor, GameMap gameMap) {
        this.tickCount += tickRate;
        return this.tickCount % this.tickThreshold == 0;
    }

    /**
     * This method checks if the current tick count fulfills the tick threshold
     *
     * @param location the location involved in the condition checking
     * @return True if the condition is satisfied, false otherwise.
     */
    @Override
    public boolean satisfies(Location location) {
        this.tickCount += tickRate;
        return this.tickCount % this.tickThreshold == 0;
    }
}
