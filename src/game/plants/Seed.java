package game.plants;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.PlantAction;

import java.util.Objects;

/**
 * A class that represents a crop's seed.
 *
 * @author hewjiajing
 * @version 1.0
 */
public class Seed extends Item
{
    /**
     * The crop that this seed will grow into.
     */
    private Crop crop;

    /**
     * The age of the seed after planting.
     */
    private int age;

    /**
     * Indicator of the seed has been planted or not.
     */
    private boolean planted;

    /**
     * Constructor.
     *
     * @param crop the crop that the seed will grow into
     */
    public Seed(Crop crop)
    {
        super("Seed", '*', true);
        this.crop = crop;
        this.age = 0;
        this.planted = false;
    }

    /**
     * Plant the seed.
     *
     * @param actor the actor planting the seed
     * @param map the game map
     */
    public void plant(Actor actor, GameMap map)
    {
        this.planted = true;
        this.crop.plant(actor, map);
    }

    /**
     * Return the stamina cost required to plant the seed.
     *
     * @return the stamina cost
     */
    public int getStaminaCost()
    {
        return crop.getStaminaCost();
    }

    @Override
    public void tick(Location currentLocation)
    {
        if (planted)
        {
            if (Objects.equals(age, crop.getGrowTime()))
            {
                currentLocation.removeItem(this);
                crop.grow(currentLocation);
            }
            age ++;
        }
    }

    @Override
    public ActionList allowableActions(Actor owner, GameMap location)
    {
        ActionList actions = super.allowableActions(owner, location);

        if (crop.getPlantCondition(owner, location))
        {
            actions.add(new PlantAction(this));
        }
        return actions;
    }

    @Override
    public String toString()
    {
        return crop + " seed";
    }
}
