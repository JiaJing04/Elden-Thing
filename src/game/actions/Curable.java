package game.actions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Interface representing an object that is able to be cured.
 *
 * @author hewjiajing
 * @version 1.0
 */
public interface Curable
{
    /**
     * Method to cure an object.
     *
     * @param actor The actor that perform the cure.
     * @param location The location of the object to be cured.
     */
    void cure(Actor actor, Location location);
}
