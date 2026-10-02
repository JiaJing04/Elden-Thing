package game.phases;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.attributes.Status;
import game.phases.managers.MerchantSleepManager;

import java.util.Map;

/**
 * Dawnveil phase where Crimson fades and merchants wake up.
 * @author Ooi Pei Shuen
 * @version 2.0
 */
public class DawnveilPhase implements TimePhase {

    private final MerchantSleepManager merchantSleepManager;

    /**
     * Constructor that takes a merchant sleep manager.
     *
     * @param merchantSleepManager manager that stores sleeping merchants
     */
    public DawnveilPhase(MerchantSleepManager merchantSleepManager) {
        this.merchantSleepManager = merchantSleepManager;
    }

    /**
     * Applies Crimson fade and wakes up merchants.
     *
     * @param map current game map
     * @param display display to show messages
     */
    @Override
    public void applyPhaseEffects(GameMap map, Display display) {
        markCrimsonToEnd(map);
        wakeUpMerchants(display);
    }

    /**
     * Marks all Crimson tiles with CRIMSON_END.
     *
     * @param map game map to check
     */
    private void markCrimsonToEnd(GameMap map) {
        for (int x : map.getXRange()) {
            for (int y : map.getYRange()) {
                Location location = map.at(x, y);
                if (location.getGround().hasCapability(Status.CRIMSON)) {
                    location.getGround().addCapability(Status.CRIMSON_END);
                }
            }
        }
    }

    /**
     * Restores all sleeping merchants and clears the manager.
     *
     * @param display display to show wake-up messages
     */
    private void wakeUpMerchants(Display display) {
        for (Map.Entry<Actor, Location> entry : merchantSleepManager.getAll().entrySet()) {
            entry.getValue().addActor(entry.getKey());
            display.println(entry.getKey() + " wakes up");
        }
        merchantSleepManager.clear();
    }

    /**
     * Returns Dawnveil phase status.
     *
     * @return phase status
     */
    @Override
    public Status getPhaseName() {
        return Status.DAWNVEIL_PHASE;
    }
}