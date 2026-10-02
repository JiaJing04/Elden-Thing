package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import game.offspring.ItemParent;

/**
 * A class which represents a behaviour that allows an actor to produce an item
 *
 * @author yujiaming
 * @version 1.0
 */
public class ProduceItemBehaviour extends Action implements Behaviour{

    /**
     * An item producer
     */
    private ItemParent itemProducer;

    /**
     * Constructor
     *
     * @param itemProducer An Item Producer
     */
    public ProduceItemBehaviour(ItemParent itemProducer) {
        this.itemProducer = itemProducer;
    }

    /**
     * A method get the action results from ProduceItemBehaviour
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return this if produce successfully, otherwise null
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        return itemProducer.canProduceItem(actor, map) ? this : null;
    }

    /**
     * This method controls the flow of producing an item
     * Allow Item to produce into surrounding where Actor can enter and no Actor currently stands on
     * This allow Item to be placed in Location that is near and reachable by ItemParent
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return result of producing an item
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Item item = itemProducer.produceItem(actor, map);
        for (Exit exit : map.locationOf(actor).getExits()){
            if (exit.getDestination().canActorEnter(actor) && !exit.getDestination().containsAnActor()){
                exit.getDestination().addItem(item);
                break;
            }
        }

        return itemProducer + "produce " + item;
    }

    /**
     * This method describes the produce item action
     *
     * @param actor The actor performing the action.
     * @return description of the ProduceItem action
     */
    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
