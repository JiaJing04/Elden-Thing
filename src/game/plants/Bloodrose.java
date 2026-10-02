package game.plants;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.attributes.Status;

/**
 * Class representing a Bloodrose plant.
 *
 * @author hewjiajing
 * @version 1.0
 */
public class Bloodrose extends Ground implements Crop
{
    /**
     * Stamina cost required to plant the bloodrose due to Crimson Rot.
     */
    private final int STAMINA_COST = 75;

    /**
     * Time needed for the bloodrose to grow.
     */
    private final int GROW_TIME = 0;

    /**
     * Health points lost when planting the bloodrose.
     */
    private final int PLANT_HP_COST = 5;

    /**
     * Health points lost when the bloodrose blooms.
     */
    private final int BLOOM_HP_COST = 10;

    /**
     * Constructor.
     */
    public Bloodrose()
    {
        super('w', "Bloodrose");
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
        // sap the Farmer’s health by 5 points
        actor.hurt(PLANT_HP_COST);

        if (!actor.isConscious())
        {
            actor.unconscious(map);
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
        // hurt surrounding actors by 10 points
        for (Exit exit : currentLocation.getExits())
        {
            Location nearbyLocation = exit.getDestination();

            if (nearbyLocation.containsAnActor())
            {
                Actor nearbyActor = nearbyLocation.getActor();
                nearbyActor.hurt(BLOOM_HP_COST);

                if (!nearbyActor.isConscious())
                {
                    nearbyActor.unconscious(currentLocation.map());
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
