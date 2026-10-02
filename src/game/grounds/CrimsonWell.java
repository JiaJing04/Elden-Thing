package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.attributes.Status;
import game.effects.AttributeUpdateEffect;
import game.effects.HealthLossEffect;
import game.items.Potion;
import game.utilities.Utils;

/**
 * Crimson Well ground that gives a potion reward or punishment after standing for 2 turns.
 * @author Ooi Pei Shuen
 * @version 1.0
 */
public class CrimsonWell extends Ground {

    /**
     * Last actor that stood on this tile.
     */
    private Actor lastActor = null;

    /**
     * Number of consecutive turns the actor has stood on it.
     */
    private int turnsOccupied = 0;

    /**
     * The cursed ground that was replaced by this Crimson Well.
     */
    private final Ground previousCursedGround;

    /**
     * Creates a Crimson Well from a cursed ground.
     *
     * @param originalCursedGround the cursed ground this well replaces
     */
    public CrimsonWell(Ground originalCursedGround) {
        super('~', "CrimsonWellGround");
        this.addCapability(Status.CRIMSON);
        this.previousCursedGround = originalCursedGround;
    }

    /**
     * Reverts to original ground if CRIMSON_END. If actor stands for 2 turns, drop potion and revert.
     *
     * @param location the location of this Crimson Well
     */
    @Override
    public void tick(Location location) {

        if (this.hasCapability(Status.CRIMSON_END)){
            location.setGround(previousCursedGround);
            return;
        }

        Actor actor = location.getActor();

        if (actor != null) {
            if (actor.equals(lastActor)) {
                turnsOccupied++;
            } else {
                lastActor = actor;
                turnsOccupied = 1;
            }

            if (turnsOccupied >= 2) {
                Potion reward = getRewardPotion(actor);
                dropPotion(location, reward);
                location.setGround(previousCursedGround);
                lastActor = null;
                turnsOccupied = 0;
            }
        } else {
            lastActor = null;
            turnsOccupied = 0;
        }
    }

    /**
     * Tries to drop potion in adjacent tile, else drops at current location.
     *
     * @param location current location
     * @param reward the potion to drop
     */
    private void dropPotion(Location location, Potion reward){
        for (Exit exit: location.getExits()){
            Location adjacent = exit.getDestination();

            if (!adjacent.containsAnActor() && adjacent.getGround().canActorEnter(null)){
                adjacent.addItem(reward);
                return;
            }
        }

        location.addItem(reward);
    }

    public Potion getRewardPotion(Actor actor){

        int halfHealth = actor.getAttribute(BaseActorAttributes.HEALTH) / 2;

        if (Utils.generateRandomNumber(2) == 0){
            return new Potion(
                    "Potion",
                    new AttributeUpdateEffect(
                            ActorAttributeOperations.INCREASE,
                            BaseActorAttributes.HEALTH,
                            halfHealth
                    )
            );
        } else {
            return new Potion(
                    "Potion",
                    new HealthLossEffect(halfHealth)
            );
        }
    }
}
