package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import game.attributes.Status;
import game.plants.Seed;

/**
 * Class representing an action to plant.
 *
 * @author hewjiajing
 * @version 1.0
 */
public class PlantAction extends Action
{
    /**
     * The Seed that is to be planted.
     */
    private Seed seed;

    /**
     * Contructor.
     *
     * @param seed Seed to be planted.
     */
    public PlantAction(Seed seed)
    {
        this.seed = seed;
    }

    /**
     * Executes the plant action.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return the output of listen action
     */
    @Override
    public String execute(Actor actor, GameMap map)
    {
        // actor plant the seed
        actor.removeItemFromInventory(seed);

        // seed is now not portable
        seed.togglePortability();

        // seed is planted on the ground
        map.locationOf(actor).addItem(seed);
        seed.plant(actor, map);

        // current location of the planted seed is not available to be planted anymore
        map.locationOf(actor).getGround().removeCapability(Status.FREE_TO_PLANT);

        // actor is affected by rot when action executes
        rotEffect(actor);

        return menuDescription(actor);
    }

    /**
     * This method describes the plant action
     *
     * @param actor The actor performing the action.
     * @return the description of the purchase action
     */
    @Override
    public String menuDescription(Actor actor)
    {
        return actor + " plants " + seed;
    }

    /**
     * Crimson Rot effect
     *
     * @param actor Actor that is affected by Crimson rot
     */
    public void rotEffect(Actor actor)
    {
        if (actor.hasAttribute(BaseActorAttributes.STAMINA))
        {
            actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.DECREASE, seed.getStaminaCost());
        }
    }

}
