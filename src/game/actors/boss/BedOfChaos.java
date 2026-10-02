package game.actors.boss;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actions.AttackAction;
import game.attributes.Status;
import game.behaviours.AttackBehaviour;
import game.behaviours.GrowBehaviour;
import game.behaviours.HostileNPC;
import game.behaviours.controllers.NPCController;
import game.utilities.Utils;
import game.weapons.ChaosWeapon;

import java.util.ArrayList;

/**
 * Class representing a BedOfChaos Boss
 *
 * @author ngziwei
 * @version 2.0
 */
public class BedOfChaos extends Actor implements TreePart, HostileNPC {

    /**
     * A constant which represents the BedOfChaos's name.
     */
    private static final String NAME = "Bed of Chaos";

    /**
     * A constant which represents the BedOfChaos's display character.
     */
    private static final char SYMBOL = 'T';

    /**
     * A constant which represents the BedOfChaos's initial health points.
     */
    private static final int HIT_POINT = 1000;

    /**
     * A constant which represents the BedOfChaos itself damage points.
     */
    private static final int DAMAGE_POINT = 25;

    private final ArrayList<TreePart> treeParts = new ArrayList<>();
    private NPCController npcController;
    private static final Integer ATTACK_BEHAVIOUR_PRIORITY = 998;
    private static final Integer GROW_BEHAVIOUR_PRIORITY = 999;

    /**
     * The constructor of the Bed of Chaos
     *
     * @param npcController the NPC controller of the BedOfChaos
     */
    public BedOfChaos(NPCController npcController) {
        super(NAME, SYMBOL, HIT_POINT);

        this.npcController = npcController;
        npcController.addBehaviour(new AttackBehaviour(this), ATTACK_BEHAVIOUR_PRIORITY);
        npcController.addBehaviour(new GrowBehaviour(this), GROW_BEHAVIOUR_PRIORITY);

        addCapability(Status.HOSTILE_TO_ENEMY);

        this.setIntrinsicWeapon(new ChaosWeapon(getDamage()));
    }

    /**
     * Returns a list of allowable actions another actor can perform on this BedOfChaos
     *
     * @param otherActor the Actor that might be performing attack.
     * @param direction  String representing the direction of the other Actor.
     * @param map        current GameMap.
     * @return a list of allowable actions.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {

        ActionList actions = new ActionList();

        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY))
        {
            actions.add(new AttackAction(this, direction));
        }

        return actions;
    }

    /**
     * This method returns the damage point brought by the BedOfChaos
     *
     * @return the damage point brought by the BedOfChaos
     */
    @Override
    public int getDamage() {

        int totalDamage = 0;

        // Accumulates the damage points of the tree parts attached to it
        for (TreePart treePart : getTreeParts()){
            totalDamage += treePart.getDamage();
        }

        totalDamage += DAMAGE_POINT;

        return totalDamage;
    }

    /**
     * This weapon gets the intrinsic weapon of Bed Of Chaos
     *
     * @return the intrinsic weapon of Bed Of Chaos
     */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {

        int accumulatedDamage  = getDamage();

        return new ChaosWeapon(accumulatedDamage);
    }

    /**
     * This method gets the treeParts of the BedOfChaos
     *
     * @return the treeParts of the BedOfChaos
     */
    public ArrayList<TreePart> getTreeParts() {
        return treeParts;
    }

    /**
     * This method shows the growing effect of a BedOfChaos
     *
     * @return the growing effect of a BedOfChaos
     */
    @Override
    public String grow() {

        int randNum = Utils.generateRandomNumber(100);

        TreePart newGrowPart;

        StringBuilder result = new StringBuilder(this + " is growing...\n");

        // 50% to grow a Leaf and Branch respectively
        if (randNum <= 50){
            newGrowPart = new Leaf(this);
        }else{
            newGrowPart = new Branch(this);
        }

        getTreeParts().add(newGrowPart);
        result.append(String.format("it grows a %s...\n",newGrowPart));

        // Each existing tree part grows
        for (TreePart treePart : getTreeParts()){
            result.append(treePart.grow());
        }

        return result.toString();
    }

    /**
     * Method to check if BedOfChaos able to attack a target Actor
     *
     * @param targetActor the Actor to be attacked
     * @param gameMap map of BedOfChaos currently stands on
     * @return boolean value whether BedOfChaos able to attack targetActor
     */
    @Override
    public boolean satisfyAttackCondition(Actor targetActor, GameMap gameMap) {
        return true;
    }

    /**
     * Returns a string representation of the BedOfChaos
     *
     * @return a string that includes BedOfChaos's current status.
     */
    @Override
    public String toString(){
        return name + " (HP:" +
                this.getAttribute(BaseActorAttributes.HEALTH) + "/" +
                this.getAttributeMaximum(BaseActorAttributes.HEALTH) +
                ")";
    }

    /**
     * Select and return an action to perform on the current turn.
     *
     * @param actions    collection of possible Actions for BedOfChaos
     * @param lastAction The Action BedOfChaos took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the BedOfChaos
     * @param display    the I/O object to which messages may be written
     * @return an action to perform on the current turn.
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return npcController.performTurn(this, map);
    }
}
