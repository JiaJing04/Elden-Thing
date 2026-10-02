package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.AttackAction;
import game.actions.ListenAction;
import game.actions.Listenable;
import game.actions.PurchaseAction;
import game.attributes.Ability;
import game.attributes.Status;
import game.behaviours.WanderBehaviour;
import game.behaviours.controllers.NPCController;
import game.behaviours.controllers.StandardNPCController;
import game.conditions.SurroundCapabilitiesCondition;
import game.conditions.TickCondition;
import game.effects.AttributeUpdateEffect;
import game.effects.MaxAttributeUpdateEffect;
import game.effects.SideEffect;
import game.effects.SpawnEffect;
import game.monologues.Monologue;
import game.conditions.DefaultCondition;
import game.offspring.producer.GoldenBeetleProducer;
import game.offspring.producer.OmenSheepProducer;
import game.utilities.Utils;
import game.weapons.Broadsword;
import game.weapons.Greatsword;
import game.weapons.Katana;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing Sellen.
 *
 * @author hewjiajing
 * @version 1.0
 */
public class Sellen extends Actor implements Listenable
{
    /**
     * A list of monologues that Sellen can say.
     */
    private final ArrayList<Monologue> monologueArrayList = new ArrayList<>();

    /**
     * A constant which represents Sellen's name.
     */
    private static final String NAME = "Sellen";

    /**
     * A constant which represents Sellen's display character.
     */
    private static final char SYMBOL = 's';

    /**
     * A constant which represents Sellen's initial health points.
     */
    private static final int HITPOINT = 150;

    /**
     * Constant to represent priority of Wander Behaviour
     */
    private final Integer WANDER_BEHAVIOUR_PRIORITY = 999;

    /**
     * Controller for Sellen
     */
    NPCController npcController;

    /**
     * Constructor.
     */
    public Sellen(NPCController npcController)
    {
        super(NAME, SYMBOL, HITPOINT);
        addMonologue();
        addCapability(Ability.MOVE_ABILITY);
        addCapability(Ability.SELL_ABILITY);
        this.npcController = npcController;
        npcController.addBehaviour(new WanderBehaviour(), WANDER_BEHAVIOUR_PRIORITY);
    }


    /**
     * This method returns all purchase options offered by the Sellen.
     *
     * @return all purchase options offered by the Sellen.
     */
    private ArrayList<PurchaseAction> loadPurchaseActions(){

        ArrayList<PurchaseAction> purchaseActions = new ArrayList<>();

        // purchase side effects involved in the purchase options offered
        SideEffect broadswordEffect1 = new MaxAttributeUpdateEffect(
                ActorAttributeOperations.INCREASE,
                BaseActorAttributes.HEALTH,20);
        SideEffect greatswordEffect1 = new SpawnEffect(new GoldenBeetleProducer(new TickCondition(1, 5), npcController.createNew()),
                            new SurroundCapabilitiesCondition(Status.CURSED));
        SideEffect katanaEffect1 = new MaxAttributeUpdateEffect(
                ActorAttributeOperations.INCREASE,
                BaseActorAttributes.STAMINA,20);
        SideEffect katanaEffect2 = new SpawnEffect(new OmenSheepProducer(new TickCondition(1, 7), npcController.createNew()),
                new TickCondition(1, 3),this);
        SideEffect katanaEffect3 = new AttributeUpdateEffect(
                ActorAttributeOperations.INCREASE,
                BaseActorAttributes.HEALTH,10
        );

        purchaseActions.add(new PurchaseAction(this,100,
                new ArrayList<>(List.of(broadswordEffect1)),
                new Broadsword("Broadsword1")));
        purchaseActions.add(new PurchaseAction(this,1500,
                new ArrayList<>(List.of(greatswordEffect1)),
                new Greatsword("Greatsword1")));
        purchaseActions.add(new PurchaseAction(this,500,
                new ArrayList<>(List.of(katanaEffect1,katanaEffect2,katanaEffect3)),
                new Katana("Katana1")));

        return purchaseActions;
    }

    /**
     * Method to define playTurn of Sellen
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return Action to be taken by Sellen
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return npcController.performTurn(this, map);
    }

    /**
     * Method to define Action that can be done to Sellen by other Actor
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return List of Action that can be done to Sellen
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map)
    {
        ActionList actionList = super.allowableActions(otherActor, direction, map);

        if (otherActor.hasCapability(Ability.PURCHASE_ABILITY)){
            for (PurchaseAction purchaseAction : loadPurchaseActions()){
                actionList.add(purchaseAction);
            }
        }

        if (otherActor.hasCapability(Ability.LISTEN_ABILITY))
        {
            actionList.add(new ListenAction(this));
        }
        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)){
            actionList.add(new AttackAction(this, direction));
        }

        return actionList;
    }

    /**
     * Method to add the default Monologue for Sellen
     */
    @Override
    public void addMonologue()
    {
        String mono1 = "The academy casts out those it fears. Yet knowledge, like the stars, cannot be bound forever.";
        monologueArrayList.add(new Monologue(this, mono1, new DefaultCondition()));

        String mono2 = "You sense it too, don’t you? The Glintstone hums, even now.";
        monologueArrayList.add(new Monologue(this, mono2, new DefaultCondition()));
    }

    /**
     * Method to choose Monologue for Sellen
     *
     * @param actor The actor interacting with the Sellen
     * @param gameMap The game map where the interaction happens.
     * @return the Monologue String chosen
     */
    @Override
    public String chooseMonologue(Actor actor, GameMap gameMap)
    {
        ArrayList<Monologue> monologues = new ArrayList<>();

        ArrayList<Monologue> monologueArrayList = getMonologueArrayList();

        for (Monologue monologue : monologueArrayList)
        {
            if (monologue.satisfyCondition(actor, gameMap))
            {
                monologues.add(monologue);
            }
        }

        int index = Utils.generateRandomNumber(monologues.size());

        Monologue monologueSelected = monologues.get(index);

        String monologueStr = monologueSelected.toString();

        return monologueStr;
    }

    /**
     * Method to return the Monologue list for Sellen
     *
     * @return the Monologue ArrayList
     */
    public ArrayList<Monologue> getMonologueArrayList()
    {
        return monologueArrayList;
    }
}
