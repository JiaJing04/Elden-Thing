package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.ConsumeAction;
import game.attributes.Ability;
import game.effects.SideEffect;
import game.actions.Consumable;

/**
 * A consumable item that applies a side effect when used.
 * @author Ooi Pei Shuen
 * @version 1.0
 */
public class Potion extends Item implements Consumable {

    /**
     * The effect applied when this potion is consumed.
     */
    private final SideEffect effect;

    /**
     * Creates a potion with the given name and effect.
     *
     * @param name name of the potion
     * @param effect the side effect to apply when consumed
     */
    public Potion(String name, SideEffect effect) {
        super(name, '%', true);
        this.effect = effect;
    }

    /**
     * Applies the effect and removes this potion from inventory.
     *
     * @param actor the actor consuming the potion
     * @param display the display used to show messages
     * @param map the game map
     */
    @Override
    public void consume(Actor actor, Display display, GameMap map) {
        effect.apply(actor, map);
        actor.removeItemFromInventory(this);
    }

    /**
     * Returns the list of actions available for this item.
     * Adds ConsumeAction if the owner can consume items.
     *
     * @param owner the actor holding this item
     * @param map the current game map
     * @return allowed actions for this item
     */
    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actionList = super.allowableActions(owner, map);

        if (owner.hasCapability(Ability.CONSUME)){
            actionList.add(new ConsumeAction(this));
        }

        return actionList;
    }
}
