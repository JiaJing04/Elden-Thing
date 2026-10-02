package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.Weapon;

/**
 * Class representing an action to attack.
 *
 * @author hewjiajing
 * @version 1.0
 */
public class AttackAction extends Action
{
    /**
     * The Actor that is to be attacked.
     */
    private Actor target;

    /**
     * The direction of the attack.
     */
    private String direction;

    /**
     * The weapon used for the attack.
     */
    private Weapon weapon;

    /**
     * Constructor without a default intrinsic weapon.
     *
     * @param target the Actor to attack
     * @param direction the direction where the attack should be performed
     */
    public AttackAction(Actor target, String direction, Weapon weapon)
    {
        this.target = target;
        this.direction = direction;
        this.weapon = weapon;
    }

    /**
     * Constructor with a default intrinsic weapon.
     *
     * @param target the actor to attack
     * @param direction the direction where the attack should be performed
     */
    public AttackAction(Actor target, String direction)
    {
        this.target = target;
        this.direction = direction;
    }

    /**
     * Executes the attack action.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return the output of listen action
     */
    @Override
    public String execute(Actor actor, GameMap map)
    {
        // get intrinsic weapon if no weapon is specified to be used
        if (weapon == null)
        {
            weapon = actor.getIntrinsicWeapon();
        }

        String result = weapon.attack(actor, target, map);

        // check target is still alive or died from attack
        if (!target.isConscious())
        {
            result += "\n" + target.unconscious(actor, map);
        }

        return result;
    }

    /**
     * This method describes the attack action
     *
     * @param actor The actor performing the action.
     * @return the description of the purchase action
     */
    @Override
    public String menuDescription(Actor actor)
    {
        return actor + " attacks " + target + " at " + direction + " with " + (weapon != null ? weapon : "Intrinsic Weapon");
    }
}
