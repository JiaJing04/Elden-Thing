package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

/**
 * A class which represents a behaviour that allows an actor to follow a target actor.
 * Calculation referenced from {@link edu.monash.fit2099.demo.mars.behaviours.FollowBehaviour}
 */
public class FollowBehaviour implements Behaviour {

    private final Actor target;

    /**
     * A constructor which creates a FollowBehaviour for a specific target.
     *
     * @param subject the Actor to follow
     */
    public FollowBehaviour(Actor subject) {
        this.target = subject;
    }

    /**
     * A method which determines the next movement actions that brings the actor closer to the target.
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return an action to move the actor toward the target, or null if no movement is possible.
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        if(!map.contains(target) || !map.contains(actor))
            return null;

        Location actorLocation = map.locationOf(actor);
        Location targetLocation = map.locationOf(target);

        int currentDistance = distance(actorLocation, targetLocation);
        Exit bestExit = null;
        int bestDistance = currentDistance;

        for (Exit exit : actorLocation.getExits()) {
            Location nextLocation = exit.getDestination();
            if (nextLocation.canActorEnter(actor)) {
                int newDistance = distance(nextLocation, targetLocation);
                if (newDistance < bestDistance) {
                    bestDistance = newDistance;
                    bestExit = exit;
                }
            }
        }

        if (bestExit != null) {
            return new MoveActorAction(bestExit.getDestination(), bestExit.getName());
        }

        return null;
    }

    /**
     * A method which calculates the Manhattan distance between two locations.
     *
     * @param a the first location
     * @param b the second location
     * @return the Manhattan distance between the two locations.
     */
    private int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }
}

