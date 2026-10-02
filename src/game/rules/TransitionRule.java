package game.rules;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.phases.TimePhase;

import java.util.List;


/**
 * Rule used to decide whether to switch to another phase.
 * @author Ooi Pei Shuen
 * @version 1.0
 */
public interface TransitionRule {

    /**
     * Returns the next index in the phase cycle based on the current context.
     * Returns currentIndex if no change is needed.
     *
     * @param currentIndex the current phase index
     * @param phases the list of all time phases
     * @param actor the actor used in evaluation
     * @param map the current game map
     * @return the next index to use, or currentIndex if unchanged
     */
    int getNextIndex(int currentIndex, List<TimePhase> phases, Actor actor, GameMap map);
}
