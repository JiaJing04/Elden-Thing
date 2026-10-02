package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.behaviours.assistantBehaviour.*;
import game.attributes.Ability;
import game.attributes.Status;
import game.behaviours.WanderBehaviour;
import game.behaviours.controllers.NPCController;
import game.conditions.Condition;
import game.conditions.SurroundCapabilitiesCondition;
import game.grounds.Soil;

/**
 * An assistant class represent a NPC which can assist farmer in performing behaviour on map.
 * Role: Farmer assistant with 200 hitpoints, 'Q' as display character on map. A consumer and curing assistant.
 * Capabilities: consume, pick up item, cure cursed ground, and wander around.
 *
 *  <p><b>Reduced Connascence:</b></p>
 *  <ul>
 *     <li><b>Connascence of Name:</b> By implementing {@code Consumer} and {@code CureAssistant} interfaces, Spriggle avoids
 *     hardcoded method names being shared across unrelated classes — behaviours are invoked via interface contracts.</li>
 *     <li><b>Connascence of Type:</b> Other parts of the program interact with Spriggle via its interfaces rather than concrete class,
 *     enabling polymorphism and loose coupling.</li>
 *     <li><b>Connascence of Position:</b> Behaviour priorities are now explicitly declared with constants instead of relying
 *     on positional logic, improving clarity and reducing hidden assumptions.</li>
 *   </ul>
 *
 * @author  Goh Jia Xuan
 * @version 1.0
 */
public class Spriggle extends Actor implements Consumer, CureAssistant {

    /**
     * NPC name representation
     */
    private static final String NAME = "Spriggle";

    /**
     * Spriggle display character representation
     */
    private static final char SYMBOL = 'Q';

    /**
     * Spriggle hit points during instantiation.
     */
    private static final int HITPOINT = 200;

    /**
     * Actor assisting on game map. i.e. the Player
     */
    private Actor owner;

    /**
     * the item plan to consume by Spriggle.
     */
    private Item itemConsume;

    /**
     * Consume behaviour priority level.
     */
    private final int CONSUME_BEHAVIOUR_PRIORITY = 996;

    /**
     * Pick up behaviour priority level.
     */
    private final int PICK_UP_BEHAVIOUR_PRIORITY = 997;

    /**
     * Cure behaviour priority level.
     */
    private final int CURE_BEHAVIOUR_PRIORITY = 995;

    /**
     * wander behaviour priority level.
     */
    private final int WANDER_BEHAVIOUR_PRIORITY = 999;

    /**
     * Controller for Spriggle
     */
    private NPCController npcController;

    /**
     * Constructor.
     * @param owner the farmer to assist with.
     */
    public Spriggle(Actor owner, NPCController npcController) {
        super(NAME, SYMBOL, HITPOINT);
        this.owner = owner;
        addCapability(Ability.MOVE_ABILITY);
        this.npcController = npcController;

        npcController.addBehaviour( new ConsumeBehaviour(this), CONSUME_BEHAVIOUR_PRIORITY);

        npcController.addBehaviour(new PickUpBehaviour(owner, this), PICK_UP_BEHAVIOUR_PRIORITY);

        npcController.addBehaviour(new CureBehaviour(this), CURE_BEHAVIOUR_PRIORITY);

        npcController.addBehaviour(new WanderBehaviour(), WANDER_BEHAVIOUR_PRIORITY);
    }

    /**
     * Select and return an action to perform on the current turn.
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return action need to be carried out.
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return npcController.performTurn(this, map);
    }

    /**
     * Retrieve the first item in inventory that is pet edible.
     * If inventory does not contain such item, return null.
     *
     * @return pet edible item in own inventory
     */
    @Override
    public Item itemToConsume() {
        for (Item item : getItemInventory()) {
            if (item.hasCapability(Status.PET_EDIBLE)) {
                itemConsume = item;
                return item;
            }
        }
        return null;
    }

    /**
     * The consume effect after consuming an item.
     * Spriggle will restore its health to full and the item consume will remove from its inventory.
     */
    @Override
    public void consume() {
        // restore health to full
        this.modifyAttribute(BaseActorAttributes.HEALTH, ActorAttributeOperations.UPDATE, this.getAttributeMaximum(BaseActorAttributes.HEALTH));

        // remove item from inventory
        this.removeItemFromInventory(itemConsume);
    }

    /**
     * Checker for consume condition.
     * If its health below 30% return true, indicate it need to consume item now, otherwise false.
     *
     * @return true if health < 30% otherwise false
     */
    @Override
    public boolean canConsume() {
        // true if health amount below 30%
        //this.modifyAttribute(BaseActorAttributes.HEALTH, ActorAttributeOperations.DECREASE, 5);
        int maxHealth = this.getAttributeMaximum(BaseActorAttributes.HEALTH);
        return this.getAttribute(BaseActorAttributes.HEALTH) < maxHealth * 0.3;
    }

    /**
     * Checker for curing condition.
     * If it's surrounded by cursed ground return true, indicate it can cure it, otherwise false.
     *
     * @param location The current location of Spriggle
     * @return true if it's surrounded by cursed ground otherwise false.
     */
    @Override
    public boolean canCure(Location location) {
        Condition surroundedByCursed = new SurroundCapabilitiesCondition(this, Status.CURSED);
        return surroundedByCursed.satisfies(this, location.map());
    }

    /**
     * The curing effect when cure behaviour is invoked.
     * Convert surrounding cursed ground into Soil ground which is beneficial to farmer.
     * Decrease health by 3 points as curing cost.
     *
     * @param location The current location actor is standing on
     */
    @Override
    public void cureEffect(Location location) {
        for (Exit exit: location.getExits()){
            if (exit.getDestination().getGround().hasCapability(Status.CURSED)){
                exit.getDestination().setGround(new Soil());
            }
        }
        modifyAttribute(BaseActorAttributes.HEALTH, ActorAttributeOperations.DECREASE, 3);
    }

    /**
     * Check for Spriggle consciousness after curing ground.
     *
     * @param location The location Spriggle is currently standing on.
     * @return true if actor still conscious otherwise false.
     */
    @Override
    public boolean actorConsciousness(Location location) {
        if (!this.isConscious())
        {
            Display display = new Display();
            display.println(this.unconscious(location.map()));
            location.map().removeActor(this);
            return false;
        }
        return true;
    }

    /**
     * Customised string to display hit points on console.
     * @return Spriggle name and hit point amount.
     */
    @Override
    public String toString()
    {
        return name + " (HP:" +
                this.getAttribute(BaseActorAttributes.HEALTH) + "/" +
                this.getAttributeMaximum(BaseActorAttributes.HEALTH) +
                ")" ;
    }
}
