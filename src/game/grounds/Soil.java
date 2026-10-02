package game.grounds;

import edu.monash.fit2099.engine.positions.Ground;
import game.attributes.Status;

/**
 * A class representing the soil in the valley
 * @author Adrian Kristanto
 * Modified by: hewjiajing
 */
public class Soil extends Ground
{
    /**
     * Constructor.
     */
    public Soil()
    {
        super('.', "Soil");
        this.addCapability(Status.FREE_TO_PLANT);
    }
}
