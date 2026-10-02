package game.phases.managers;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.phases.PhaseTransitionLogic;
import game.phases.TimePhase;

import java.util.List;

/**
 * Manages the current time phase of the game and handles transitions between phases.
 * @author Ooi Pei Shuen
 * @version 2.0
 */
public class TimePhaseManager {

    /**
     * The list of time phases that form the cycle.
     */
    private final List<TimePhase> phaseCycle;

    /**
     * The logic used to determine when and how to transition to the next phase.
     */
    private final PhaseTransitionLogic transitionLogic;

    /**
     * The index of the current phase in the cycle.
     */
    private int currentIndex;

    /**
     * Constructs a TimePhaseManager with a phase cycle and transition logic.
     *
     * @param phaseCycle the list of phases in cycle order
     * @param transitionLogic the logic used to decide phase transitions
     */
    public TimePhaseManager(List<TimePhase> phaseCycle, PhaseTransitionLogic transitionLogic) {
        this.phaseCycle = phaseCycle;
        this.transitionLogic = transitionLogic;
        this.currentIndex = 0;
    }

    /**
     * Updates the current phase based on transition rules and applies the phase effects.
     *
     * @param map the current game map
     * @param actor the main actor or player used in transition logic
     * @param display the display for showing phase-related messages
     */
    public void update(GameMap map, Actor actor, Display display) {
        currentIndex = transitionLogic.getNextIndex(currentIndex, phaseCycle, actor, map);

        TimePhase currentPhase = phaseCycle.get(currentIndex);
        if (currentPhase != null) {
            currentPhase.applyPhaseEffects(map, display);
        }
    }

    /**
     * Returns the name of the current time phase.
     *
     * @return the current phase's name
     */
    public String getCurrentPhaseName() {
        return phaseCycle.get(currentIndex).getPhaseName().name();
    }
}