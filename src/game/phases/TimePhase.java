package game.phases;

import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.attributes.Status;

/**
 * Interface for world phases. Each phase defines its own effects and name.
 * @author Ooi Pei Shuen
 * @version 1.0
 */
public interface TimePhase {

    /**
     * Applies effects for this phase.
     *
     * @param map the game map
     * @param display the display to show messages
     */
    void applyPhaseEffects(GameMap map, Display display);

    /**
     * Returns the name of this phase.
     *
     * @return the phase name
     */
    Status getPhaseName();
}
