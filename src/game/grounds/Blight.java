package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.Curable;
import game.actions.CureAction;
import game.attributes.Ability;
import game.attributes.Status;

/**
 * A class representing a blight covering the ground of the valley.
 * @author Adrian Kristanto
 * Modified by: hewjiajing
 */
public class Blight extends Ground implements Curable
{
    /**
     * Stamina cost required to cure the blight.
     */
    private final int STAMINA_COST = 50;

    /**
     * Constructor.
     */
    public Blight()
    {
        super('x', "Blight");
        this.addCapability(Status.CURSED);
    }

    /**
     * This method shows the effect when the blight is cured
     *
     * @param actor The actor that perform the cure.
     * @param location The location of the object to be cured.
     */
    @Override
    public void cure(Actor actor, Location location)
    {
        // blight is cured to be soil
        location.setGround(new Soil());

        // consume actor's stamina needed for curing blight
        actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.DECREASE, STAMINA_COST);
    }

    /**
     * This method returns all actions can be performed on the blight
     *
     * @param actor     the Actor acting
     * @param location  the current Location
     * @param direction the direction of the Blight from the Actor
     * @return all actions can be performed on the blight
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction)
    {
        ActionList actions = new ActionList();

        if (actor.hasCapability(Ability.CURE_ABILITY) && actor.getAttribute(BaseActorAttributes.STAMINA) >= STAMINA_COST)
        {
            String displayDirection = direction.isEmpty() ? "here" : direction;

            actions.add(new CureAction(this, displayDirection, location));
        }

        return actions;
    }

}
