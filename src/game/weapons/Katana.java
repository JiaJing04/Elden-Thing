package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.Purchasable;

/**
 * Class representing a weapon item called Katana.
 *
 * @author ngziwei
 * @version 1.0
 */
public class Katana extends WeaponItem implements Purchasable {
    /**
     * Constructor.
     *
     * @param name        name of the item
     */
    public Katana(String name) {
        super(name, 'j', 50, "strikes", 60);
    }

    /**
     * This method shows the effects when the Katana be
     * purchased successfully.
     *
     * @param actor The actor who purchase the Katana
     * @param map The map the actor currently on
     * @return a string showing the effects when the Katana be purchased
     */
    @Override
    public String purchased(Actor actor, GameMap map) {

        String purchaseOutput;

        actor.hurt(25);
        purchaseOutput = String.format("Purchasing %s hurts %s by 25 points",
                this, actor);

        // Check if actor still alive after be hurting
        if (!actor.isConscious()){
            purchaseOutput += "\n" + actor.unconscious(map);
        }else {
            actor.addItemToInventory(this);
        }

        return purchaseOutput;
    }
}
