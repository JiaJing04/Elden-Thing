package game.conditions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;

import java.util.List;

/**
 * A class representing a condition that if the Entity surrounding ground has
 * certain capabilities
 *
 * @author yujiaming
 * @version 1.0
 */
public class SurroundCapabilitiesCondition implements Condition {

    /**
     * The capability to be checked
     */
    private final Enum<?> capability;

    /**
     * The actor to be checked
     */
    private final Actor actorToCheck;

    /**
     * Constructor
     *
     * @param actorToCheck The actor to be checked
     * @param capability The capability to be checked
     */
    public SurroundCapabilitiesCondition(Actor actorToCheck, Enum<?> capability){
        this.actorToCheck = actorToCheck;
        this.capability = capability;
    }

    /**
     * Constructor
     *
     * @param capability The capability to be checked
     */
    public SurroundCapabilitiesCondition(Enum<?> capability) {
        this.actorToCheck = null;
        this.capability = capability;
    }

    /**
     * Checks if the surroundings of the actor to be checked has the certain
     * capability
     *
     * @param actor The actor involved in the condition checking
     * @param gameMap The game map the actor is on.
     * @return True if the condition is satisfied, false otherwise.
     */
    @Override
    public boolean satisfies(Actor actor, GameMap gameMap) {
        List<Exit> exitList;

        exitList = gameMap.locationOf(actorToCheck).getExits();
        return helper(exitList);
    }

    /**
     * Checks if the surroundings of the location passed in has the certain
     * capability
     *
     * @param location the location to be checked its surroundings
     * @return True if the condition is satisfied, false otherwise.
     */
    @Override
    public boolean satisfies(Location location){
        List<Exit> exitList = location.getExits();

        return helper(exitList);
    }

    /**
     * A helper method to check if the list of exists have the target capability
     *
     * @param exitList the list of exists to be checked if having the target
     *                 capability
     * @return True if there is an exit fulfill the condition
     */
    private boolean helper(List<Exit> exitList){

        boolean flag = false;
        for (Exit exit : exitList){

            Ground ground = exit.getDestination().getGround();
            if (ground.hasCapability(capability)){
                flag = true;
            }

            if (exit.getDestination().containsAnActor()){
                Actor surroundActor = exit.getDestination().getActor();
                if (surroundActor.hasCapability(capability)) {
                    flag = true;
                }
            }

            if (!exit.getDestination().getItems().isEmpty()){
                List<Item> itemList = exit.getDestination().getItems();
                for (Item item : itemList){
                    if (item.hasCapability(capability)){
                        flag = true;
                    }
                }
            }

        }
        return flag;
    }
}