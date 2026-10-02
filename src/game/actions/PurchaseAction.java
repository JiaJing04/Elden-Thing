package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.effects.SideEffect;

import java.util.ArrayList;

/**
 * Class representing an action to purchase item.
 *
 * @author ngziwei
 * @version 1.0
 */
public class PurchaseAction extends Action {

    /**
     * The merchant sells the purchasable entity
     */
    private final Actor merchant;

    /**
     * The price of the purchasable
     */
    private final int purchasePrice;

    /**
     * The effects upon purchase
     */
    private final ArrayList<SideEffect> purchaseEffects;

    /**
     * The purchasable entity
     */
    private final Purchasable purchasedItem;

    /**
     * Constructor
     *
     * @param merchant The merchant sells the purchasable entity
     * @param purchasePrice The price of the purchasable
     * @param purchaseEffects The effects upon purchase
     * @param purchasedItem The purchasable entity
     */
    public PurchaseAction(Actor merchant, int purchasePrice, ArrayList<SideEffect> purchaseEffects, Purchasable purchasedItem) {
        this.merchant = merchant;
        this.purchasePrice = purchasePrice;
        this.purchaseEffects = purchaseEffects;
        this.purchasedItem = purchasedItem;
    }

    /**
     *This method controls the flow of trading a purchase option with a merchant.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return the output of purchase action
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        String purchaseOutput;
        int actorBalance = actor.getBalance();

        // Check if the actor has enough wallet balance for the purchase option
        if (actorBalance >= getPurchasePrice()){

            // Deduct the customer wallet balance
            actor.deductBalance(getPurchasePrice());

            // Shows additional side effects upon successful purchase action
            for (SideEffect sideEffect : getPurchaseEffects()){
                sideEffect.apply(actor,map);
            }

            // Purchased item shows its purchase effect
            purchaseOutput = getPurchasedItem().purchased(actor,map);

        }else{
            purchaseOutput = String.format("%s do not have enough wallet " +
                    "balance to purchase %s", actor, getPurchasedItem());
        }
        return purchaseOutput;
    }

    /**
     * This method gets the purchase price of PurchaseAction
     *
     * @return the purchase price of PurchaseAction
     */
    public int getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * This method gets the purchase effects of PurchaseAction
     *
     * @return the purchase effects of PurchaseAction
     */
    public ArrayList<SideEffect> getPurchaseEffects() {
        return purchaseEffects;
    }

    /**
     * This method gets the purchased item of the PurchaseAction
     *
     * @return the purchased item of the PurchaseAction
     */
    public Purchasable getPurchasedItem() {
        return purchasedItem;
    }

    /**
     * This method describes the purchase action
     *
     * @param actor The actor performing the action.
     * @return the description of the purchase action
     */
    @Override
    public String menuDescription(Actor actor) {
        return String.format("%s purchases %s from %s",actor,
                purchasedItem,merchant);
    }
}
