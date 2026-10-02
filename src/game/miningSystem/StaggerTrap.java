package game.miningSystem;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.attributes.Status;

/**
 * A trap subclass which is build from Magical Potion mine item.
 * Responsibility: define Craftable interface methods and abstract method of Trap class.
 *
 * @author Goh Jia Xuan
 * @version 1.0
 */
public class StaggerTrap extends Trap{

    /**
     * the actor drop into the trap.
     */
    private Actor storedActor = null;

    /**
     * the location of the trap.
     */
    private Location storedLocation = null;

    /**
     * the remaining turn for the actor to return back to the map.
     */
    private int turnsRemaining = 0;

    /**
     * Constructor.
     */
    public StaggerTrap() {
        super('S', "Stagger Trap");
    }

    /**
     * Override interface method to get the ground type available for building the trap.
     * The cursed ground type is represented by Status.CURSED enum.
     *
     * @param actor the actor performing craft action.
     * @param location the location actor is standing
     * @return the targeted location if found cursed ground type otherwise return null.
     */
    @Override
    public Location locationToCraft(Actor actor, Location location) {
        for (Exit exit : location.getExits()) {
            Location adjacent = exit.getDestination();
            Ground ground = adjacent.getGround();

            // wood trap build at cursed ground
            if (ground != null && ground.hasCapability(Status.CURSED)) {
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
     * Override tick from parent class to update status of actor trapped inside.
     * If turn remaining == 0, then return actor back into the map and reset attribute status
     * to allow other enemies to drop into this trap.
     *
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        // This will check if an actor steps on the trap and trigger the effect
        if (storedActor == null && location.containsAnActor()
                && location.getActor().hasCapability(Status.HOSTILE_TO_ENEMY)) {
            craftEffect(location.getActor(), location);
        }

        // If trap is currently holding an actor
        if (storedActor != null) {
            turnsRemaining--;

            if (turnsRemaining <= 0) {
                if (!storedLocation.containsAnActor()) {
                    storedLocation.addActor(storedActor);
                    new Display().println(storedActor + " returns from stagger trap!");

                } else {
                    new Display().println("Stagger trap failed to return " + storedActor + " because location is occupied!");
                    // delay return
                    turnsRemaining = 1; // Retry next turn (most likely won't happen)
                }

                // Reset for next actor
                storedActor = null;
                storedLocation = null;
            }
        }
    }

    /**
     * The effect applies on enemy when they drop into stagger trap.
     * Actor will vanish from the map for 3 turns.
     * If the trap currently have an actor trapped inside, this trap is not available for other actor.
     *
     * @param actor the actor affected by the effect
     * @param location the location of the actor
     */
    @Override
    void craftEffect(Actor actor, Location location) {
        // Only trigger once
        if (storedActor != null) return;

        Display display = new Display();
        storedActor = actor;
        storedLocation = location;
        turnsRemaining = 3;

        location.map().removeActor(actor);
        display.println(actor + " drop into stagger trap and is staggered and vanishes for 3 turns!");
    }
}
