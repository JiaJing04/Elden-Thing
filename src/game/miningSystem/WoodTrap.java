package game.miningSystem;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.attributes.Status;

/**
 * A trap subclass which is build from Wood mine item.
 * Responsibility: define Craftable interface methods and abstract method of Trap class.
 *
 * @author Goh Jia Xuan
 * @version 1.0
 */
public class WoodTrap extends Trap {

    /***
     * Constructor.
     */
    public WoodTrap() {
        super('W', "Wood Trap");
    }

    /**
     * Override interface method to get the Soil ground available for building the trap.
     * The Soil ground type is represented by Status.FREE_TO_PLANT enum.
     *
     * @param actor the actor performing craft action.
     * @param location the location actor is standing
     * @return the targeted location if found Soil ground type otherwise return null.
     */
    @Override
    public Location locationToCraft(Actor actor, Location location) {
        for (Exit exit : location.getExits()) {
            Location adjacent = exit.getDestination();
            Ground ground = adjacent.getGround();

            // wood trap build at soil ground
            if (ground != null && ground.hasCapability(Status.FREE_TO_PLANT)) {
                return adjacent;
            }
        }
        return null;
    }

    /**
     * set new ground type into this instance.
     *
     * @param location the location to change ground type.
     */
    @Override
    public void setNewGround(Location location) {
        location.setGround(this);
    }

    /**
     * The effect applies on enemy when they drop into wood trap.
     * enemy will be hurt by 15 points.
     *
     * @param actor the actor affected by the effect
     * @param location the location of the actor
     */
    @Override
    public void craftEffect(Actor actor, Location location) {
        Display display = new Display();
        actor.hurt(15);
        display.println(actor + " drop into wood trap and get hurt by 15 points.");
    }
}
