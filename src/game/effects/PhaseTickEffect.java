package game.effects;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.StatusEffect;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.phases.managers.TimePhaseManager;

/**
 * Status effect that updates the phase every turn using the TimePhaseManager.
 * @author Ooi Pei Shuen
 * @version 1.0
 */
public class PhaseTickEffect extends StatusEffect {

    /**
     * Phase manager used to control phase transitions.
     */
    private final TimePhaseManager manager;


    /**
     * Creates a phase tick effect that updates the phase each turn.
     *
     * @param manager the phase manager to use
     */
    public PhaseTickEffect(TimePhaseManager manager) {
        super("Phase Tick Effect");
        this.manager = manager;
    }

    /**
     * Updates the current phase and prints the phase name.
     *
     * @param location the actor's location
     * @param actor the actor affected
     */
    @Override
    public void tick(Location location, Actor actor) {
        GameMap map = location.map();
        Display display = new Display();
        manager.update(map, actor, display);
        String currentPhaseName = manager.getCurrentPhaseName();
        display.println("\n[Current Phase]: " + currentPhaseName.toUpperCase());
    }
}