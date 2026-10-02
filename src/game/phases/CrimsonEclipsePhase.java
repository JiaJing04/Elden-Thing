package game.phases;

import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.attributes.Status;
import game.grounds.CrimsonWell;
import game.utilities.Utils;

/**
 * Crimson Eclipse phase where a Crimson Well spawns on cursed ground.
 * @author Ooi Pei Shuen
 * @version 2.0
 */
public class CrimsonEclipsePhase implements TimePhase {

    /**
     * Applies Crimson Eclipse effects by spawning a Crimson Well.
     *
     * @param map current game map
     * @param display display to show messages
     */
    @Override
    public void applyPhaseEffects(GameMap map, Display display) {
        spawnCrimsonWell(map);
    }

    /**
     * Returns Crimson Eclipse phase status.
     *
     * @return phase status
     */
    @Override
    public Status getPhaseName() {
        return Status.CRIMSON_ECLIPSE_PHASE;
    }

    /**
     * Spawns a Crimson Well on a random cursed, walkable and unoccupied tile.
     *
     * @param map game map to find a spawn tile
     */
    private void spawnCrimsonWell(GameMap map) {
        int minX = map.getXRange().min();
        int maxX = map.getXRange().max();
        int minY = map.getYRange().min();
        int maxY = map.getYRange().max();

        while (true) {
            int x = minX + Utils.generateRandomNumber(maxX - minX + 1);
            int y = minY + Utils.generateRandomNumber(maxY - minY + 1);
            Location location = map.at(x, y);

            if (!location.containsAnActor()
                    && location.getGround().canActorEnter(null)
                    && location.getGround().hasCapability(Status.CURSED)) {

                location.setGround(new CrimsonWell(location.getGround()));
                break;
            }
        }
    }
}