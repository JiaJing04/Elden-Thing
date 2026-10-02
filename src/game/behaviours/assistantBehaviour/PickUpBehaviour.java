package game.behaviours.assistantBehaviour;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.attributes.Status;



/**
 * A behavioural class to allow NPC to perform pick up behaviour on map.
 * Responsibility: check surrounding condition, pick up an item, store into inventory, display message.
 * Role: an Action subclass and Behaviour implementer
 *
 * <p><b>Reduced Connascence:</b></p>
 *   <ul>
 *     <li><b>Connascence of Type:</b> Dependencies are typed as {@code Actor}, {@code Location}, and {@code Item} using clear roles (owner, assistant), minimizing misuse and making the method contracts clear.</li>
 *     <li><b>Connascence of Meaning (or Algorithm):</b> The health threshold and capability check (`PET_EDIBLE`) are both encapsulated within a single helper method `addItem()`, reducing duplicated logic and improving cohesion.</li>
 *    <li><b>Connascence of Position:</b> Parameters are clearly named and documented (e.g., `owner`, `assistant`) to avoid misordering or misuse during instantiation.</li>
 *  </ul>
 *
 * @author Goh Jia Xuan
 * @version 1.0
 */
public class PickUpBehaviour extends Action implements Behaviour {

    /**
     * the actor which npc is assisting. i.e. the Player
     */
    private final Actor owner;

    /**
     * the farmer assistant. i.e. Spriggle
     */
    private final Actor assistant;

    /**
     * the actor chosen to store item into their inventory.
     */
    private Actor chosenActor;

    /**
     * the location which contain an item.
     */
    private Location targetLocation;

    /**
     * the item to picked up.
     */
    private Item itemPicked;

    /**
     * Constructor.
     *
     * @param owner the actor which npc is assisting. i.e. the Player
     * @param assistant the farmer assistant. i.e. Spriggle
     */
    public PickUpBehaviour(Actor owner, Actor assistant) {
        this.owner = owner;
        this.assistant = assistant;
        this.chosenActor = null;
    }

    /**
     * The action carried out when PickUpBehaviour instance is invoked at every game tick.
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return this class if there's an item at the surrounding otherwise false.
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location location = map.locationOf(actor);

        if (!location.getItems().isEmpty() && !location.containsAnActor()) {
            targetLocation = location;
            return this;
        }

        for (Exit exit : location.getExits()) {
            Location adjacent = exit.getDestination();
            if (!adjacent.getItems().isEmpty() && !adjacent.containsAnActor()) {
                targetLocation = adjacent;
                return this;
            }
        }
        return null;
    }

    /**
     * a helper method to decide which inventory should the item stored into and store the item.
     * when assistant health < 50%, store in assistant inventory, otherwise store in farmer inventory.
     */
    private void addItem(){
        itemPicked = targetLocation.getItems().get(0);

        if (assistant.getAttribute(BaseActorAttributes.HEALTH) < assistant.getAttributeMaximum(BaseActorAttributes.HEALTH) * 0.5
                && itemPicked.hasCapability(Status.PET_EDIBLE)) {
            assistant.addItemToInventory(itemPicked);
            chosenActor = assistant;
        } else {
            owner.addItemToInventory(itemPicked);
            chosenActor = owner;
        }

        targetLocation.removeItem(itemPicked);

    }

    /**
     * Execute add item to inventory action and display appropriate message on console after action.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return message indicate target has picked up an item and which inventory belongs to.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        addItem();

        StringBuilder result = new StringBuilder();
        result.append("\n");

        String ownerName = (chosenActor == assistant) ? "pet" : "farmer";
        result.append(String.format("%s picked up a %s \n", assistant, itemPicked));
        result.append(String.format("Current items in %s inventory:\n", ownerName));

        for (Item item : chosenActor.getItemInventory()) {
            result.append("- ").append(item.toString()).append("\n");
        }

        return result.toString();
    }

    /**
     * A message display on console to indicate target pick up an item.
     * This method currently unused.
     *
     * @param actor The actor performing the action.
     * @return the message
     */
    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
