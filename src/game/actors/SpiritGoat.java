package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.AttackAction;
import game.actions.Curable;
import game.actions.CureAction;
import game.attributes.Ability;
import game.attributes.NewBaseActorAttributes;
import game.attributes.Status;
import game.behaviours.ProduceOffspringBehaviour;
import game.behaviours.controllers.NPCController;
import game.conditions.Condition;
import game.offspring.OffspringParent;
import game.behaviours.WanderBehaviour;
import game.effects.CrimsonRotEffect;
import game.offspring.producer.Producer;

/**
 * A Class which represents the Spirit Goat.
 *
 * @author hewjiajing
 * @version 3.0
 */
public class SpiritGoat extends Actor implements Curable, OffspringParent
{
    /**
     * A constant which represents the Spirit Goat's name.
     */
    private static final String NAME = "Spirit Goat";

    /**
     * A constant which represents the Spirit Goat's display character.
     */
    private static final char SYMBOL = 'y';

    /**
     * A constant which represents the Spirit Goat's initial health points.
     */
    private static final int HITPOINT = 50;

    /**
     * Condition to produce an Offspring (Actor)
     */
    private Condition reproduceCondition;

    /**
     * Producer to produce an Offspring (Actor)
     */
    private Producer producer;

    /**
     * Controller of SpiritGoat
     */
    private NPCController npcController;

    /**
     * Constant to represent priority of Produce Offspring Behaviour
     */
    private final Integer PRODUCE_OFFSPRING_PRIORITY = 998;

    /**
     * Constant to represent priority of Wander Behaviour
     */
    private final Integer WANDER_BEHAVIOUR_PRIORITY = 999;

    /**
     * A constructor which creates a Spirit Goat.
     *
     * @param reproduceCondition the Condition for SpiritGoat to produce
     *                           offspring
     * @param producer the Producer of what offspring will be produced
     */
    public SpiritGoat(Condition reproduceCondition, Producer producer, NPCController npcController)
    {
        super(NAME, SYMBOL, HITPOINT);
        this.reproduceCondition = reproduceCondition;
        this.producer = producer;
        this.addCapability(Ability.MOVE_ABILITY);
        this.addStatusEffect(new CrimsonRotEffect(this, 10));
        this.npcController = npcController;
        npcController.addBehaviour(new ProduceOffspringBehaviour(this), PRODUCE_OFFSPRING_PRIORITY);
        npcController.addBehaviour(new WanderBehaviour(), WANDER_BEHAVIOUR_PRIORITY);
    }

    /**
     * A method which returns the allowable actions another actor can perform on the Spirit Goat.
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return a list of allowable actions
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map)
    {
        ActionList actions = new ActionList();

        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY))
        {
            actions.add(new AttackAction(this, direction));
        }
        if (otherActor.hasCapability(Ability.CURE_ABILITY))
        {
            actions.add(new CureAction(this, direction, map.locationOf(this)));
        }

        return actions;
    }

    /**
     * A method which resets the countdown timer when the Spirit Goat is cured.
     *
     * @param actor The actor that perform the cure.
     * @param location The location of the object to be cured.
     */
    @Override
    public void cure(Actor actor, Location location)
    {
        resetCountdownTimer();  // connascence of algorithm
    }

    /**
     * A method which resets the countdown timer to its maximum value.
     */
    private void resetCountdownTimer() {
        this.modifyAttribute(NewBaseActorAttributes.TIMER,
                ActorAttributeOperations.UPDATE,
                this.getAttributeMaximum(NewBaseActorAttributes.TIMER));
    }

    /**
     * A method which returns a string representation of the Spirit Goat
     *
     * @return A string that includes Spirit Goat's current status.
     */
    @Override
    public String toString()
    {
        return name + " (HP:" +
                this.getAttribute(BaseActorAttributes.HEALTH) + "/" +
                this.getAttributeMaximum(BaseActorAttributes.HEALTH) +
                ")" + " (Countdown:" +
                this.getAttribute(NewBaseActorAttributes.TIMER) + "/" +
                this.getAttributeMaximum(NewBaseActorAttributes.TIMER) +
                ")" ;
    }

    /**
     * Method to define playTurn of SpiritGoat
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return  Action to be taken from SpiritGoat
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return npcController.performTurn(this, map);
    }

    /**
     * Method to check if SpiritGoat can produce an Offspring
     *
     * @param actor the SpiritGoat
     * @param map the map where SpiritGoat stands
     * @return boolean value of if SpiritGoat can produce offspring
     */
    @Override
    public boolean canProduceOffspring(Actor actor, GameMap map) {
        return reproduceCondition.satisfies(map.locationOf(this));
    }

    /**
     * Method to return the Actor produced by SpiritGoat based on the Producer
     *
     * @param actor the SpiritGoat
     * @param map the map where SpiritGoat stands on
     * @return the Actor produced by SpiritGoat
     */
    @Override
    public Actor produceOffspring(Actor actor, GameMap map) {
        return producer.create();
    }
}