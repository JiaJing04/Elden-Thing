package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.Purchasable;

/**
 * Class representing a weapon item called Broadsword.
 *
 * @author ngziwei
 * @version 1.0
 */
public class Broadsword extends WeaponItem implements Purchasable {

    /**
     * Constructor.
     *
     * @param name        name of the Broadsword
     */
    public Broadsword(String name) {
        super(name, 'b', 30, "slash", 50);
    }

    /**
     * This method shows the effects when the Broadsword be purchased
     * successfully.
     *
     * @param actor The actor who purchase the Broadsword
     * @param map The map the actor currently on
     * @return a string showing the effects when the Broadsword be
     *           purchased
     */
    @Override
    public String purchased(Actor actor, GameMap map) {

        String purchasedOutput;

        // Heal the actor who purchases it
        actor.heal(10);

        actor.addItemToInventory(this);

        purchasedOutput = String.format("Purchasing %s heals %s by 10 points",
                this, actor);

        return purchasedOutput;
    }
}
