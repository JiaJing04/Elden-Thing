package game.miningSystem;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.attributes.Ability;

/**
 * A single class representing all mining materials on the map.
 * Role: an item on map which can be picked up by farmer assistant. i.e. Spriggle.
 * Responsibility: allow owner to craft this material into beneficial ground type, retrieve craftable item.
 */
public class MineItem extends Item {

    /**
     * the ground item crafted by this mine item.
     */
    private Craftable itemCrafted;

    /**
     * Constructor.
     *
     * @param name the name of the MineItem instance
     * @param itemCrafted the item crafted by this item.
     */
    public MineItem(String name, Craftable itemCrafted) {
        super(name, 'P', false);
        this.itemCrafted = itemCrafted;
    }

    /**
     * Retrieve the crafted item. Act as getter method.
     *
     * @return the crafted item.
     */
    public Craftable retrieveItem(){
        return itemCrafted;
    }

    /**
     * The action can be performed on this instance.
     *
     * @param owner the actor that owns the item
     * @param map the map where the actor is performing the action on
     * @return a list of allowable actions.
     */
    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actionList = super.allowableActions(owner, map);

        if (owner.hasCapability(Ability.CRAFT_ABILITY)){
            actionList.add(new CraftAction(this, retrieveItem()));
        }
        return actionList;
    }

}
