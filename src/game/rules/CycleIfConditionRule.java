package game.rules;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.conditions.Condition;
import game.phases.TimePhase;

import java.util.List;

/**
 * Rule that cycles to the next phase if the condition is satisfied.
 * @author Ooi Pei Shuen
 * @version 1.0
 */
public class CycleIfConditionRule implements TransitionRule {

    /**
     * The condition to check before cycling to the next phase.
     */
    private final Condition condition;

    /**
     * Creates a rule that checks the given condition before cycling to the next phase.
     *
     * @param condition the condition to evaluate
     */
    public CycleIfConditionRule(Condition condition) {
        this.condition = condition;
    }

    /**
     * Returns the next index in the phase cycle if the condition is satisfied.
     * Uses modulo to wrap around the cycle. Otherwise, returns currentIndex.
     *
     * @param currentIndex current index in the phase list
     * @param phases the phase cycle
     * @param actor the actor to evaluate
     * @param map the current game map
     * @return next index in cycle or currentIndex
     */
    @Override
    public int getNextIndex(int currentIndex, List<TimePhase> phases, Actor actor, GameMap map) {
            if (condition.satisfies(actor, map)) {
                return (currentIndex + 1) % phases.size();
            }
            return currentIndex;
    }
}
