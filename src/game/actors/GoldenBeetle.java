package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.*;
import game.attributes.Ability;
import game.attributes.Status;
import game.behaviours.*;
import game.behaviours.controllers.NPCController;
import game.effects.AttributeUpdateEffect;
import game.effects.SideEffect;
import game.conditions.Condition;
import game.offspring.EggHatchable;
import game.offspring.ItemParent;
import game.offspring.producer.EggProducer;
import game.offspring.producer.ItemProducer;
import game.offspring.producer.Producer;

/**
 * A class which represents the Golden Beetle creature in the game.
 *
 * @author ooipeishuen
 * @version 1.0
 */
public class GoldenBeetle extends Actor implements ItemParent, EggHatchable, Consumable {
    /**
     * A constant which represents the Golden Beetle's name.
     */
    private static final String NAME = "Golden Beetle";

    /**
     * A constant which represents the Golden Beetle's display character.
     */
    private static final char SYMBOL = 'b';

    /**
     * A constant which represents the Golden Beetle's initial health points.
     */
    private static final int HITPOINT = 25;

    /**
     * Condition of GoldenBeetle to produce an Offspring
     */
    private Condition reproduceCondition;

    /**
     * Producer to produce an Item for GoldenBeetle
     */
    private ItemProducer itemProducer;

    /**
     * Produce to produce an Offspring (Actor)
     */
    private Producer producer;

    /**
     * The Actor to be followed
     */
    private Actor followTarget;

    /**
     * The Controller for GoldenBeetle
     */
    private NPCController npcController;

    /**
     * Constant to represent priority of Wander Behaviour
     */
    private final Integer WANDER_BEHAVIOUR_PRIORITY = 999;

    /**
     * Constant to represent priority of Produce Item Behaviour
     */
    private final Integer PRODUCE_ITEM_BEHAVIOUR_PRIORITY = 997;

    /**
     * Constant to represent priority of Follow Behaviour
     */
    private final Integer FOLLOW_BEHAVIOUR_PRIORITY = 998;

    /**
     * Constructor for GoldenBeetle to produce Item (Egg)
     *
     * @param reproduceCondition Condition to reproduce (produce Item(Egg))
     * @param hatchEggCondition Condition for Item(Egg) to hatch into the Producer
     * @param producer the Producer where the Item (Egg) will hatch into
     */
    public GoldenBeetle(Condition reproduceCondition, Condition hatchEggCondition, Producer producer, NPCController npcController)
    {
        super(NAME, SYMBOL, HITPOINT);
        addCapability(Ability.MOVE_ABILITY);
        this.reproduceCondition = reproduceCondition;
        this.itemProducer = new EggProducer("Golden Egg", this, hatchEggCondition, producer);
        this.npcController = npcController;
        npcController.addBehaviour(new ProduceItemBehaviour(this), PRODUCE_ITEM_BEHAVIOUR_PRIORITY);
        npcController.addBehaviour(new WanderBehaviour(), WANDER_BEHAVIOUR_PRIORITY);
    }

    /**
     * Constructor for GoldenBeetle to produce Offspring
     *
     * @param reproduceCondition Condition to produce Offspring
     * @param producer the Producer where the Offspring will turn into
     */
    public GoldenBeetle(Condition reproduceCondition, Producer producer, NPCController npcController)
    {
        super(NAME, SYMBOL, HITPOINT);
        addCapability(Ability.MOVE_ABILITY);
        this.reproduceCondition = reproduceCondition;
        this.producer = producer;
        this.npcController = npcController;
        npcController.addBehaviour(new WanderBehaviour(), WANDER_BEHAVIOUR_PRIORITY);
    }


    /**
     * Returns a list of allowable actions another actor can perform on this Golden Beetle.
     *
     * @param otherActor the Actor that might be performing attack.
     * @param direction  String representing the direction of the other Actor.
     * @param map        current GameMap.
     * @return a list of allowable actions.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map)
    {
        ActionList actions = new ActionList();

        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY))
        {
            actions.add(new AttackAction(this, direction));
        }

        if (otherActor.hasCapability(Ability.CONSUME)){
            actions.add(new ConsumeAction(this));
        }

        if (otherActor.hasCapability(Ability.CAN_BE_FOLLOWED)) {
            followTarget = otherActor;
            npcController.addBehaviour(new FollowBehaviour(followTarget), FOLLOW_BEHAVIOUR_PRIORITY);
        }

        return actions;
    }


    /**
     * Allows an actor to consume the beetle directly from the map.
     *
     * @param actor The actor consuming the object.
     * @param display The display used to print messages.
     * @param map The game map where the consumption occurs.
     */
    @Override
    public void consume(Actor actor, Display display, GameMap map) {
        // heal farmer by 15 points
        actor.heal(15);

        actor.addBalance(1000);

        // remove beetle from game
        map.removeActor(this);
    }

    /**
     * Returns a string representation of the Golden Beetle
     *
     * @return a string that includes beetle's current status.
     */
    @Override
    public String toString()
    {
        return name + " (HP:" +
                this.getAttribute(BaseActorAttributes.HEALTH) + "/" +
                this.getAttributeMaximum(BaseActorAttributes.HEALTH) +
                ")";
    }

    /**
     * Method to define playTurn method of GoldenBeetle
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return Action to be taken by GoldenBeetle
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return npcController.performTurn(this, map);
    }

    /**
     * Method to determine consumption effect of Egg for Egg lays by GoldenBeetle
     *
     * @param actor the Actor consuming the Egg
     * @param gameMap the map where Actor stands on
     */
    @Override
    public void consumeEgg(Actor actor, GameMap gameMap) {
        SideEffect restoreStamina = new AttributeUpdateEffect(ActorAttributeOperations.INCREASE, BaseActorAttributes.STAMINA, 20);

        restoreStamina.apply(actor, gameMap);
    }

    /**
     * Method to check if GoldenBeetle can produce an Item (Egg)
     *
     * @param actor the GoldenBeetle
     * @param map the map where GoldenBeetle stands on
     * @return boolean value whether GoldenBeetle can produce an Item (Egg)
     */
    @Override
    public boolean canProduceItem(Actor actor, GameMap map) {
        return reproduceCondition.satisfies(actor, map);
    }

    /**
     * Method to produce the Item produced by GoldenBeetle
     *
     * @param actor GoldenBeetle
     * @param map map where GoldenBeetle stands on
     * @return the Item produced (Egg)
     */
    @Override
    public Item produceItem(Actor actor, GameMap map) {
        return itemProducer.turnIntoItem();
    }
}
