package game.plants;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.attributes.Status;
import game.grounds.Soil;

/**
 * Class representing a Inheritree plant.
 *
 * @author hewjiajing
 * @version 1.0
 */
public class Inheritree extends Ground implements Crop
{
    /**
     * Stamina cost required to plant the inheritree.
     */
    private final int STAMINA_COST = 25;

    /**
     * Time needed for the inheritree to grow.
     */
    private final int GROW_TIME = 0;

    /**
     * Amount of health healed when the inheritree blooms.
     */
    private final int BLOOM_HP_HEAL = 5;

    /**
     * Amount of stamina restored when the inheritree blooms.
     */
    private final int BLOOM_STAMINA_HEAL = 5;

    /**
     * Constructor.
     */
    public Inheritree()
    {
        super('t', "Inheritree");
        this.addCapability(Status.BLESSED);
    }

    @Override
    public int getStaminaCost()
    {
        return STAMINA_COST;
    }

    @Override
    public int getGrowTime()
    {
        return GROW_TIME;
    }

    @Override
    public void plant(Actor actor, GameMap map)
    {
        //  convert the surrounding blights (x) to soil (.), all eight tiles
        for (Exit exit : map.locationOf(actor).getExits())
        {
            Location nearbyLocation = exit.getDestination();
            Ground nearbyGround = nearbyLocation.getGround();

            if (nearbyGround != null && nearbyGround.hasCapability(Status.CURSED))
            {
                nearbyLocation.setGround(new Soil());
            }
        }
    }

    @Override
    public void grow(Location location)
    {
        location.setGround(this);
    }

    @Override
    public void bloom(Location currentLocation)
    {
        // heal any actor within its surroundings by 5 points and restore their stamina by 5 points
        for (Exit exit : currentLocation.getExits())
        {
            Location nearbyLocation = exit.getDestination();

            if (nearbyLocation.containsAnActor())
            {
                Actor nearbyActor = nearbyLocation.getActor();
                nearbyActor.heal(BLOOM_HP_HEAL);

                if (nearbyActor.hasAttribute(BaseActorAttributes.STAMINA))
                {
                    nearbyActor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.INCREASE, BLOOM_STAMINA_HEAL);
                }
            }
        }
    }

    @Override
    public boolean getPlantCondition(Actor actor, GameMap map)
    {
        return map.locationOf(actor).getGround().hasCapability(Status.FREE_TO_PLANT) && actor.getAttribute(BaseActorAttributes.STAMINA) > STAMINA_COST;
    }

    @Override
    public void tick(Location currentLocation)
    {
        bloom(currentLocation);
    }

}
