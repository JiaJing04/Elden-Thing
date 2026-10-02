package game.miningSystem;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.attributes.Ability;
import game.attributes.Status;

/**
 * An abstract class represent a Trap type on the map ground.
 * Role: Ground subclass and Craftable implementer.
 * Responsibility: Define common logic can be shared across all subclasses.
 *
 * <p><b>Reduced Connascence:</b></p>
 *  <ul>
 *     <li><b>Connascence of Meaning:</b> Logic like “only enemies fall into traps” and “apply trap effect if enemy is present” is centralized, giving shared semantic meaning across all subclasses. This prevents inconsistency or divergent logic across trap types.</li>
 *     <li><b>Connascence of Type:</b> Use of strong typing (`Actor`, `Location`) and capabilities (`Status.HOSTILE_TO_ENEMY`, `Ability.CRAFT_ABILITY`) improves clarity and consistency while reducing the chance of misuse.</li>
 *     <li><b>Connascence of Execution:</b> Subclasses only need to define their unique `craftEffect()` implementation, not the entire tick or entry logic. This clean delegation reduces coupling on execution order between trap logic and game loop.</li>
 *   </ul>
 *
 * @author Goh Jia Xuan
 * @version 1.0
 */
public abstract class Trap extends Ground implements Craftable {

    /**
     * Constructor.
     *
     * @param displayChar character to display for this type of terrain
     * @param name the name of the trap.
     */
    public Trap(char displayChar, String name) {
        super(displayChar, name);
    }

    /**
     * The condition for actor to enter the trap.
     * Only enemies can drop into trap.
     *
     * @param actor the Actor to check
     * @return true if actor is enemy return otherwise false.
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        // actor is not farmer(the one that create the trap) and actor is enemy
        return actor.hasCapability(Status.HOSTILE_TO_ENEMY) && !actor.hasCapability(Ability.CRAFT_ABILITY);
    }

    /**
     * The craft effect carries out at every game tick.
     * If the location contain an enemy, then we will apply craft effect on enemy.
     * The craft effect is defined in every subclass. Each of them have their customise effect.
     *
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        if (location.containsAnActor() && location.getActor().hasCapability(Status.HOSTILE_TO_ENEMY)) {
            craftEffect(location.getActor(), location);
        }
    }

    /**
     * The effect applies to enemy when enemy drop into the trap.
     * Set to abstract to allow every subclass defines own effect.
     *
     * @param actor the actor affected by the effect
     * @param location the location of the actor
     */
    abstract void craftEffect(Actor actor, Location location);
}
