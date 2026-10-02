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
import game.effects.AttributeUpdateEffect;
import game.effects.MaxAttributeUpdateEffect;
import game.effects.SideEffect;
import game.monologues.Monologue;
import game.conditions.*;
import game.utilities.Utils;
import game.weapons.Broadsword;
import game.weapons.Greatsword;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing Kale.
 *
 * @author hewjiajing
 * @version 1.0
 */
public class Kale extends Actor implements Listenable
{
    /**
     * A list of monologues that Kale can say.
     */
    private final ArrayList<Monologue> monologueArrayList = new ArrayList<>();

    /**
     * A threshold of runes that Kale holds.
     */
    private final int RUNE_THRESHOLD = 500;

    /**
     * A constant which represents Kale's name.
     */
    private static final String NAME = "Kale";

    /**
     * A constant which represents Kale's display character.
     */
    private static final char SYMBOL = 'k';

    /**
     * A constant which represents Kale's initial health points.
     */
    private static final int HITPOINT = 200;

    /**
     * Controller for Kale
     */
    private NPCController npcController;

    private final Integer WANDER_BEHAVIOUR_PRIORITY = 999;

    /**
     * Constructor.
     */
    public Kale(NPCController npcController)
    {
        super(NAME, SYMBOL, HITPOINT);
        addMonologue();
        addCapability(Ability.MOVE_ABILITY);
        addCapability(Ability.SELL_ABILITY);
        this.npcController = npcController;
        npcController.addBehaviour(new WanderBehaviour(), WANDER_BEHAVIOUR_PRIORITY);
    }

    /**
     * This method returns all purchase options offered by the Kale.
     *
     * @return all purchase options offered by the Kale.
     */
    private ArrayList<PurchaseAction> loadPurchaseActions(){

        ArrayList<PurchaseAction> purchaseActions = new ArrayList<>();

        // purchase side effects involved in the purchase options offered
        SideEffect broadswordEffect1 = new MaxAttributeUpdateEffect(
                ActorAttributeOperations.INCREASE,
                BaseActorAttributes.STAMINA,30);
        SideEffect greatswordEffect1 = new AttributeUpdateEffect(
                ActorAttributeOperations.INCREASE,
                BaseActorAttributes.STAMINA,20);

        purchaseActions.add(new PurchaseAction(this,150,
                new ArrayList<>(List.of(broadswordEffect1)),
                new Broadsword("Broadsword2")));
        purchaseActions.add(new PurchaseAction(this,1700,
                new ArrayList<>(List.of(greatswordEffect1)),
                new Greatsword("Greatsword2")));

        return purchaseActions;
    }

    /**
     * Method to define play Turn of Kale
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return Action to be taken by Kale
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return npcController.performTurn(this, map);
    }

    /**
     * Method to define Action that can be done to Kale by other Actor
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return List of Action that can be done to Kale
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
        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)){
            actionList.add(new AttackAction(this, direction));
        }
        if (otherActor.hasCapability(Ability.LISTEN_ABILITY))
        {
            actionList.add(new ListenAction(this));
        }
        return actionList;
    }

    /**
     * Method to add the default Monologue for Kale
     */
    @Override
    public void addMonologue()
    {
        String less500Runes = "Ah, hard times, I see. Keep your head low and your blade sharp.";
        monologueArrayList.add(new Monologue(this, less500Runes, new WalletCondition(RUNE_THRESHOLD, ComparisonOperator.L)));

        String surroundCurse = "Rest by the flame when you can, friend. These lands will wear you thin.";
        monologueArrayList.add(new Monologue(this, surroundCurse, new SurroundCapabilitiesCondition(this, Status.CURSED)));

        String defaultMonologue = "A merchant’s life is a lonely one. But the roads… they whisper secrets to those who listen.";
        monologueArrayList.add(new Monologue(this, defaultMonologue, new DefaultCondition()));

        String inventoryEmpty = "Not a scrap to your name? Even a farmer should carry a trinket or two.";
        monologueArrayList.add(new Monologue(this, inventoryEmpty, new InventoryCondition(0, ComparisonOperator.E)));
    }

    /**
     * Method to choose Monologue for Kale
     * @param actor The actor interacting with the listenable object.
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
     * Method to return the Monologue list for Kale
     *
     * @return the Monologue ArrayList
     */
    public ArrayList<Monologue> getMonologueArrayList()
    {
        return monologueArrayList;
    }
}
