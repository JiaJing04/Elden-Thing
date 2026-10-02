package game.behaviours.assistantBehaviour;

import edu.monash.fit2099.engine.positions.Location;

/**
 * An interface represent NPC has curing capability.
 * NPC curing logic defined in this class.
 *
 * @author Goh Jia Xuan
 * @version 1.0
 */
public interface CureAssistant {

    /**
     * personalise curing condition.
     * @param location the location to check condition
     * @return true if condition met otherwise false.
     */
    boolean canCure(Location location);

    /**
     * personalise cure effect
     * @param location the location to cure.
     */
    void cureEffect(Location location);

    /**
     * personalise actor consciousness checking attribute (stamina/health)
     *
     * @param location the location to removen actor from map if unconscious.
     * @return true if actor is conscious otherwise false.
     */
    boolean actorConsciousness(Location location);

}
