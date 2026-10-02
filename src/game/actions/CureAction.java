package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Class representing an action to cure.
 *
 * @author hewjiajing
 * @version 1.0
 */
public class CureAction extends Action
{
    /**
     * The object that is to be cured.
     */
    private final Curable curable;

    /**
     * The direction of the object to be cured.
     */
    private final String direction;

    /**
     * The location of the object that is to be cured.
     */
    private final Location location;

    /**
     * Constructor.
     *
     * @param target The object that is to be cured.
     * @param direction The direction of the object to be cured.
     * @param location The location of the object that is to be cured.
     */
    public CureAction(Curable target, String direction, Location location)
    {
        this.curable = target;
        this.direction = direction;
        this.location = location;
    }

    /**
     * This method controls the flow of curing a Cureable
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return the output of CureAction
     */
    @Override
    public String execute(Actor actor, GameMap map)
    {
        // cure the object intended to be cured
        curable.cure(actor, this.location);

        return menuDescription(actor);
    }

    /**
     * This method describes the cure action
     *
     * @param actor The actor performing the cure action.
     * @return the description of cure action
     */
    @Override
    public String menuDescription(Actor actor)
    {
        return actor + " cures " + curable + " at direction " + direction;
    }
}
