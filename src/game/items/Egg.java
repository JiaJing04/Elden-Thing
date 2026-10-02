package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.Consumable;
import game.actions.ConsumeAction;
import game.conditions.Condition;
import game.offspring.EggHatchable;
import game.attributes.Ability;
import game.attributes.Status;
import game.offspring.producer.Producer;

/**
 * A class which represents an abstract Egg item that can hatched into an actor.
 *
 * @author gohjiaxuan
 * @version 3.0
 */
public class Egg extends Item implements Consumable {

    /**
     * Represents the display character for egg
     */
    private static final char SYMBOL = '0';

    /**
     * The parent who lays the Egg
     */
    private EggHatchable parent;

    /**
     * Condition for the Egg to hatch
     */
    private Condition hatchCondition;

    /**
     * The Producer where the Egg will hatch into
     */
    private Producer producer;

    /**
     * Constructor for Egg
     *
     * @param name Name of the Egg
     * @param parent Parent of the Egg
     * @param hatchCondition The Condition for the Egg to hatch
     * @param producer The Producer where the Egg will hatch into
     */
    public Egg(String name, EggHatchable parent, Condition hatchCondition, Producer producer){
        super(name, SYMBOL, true);

        this.parent = parent;
        this.hatchCondition = hatchCondition;
        this.producer = producer;
        this.addCapability(Status.PET_EDIBLE);
    }

    /**
     * List of allowable actions that the egg can perform to its owner
     * or to the current map while being carried by an actor
     * Example #1: a healing item can have a special skill that can increase the current actor's hitpoints.
     *
     * @param owner the actor that owns the item
     * @param map the map where the actor is performing the action on
     * @return an unmodifiable list of Actions
     */
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actionList = super.allowableActions(owner, map);

        if (owner.hasCapability(Ability.CONSUME)){
            actionList.add(new ConsumeAction(this));
        }

        return actionList;
    }

    /**
     * Tick the Egg
     * Check if satisfies Condition to hatch
     *
     * @param currentLocation The location of the ground on which we lie.
     */
    @Override
    public void tick(Location currentLocation) {
        Display display = new Display();

        // if condition met
        if (hatchCondition.satisfies(currentLocation)){
            // if the childToProduce also able to produce an Item
            Actor childToProduce = producer.create(hatchCondition);

            // else it can only produce Offspring
            if (childToProduce == null){
                childToProduce = producer.create();
            }

            // add the child Actor into map
            for (Exit exit :currentLocation.getExits()){
                if (exit.getDestination().canActorEnter(childToProduce) && !exit.getDestination().containsAnActor()){
                    exit.getDestination().addActor(childToProduce);
                    break;
                }
            }

            // remove the Egg
            currentLocation.removeItem(this);

            display.println(this + "hatched into " + childToProduce);
        }
    }

    /**
     * Method to define logic of consuming an Egg
     *
     * @param actor The actor consuming the object.
     * @param display The display used to print messages.
     * @param map The game map where the consumption occurs.
     */
    @Override
    public void consume(Actor actor, Display display, GameMap map) {
        parent.consumeEgg(actor, map);
        actor.removeItemFromInventory(this);
    }
}