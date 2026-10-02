package game.phases;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.rules.TransitionRule;

import java.util.TreeMap;

/**
 * Checks a list of rules to decide if the phase should change.
 * Returns the next index in the phase cycle based on the rules.
 * @author Ooi Pei Shuen
 * @version 2.0
 */
public class PhaseTransitionLogic {

    /**
     * Prioritized rules (key = priority).
     */
    private final TreeMap<Integer, TransitionRule> rules;

    /**
     * Creates phase transition logic using prioritized rules.
     *
     * @param rules TreeMap of rules with unique priorities
     */
    public PhaseTransitionLogic(TreeMap<Integer, TransitionRule> rules) {
        this.rules = rules;
    }

    /**
     * Returns the next index in the phase cycle if any rule applies.
     * If no rule applies, returns currentIndex.
     *
     * @param currentIndex current phase index
     * @param phases list of phases
     * @param actor the actor used in rule checking
     * @param map the game map
     * @return next phase index or currentIndex
     */
    public int getNextIndex(int currentIndex, java.util.List<TimePhase> phases, Actor actor, GameMap map) {
        for (TransitionRule rule : rules.values()) {
            int next = rule.getNextIndex(currentIndex, phases, actor, map);
            if (next != currentIndex) {
                return next;
            }
        }
        return currentIndex;
    }
}

