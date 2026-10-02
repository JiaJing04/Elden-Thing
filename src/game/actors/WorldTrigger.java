package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.attributes.Status;
import game.effects.PhaseTickEffect;
import game.phases.managers.TimePhaseManager;

/**
 * World Trigger is a passive actor used to apply global effects each turn.
 * @author Ooi Pei Shuen
 * @version 1.0
 */
public class WorldTrigger extends Actor
{
    /**
     * Creates a World Trigger. Adds PhaseTickEffect and cannot be attacked.
     *
     * @param manager the manager that controls world-level behaviour
     */
    public WorldTrigger(TimePhaseManager manager)
    {
        super("WorldTrigger", '-', Integer.MAX_VALUE);
        addCapability(Status.CANNOT_BE_ATTACKED);
        this.addStatusEffect(new PhaseTickEffect(manager));
    }

    /**
     * Returns DoNothingAction. World Trigger does not perform actions.
     *
     * @param actions available actions
     * @param lastAction last action taken
     * @param map current game map
     * @param display display for messages
     * @return DoNothingAction
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display)
    {
        return new DoNothingAction();
    }

    /**
     * Returns the display name of this actor.
     *
     * @return "World Trigger"
     */
    @Override
    public String toString()
    {
        return "World Trigger";
    }
}