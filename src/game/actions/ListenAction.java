package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Class representing an action to listen.
 *
 * @author hewjiajing
 * @version 1.0
 */
public class ListenAction extends Action
{
    /**
     * The Listenable that is to be listened.
     */
    private final Listenable listenable;

    /**
     * Constructor for ListenAction.
     *
     * @param listenable The object that can be listened to.
     */
    public ListenAction(Listenable listenable)
    {
        this.listenable = listenable;
    }

    /**
     * Executes the listen action.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return the output of listen action
     */
    @Override
    public String execute(Actor actor, GameMap map)
    {
        String monologueChosen = listenable.chooseMonologue(actor, map);

        return monologueChosen;
    }

    /**
     * This method describes the listen action
     *
     * @param actor The actor performing the action.
     * @return the description of the purchase action
     */
    @Override
    public String menuDescription(Actor actor)
    {
        return actor + " listen to " + listenable;
    }
}
