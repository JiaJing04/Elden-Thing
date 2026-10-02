package game.items;

import edu.monash.fit2099.engine.items.Item;
import game.attributes.Ability;

/**
 * A class representing a Talisman that an actor can pick up and drop
 * @author Adrian Kristanto
 * Modified by: hewjiajing
 */
public class Talisman extends Item
{
    /**
     * Constructor.
     */
    public Talisman()
    {
        super("Talisman", 'o', true);
        this.addCapability(Ability.CURE_ABILITY);
    }

}
