package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * An action which allows an Actor to consume a Consumable object.
 *
 * @author ooipeishuen
 * @version 1.0
 */
public class ConsumeAction extends Action {

    /**
     * The consumable object that will be consumed.
     */
    private final Consumable consumable;

    /**
     * Constructor to create a ConsumeAction.
     *
     * @param consumable the object to be consumed
     */
    public ConsumeAction(Consumable consumable){
        this.consumable = consumable;
    }

    /**
     * Executes the consumption of the consumable object.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return A string describing the result of the action.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Display display = new Display();
        consumable.consume(actor, display, map);
        return actor + " consumes " + consumable;
    }

    /**
     * A method that returns a short description of consume action in the game menu.
     *
     * @param actor The actor performing the action.
     * @return A string for displaying in the menu.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " consumes " + consumable.toString();
    }
}
