package game.effects;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.StatusEffect;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Location;
import game.attributes.NewBaseActorAttributes;

/**
 * A class represents a status effect due to Crimson Rot that effects on an actor.
 * Reduces the actor's timer each turn until the actor becomes unconscious.
 *
 * @author hewjiajing
 * @version 1.0
 */
public class CrimsonRotEffect extends StatusEffect
{
    /**
     * Constructor.
     *
     * @param actor the actor affected by the rot
     * @param timer the countdown timer value
     */
    public CrimsonRotEffect(Actor actor, int timer)
    {
        super("Rot Effect");
        actor.addAttribute(NewBaseActorAttributes.TIMER, new BaseActorAttribute(timer));
    }

    /**
     * Decreases the countdown timer each turn.
     * Removes the effect if the actor is resistant to rot.
     *
     * @param location the current location of the actor
     * @param target the actor affected by the rot
     */
    @Override
    public void tick(Location location, Actor target)
    {
        // target being rot
        target.modifyAttribute(NewBaseActorAttributes.TIMER, ActorAttributeOperations.DECREASE, 1);

        // check if target is still alive or died
        if (target.getAttribute(NewBaseActorAttributes.TIMER) <= 0)
        {
            Display display = new Display();
            display.println(target.unconscious(location.map()));
        }
    }

}
