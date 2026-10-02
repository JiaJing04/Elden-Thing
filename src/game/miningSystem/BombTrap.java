package game.miningSystem;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.grounds.Soil;

/**
 * A trap subclass which is build from Explosive Powder mine item.
 * Responsibility: define Craftable interface methods and abstract method of Trap class.
 *
 * @author Goh Jia Xuan
 * @version 1.0
 */
public class BombTrap extends Trap{

    /**
     * Constructor.
     */
    public BombTrap() {
        super('B', "Bomb Trap");
    }

    /**
     * Override interface method to get any ground type available for building the trap.
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

            // build at any location
            if (ground != null) {
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
     * The effect applies on enemy when they drop into bomb trap.
     * enemy will instantly be killed and remove from map along with the trap being converted into Soil ground type.
     *
     * @param actor the actor affected by the effect
     * @param location the location of the actor
     */
    @Override
    public void craftEffect(Actor actor, Location location) {
        // enemy dead immediately
        Display display = new Display();

        // bomb can only use once
        location.setGround(new Soil());

        // remove actor from map
        actor.unconscious(location.map());

        display.println(actor + " drop into bomb trap and is dead from a bomb trap.");
    }
}
