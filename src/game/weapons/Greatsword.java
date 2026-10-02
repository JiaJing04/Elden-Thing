package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.Purchasable;

/**
 * Class representing a weapon item called Greatsword.
 *
 * @author ngziwei
 * @version 1.0
 */
public class Greatsword extends WeaponItem implements Purchasable {
    /**
     * Constructor.
     *
     * @param name        name of the item
     */
    public Greatsword(String name) {
        super(name, 'D', 70, "strikes", 75);
    }

    /**
     * This method shows the effects when the Dragonslayer Greatsword be
     * purchased successfully.
     *
     * @param actor The actor who purchase the Dragonslayer Greatsword
     * @param map The map the actor currently on
     * @return a string showing the effects when the Greatsword be purchased.
     */
    @Override
    public String purchased(Actor actor, GameMap map) {

        String purchasedOutput;

        actor.modifyAttributeMaximum(BaseActorAttributes.HEALTH,
                ActorAttributeOperations.INCREASE, 15);

        actor.addItemToInventory(this);

        purchasedOutput = String.format("Purchasing %s grants 15-point increase " +
                "to %s maximum health", this, actor);

        return purchasedOutput;
    }
}
