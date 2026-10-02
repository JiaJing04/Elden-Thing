package game.behaviours.assistantBehaviour;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * A behavioural class to allow NPC to perform consuming behaviour on map.
 * Responsibility: check consuming condition, get item to consume, apply consuming effect, and display appropriate
 * message when action is carried out.
 * Role: an Action subclass and Behaviour implementer
 *
 * @author Goh Jia Xuan
 * @version 1.0
 */
public class ConsumeBehaviour extends Action implements Behaviour {

    /**
     * the consumer which have the capability to consume an item.
     */
    private Consumer target;

    /**
     * the item to consume by consumer.
     */
    private Item itemConsume;

    /**
     * Constructor.
     *
     * @param target the consumer target to carry out this behaviour
     */
    public ConsumeBehaviour(Consumer target) {
        this.target = target;
    }

    /**
     * The action carried out when ConsumeBehaviour instance is invoked at every game tick.
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return this class if canConsume return true and itemToConsume doesn't return null, otherwise false.
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        if (target.canConsume() && target.itemToConsume() != null){
            itemConsume = target.itemToConsume();
            return this;
        }
        return null;
    }

    /**
     * Execute consume effect and display appropriate message on console after action.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return message indicate target has consumed an item.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        target.consume();
        return menuDescription(null);
    }

    /**
     * A message display on console to indicate target consume an item.
     *
     * @param actor The actor performing the action.
     * @return the message
     */
    @Override
    public String menuDescription(Actor actor) {
        return target + "consume " + itemConsume;
    }
}
