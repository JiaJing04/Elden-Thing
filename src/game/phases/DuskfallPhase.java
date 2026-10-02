package game.phases;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.attributes.Ability;
import game.attributes.Status;
import game.phases.managers.MerchantSleepManager;

/**
 * Duskfall phase where merchants go to sleep.
 * @author Ooi Pei Shuen
 * @version 2.0
 */
public class DuskfallPhase implements TimePhase {

    private final MerchantSleepManager merchantSleepManager;

    /**
     * Constructor that takes a merchant sleep manager.
     *
     * @param merchantSleepManager manager that stores sleeping merchants
     */
    public DuskfallPhase(MerchantSleepManager merchantSleepManager) {
        this.merchantSleepManager = merchantSleepManager;
    }

    /**
     * Applies Duskfall effects by making non-hostile merchants sleep.
     *
     * @param map current game map
     * @param display display to show messages
     */
    @Override
    public void applyPhaseEffects(GameMap map, Display display) {
        putMerchantsToSleep(map, display);
    }

    /**
     * Loops through the map and puts non-hostile merchants to sleep.
     *
     * @param map game map to check
     * @param display display to show sleep messages
     */
    private void putMerchantsToSleep(GameMap map, Display display) {
        for (int x : map.getXRange()) {
            for (int y : map.getYRange()) {
                Location location = map.at(x, y);

                if (location.containsAnActor()) {
                    Actor actor = location.getActor();

                    if (actor.hasCapability(Ability.SELL_ABILITY) && !actor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
                        merchantSleepManager.put(actor, location);
                        map.removeActor(actor);
                        display.println(actor + " goes to sleep");
                    }
                }
            }
        }
    }

    /**
     * Returns Duskfall phase status.
     *
     * @return phase status
     */
    @Override
    public Status getPhaseName() {
        return Status.DUSKFALL_PHASE;
    }
}