package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.boss.TreePart;

/**
 * Class representing an action to grow.
 *
 * @author ngziwei
 * @version 1.0
 */
public class GrowAction extends Action {

    private final TreePart growTreePart;

    /**
     * Constructor for GrowAction
     *
     * @param growTreePart the tree part to grow.
     */
    public GrowAction(TreePart growTreePart) {
        this.growTreePart = growTreePart;
    }

    /**
     * This method controls the flow of GrowAction.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return the result of grow action.
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        String result = getGrowTreePart().grow();

        return result;
    }

    /**
     * This method gets the growTreePart
     *
     * @return the growTreePart
     */
    public TreePart getGrowTreePart() {
        return growTreePart;
    }

    /**
     * This method describes the GrowAction
     *
     * @param actor The actor performing the GrowAction
     * @return the description of GrowAction
     */
    @Override
    public String menuDescription(Actor actor) {
        return getGrowTreePart() + " is growing";
    }
}
