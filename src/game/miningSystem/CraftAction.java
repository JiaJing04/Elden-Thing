package game.miningSystem;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

/**
 * The action class represent the player carry out crafting action.
 * Role: Action subclass
 * Responsibility: execute craft action, show appropriate menu description.
 *
 * @author Goh Jia Xuan
 * @version 1.0
 */
public class CraftAction extends Action {

    /**
     * The mining material crafted the craftable ground item.
     */
    private MineItem item;

    /**
     * the item want to be crafted on ground.
     */
    private Craftable itemCrafted;

    /**
     * Constructor.
     *
     * @param item The mining material crafted the craftable ground item.
     * @param itemCrafted the item want to be crafted on ground.
     */
    public CraftAction(MineItem item, Craftable itemCrafted) {
        this.item = item;
        this.itemCrafted = itemCrafted;
    }

    /**
     * Execute crafting logic and display appropriate message on console after action.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return message indicate resulting action performed (successful or unsuccessful).
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Location location = map.locationOf(actor);

        Location locationToBuild = itemCrafted.locationToCraft(actor, location);
        if (locationToBuild != null){
            actor.removeItemFromInventory(item);
            itemCrafted.setNewGround(locationToBuild);
            return String.format("Farmer successfully built a %s at %s!", itemCrafted, locationToBuild);
        }
        return String.format("No valid spot nearby to build a %s!", itemCrafted);
    }

    /**
     * The craft option display on menu for Player.
     *
     * @param actor The actor performing the action.
     * @return the craft option.
     */
    @Override
    public String menuDescription(Actor actor) {
        return String.format("%s build a %s here", actor, itemCrafted);
    }
}
