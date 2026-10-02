package game.actors;


import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.AttackAction;
import game.actions.Curable;
import game.actions.CureAction;
import game.attributes.Ability;
import game.attributes.NewBaseActorAttributes;
import game.attributes.Status;
import game.behaviours.controllers.NPCController;
import game.offspring.EggHatchable;
import game.offspring.ItemParent;
import game.behaviours.ProduceItemBehaviour;
import game.behaviours.WanderBehaviour;
import game.effects.CrimsonRotEffect;
import game.effects.MaxAttributeUpdateEffect;
import game.effects.SideEffect;
import game.conditions.Condition;
import game.offspring.producer.EggProducer;
import game.offspring.producer.ItemProducer;
import game.offspring.producer.Producer;
import game.plants.Inheritree;


/**
 * A class which represents the Omen Sheep.
 *
 * @author hewjiajing
 * @version 1.0
 */
public class OmenSheep extends Actor implements Curable, ItemParent, EggHatchable
{
    /**
     * A constant which represents the Omen Sheep's name.
     */
    private static final String NAME = "Omen Sheep";

    /**
     * A constant which represents the Omen Sheep's display character.
     */
    private static final char SYMBOL = 'm';

    /**
     * A constant which represents the Omen Sheep's initial health points.
     */
    private static final int HITPOINT = 75;

    /**
     * Condition to produce Egg
     */
    private Condition reproduceCondition;

    /**
     * The Egg Producer (ItemProducer)
     */
    private ItemProducer itemProducer;

    /**
     * The Offspring Producer (applies for OmenSheep to produce Offspring, NOT lay Egg)
     */
    private Producer producer;

    /**
     * Controller for OmenSheep
     */
    private NPCController npcController;

    /**
     * Constant to represent priority of Produce Item Behaviour
     */
    private final Integer PRODUCE_ITEM_BEHAVIOUR_PRIORITY = 997;

    /**
     * Constant to represent priority of Wander Behaviour
     */
    private final Integer WANDER_BEHAVIOUR_PRIORITY = 999;

    /**
     * Constructor which creates an Omen Sheep to produce an Egg
     *
     * @param reproduceCondition the Condition for OmenSheep to produce Egg
     * @param hatchEggCondition the Condition for Egg to hatch
     * @param producer the Producer of what Egg will hatch into
     */
    public OmenSheep(Condition reproduceCondition, Condition hatchEggCondition, Producer producer, NPCController npcController)
    {
        super(NAME, SYMBOL, HITPOINT);
        this.reproduceCondition = reproduceCondition;
        this.itemProducer = new EggProducer("Omen Egg", this, hatchEggCondition, producer);
        this.addStatusEffect(new CrimsonRotEffect(this, 15));
        addCapability(Ability.MOVE_ABILITY);
        this.npcController = npcController;
        npcController.addBehaviour(new ProduceItemBehaviour(this), PRODUCE_ITEM_BEHAVIOUR_PRIORITY);
        npcController.addBehaviour(new WanderBehaviour(), WANDER_BEHAVIOUR_PRIORITY);
    }

    /**
     * Constructor which creates an Omen Sheep to produce Offspring
     *
     * Note: add ProduceOffspringBehaviour if needed in future requirements
     *
     * @param reproduceCondition condition to produce Offspring
     * @param producer the Offspring Producer
     */
    public OmenSheep(Condition reproduceCondition, Producer producer, NPCController npcController){
        super(NAME, SYMBOL, HITPOINT);
        this.reproduceCondition = reproduceCondition;
        this.producer = producer;
        this.addStatusEffect(new CrimsonRotEffect(this, 15));
        addCapability(Ability.MOVE_ABILITY);
        this.npcController = npcController;
        npcController.addBehaviour(new WanderBehaviour(), WANDER_BEHAVIOUR_PRIORITY);
    }

    /**
     * Returns a list of allowable actions another actor can perform on this
     * OmenSheep
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
     * A method which checks surrounding tiles and grows Inheritree plants
     * around the Omen Sheep when cured.
     *
     * @param actor The actor that perform the cure.
     * @param location The location of the object to be cured.
     */
    @Override
    public void cure(Actor actor, Location location)
    {
        for (Exit exit : location.getExits())
        {
            Location nearbyLocation = exit.getDestination();
            Ground nearbyGround = nearbyLocation.getGround();

            if (nearbyGround != null)
            {
                nearbyLocation.setGround(new Inheritree());
            }
        }
    }

    /**
     * Returns a string representation of the OmenSheep
     *
     * @return A string that includes OmenSheep's current status.
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
                ")";
    }

    /**
     * Method to define playTurn of OmenSheep
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return Action to be taken by OmenSheep
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return npcController.performTurn(this, map);
    }

    /**
     * Method to check if OmenSheep can lay Egg
     *
     * @param actor the OmenSheep
     * @param map the map OmenSheep stands on
     * @return the boolean value whether OmenSheep can lay Egg
     */
    @Override
    public boolean canProduceItem(Actor actor, GameMap map) {
        return reproduceCondition.satisfies(actor, map);
    }

    /**
     * Method to return the Egg produced by OmenSheep
     *
     * @param actor the OmenSheep
     * @param map the map OmenSheep stands on
     * @return the new Egg object
     */
    @Override
    public Item produceItem(Actor actor, GameMap map) {
        return itemProducer.turnIntoItem();
    }

    /**
     * Method to define the effect of Player consuming the OmenEgg
     *
     * @param actor the Actor consuming the OmenEgg
     * @param gameMap the map that Actor currently stands on
     */
    @Override
    public void consumeEgg(Actor actor, GameMap gameMap) {
        SideEffect addMaxHealth = new MaxAttributeUpdateEffect(ActorAttributeOperations.INCREASE, BaseActorAttributes.HEALTH, 10);
        addMaxHealth.apply(actor, gameMap);
    }
}