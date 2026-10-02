package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.AttackAction;
import game.actions.ListenAction;
import game.actions.Listenable;
import game.attributes.Ability;
import game.attributes.Status;
import game.behaviours.AttackBehaviour;
import game.behaviours.HostileNPC;
import game.behaviours.WanderBehaviour;
import game.behaviours.controllers.NPCController;
import game.monologues.Monologue;
import game.conditions.BaseAttributeCondition;
import game.conditions.ComparisonOperator;
import game.conditions.DefaultCondition;
import game.conditions.Condition;
import game.utilities.Utils;
import game.weapons.BareFist;

import java.util.ArrayList;

/**
 * Class representing Guts.
 *
 * @author hewjiajing
 * @version 1.0
 */
public class Guts extends Actor implements Listenable, HostileNPC
{
    /**
     * A list of monologues that Guts can say.
     */
    private final ArrayList<Monologue> monologueArrayList = new ArrayList<>();

    /**
     * A constant which represents Guts's name.
     */
    private static final String NAME = "Guts";

    /**
     * A constant which represents Guts's display character.
     */
    private static final char SYMBOL = 'g';

    /**
     * A constant which represents Guts's initial health points.
     */
    private static final int HITPOINT = 500;

    /**
     * A constant which represents the priority of Wander Behaviour
     */
    private static final Integer WANDER_BEHAVIOUR_PRIORITY = 999;

    /**
     * A constant which represents the priority of Attack Behaviour
     */
    private static final Integer ATTACK_BEHAVIOUR_PRIORITY = 1;

    /**
     * Controller for Guts
     */
    private NPCController npcController;

    /**
     * Constructor.
     */
    public Guts(NPCController npcController)
    {
        super(NAME, SYMBOL, HITPOINT);
        addMonologue();
        setIntrinsicWeapon(new BareFist());
        addCapability(Ability.MOVE_ABILITY);
        addCapability(Status.HOSTILE_TO_ENEMY);
        this.npcController = npcController;

        npcController.addBehaviour(new WanderBehaviour(), WANDER_BEHAVIOUR_PRIORITY);
        npcController.addBehaviour(new AttackBehaviour(this), ATTACK_BEHAVIOUR_PRIORITY);
    }

    /**
     * Method to define logic of play turn for Guts
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return Action to be taken from Guts
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return npcController.performTurn(this, map);
    }

    /**
     * Method to define Action that can be done to Guts by other Actor
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return List of Action that can be done to Guts
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map)
    {
        ActionList actionList = super.allowableActions(otherActor, direction, map);

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
     * Method to add the default Monologue for Guts
     */
    @Override
    public void addMonologue()
    {
        Condition attackCondition = new DefaultCondition();
        String defaultAttack1String = "RAAAAGH!";
        Monologue defaultAttack1 = new Monologue(this, defaultAttack1String, attackCondition);

        String defaultAttack2String = "I’LL CRUSH YOU ALL!";
        Monologue defaultAttack2 = new Monologue(this, defaultAttack2String, attackCondition);

        Condition weakCondition = new BaseAttributeCondition(50, BaseActorAttributes.HEALTH, ComparisonOperator.L);
        String weakString = "WEAK! TOO WEAK TO FIGHT ME!";
        Monologue weakMonologue = new Monologue(this, weakString, weakCondition);

        monologueArrayList.add(defaultAttack1);
        monologueArrayList.add(defaultAttack2);
        monologueArrayList.add(weakMonologue);
    }

    /**
     * Method to choose Monologue for Guts
     *
     * @param actor The actor interacting with the Guts
     * @param gameMap The game map where the interaction happens.
     * @return the Monologue String chosen
     */
    @Override
    public String chooseMonologue(Actor actor, GameMap gameMap)
    {
        ArrayList<Monologue> monologues = new ArrayList<>();

        ArrayList<Monologue> monologueArrayList = getMonologueArrayList();
        // loop through the Monologue ArrayList and check if satisfies
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
     * Method to check if Guts able to attack a target Actor
     *
     * @param targetActor the Actor to attack
     * @param gameMap map of Guts currently stands on
     * @return boolean value whether Guts able to attack targetActor
     */
    @Override
    public boolean satisfyAttackCondition(Actor targetActor, GameMap gameMap)
    {
        // Condition where HP > 50
        Condition attackCondition = new BaseAttributeCondition(50, BaseActorAttributes.HEALTH, ComparisonOperator.G);
        return attackCondition.satisfies(targetActor, gameMap);
    }

    /**
     * Method to return the Monologue list for Guts
     *
     * @return the Monologue ArrayList
     */
    public ArrayList<Monologue> getMonologueArrayList()
    {
        return monologueArrayList;
    }
}
