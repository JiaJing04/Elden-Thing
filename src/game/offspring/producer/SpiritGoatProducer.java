package game.offspring.producer;

import edu.monash.fit2099.engine.actors.Actor;
import game.actors.SpiritGoat;
import game.behaviours.controllers.NPCController;
import game.conditions.Condition;

/**
 * Class to produce a new SpiritGoat
 *
 * @author yujiaming
 * @version 2.0
 * Modified by: hewjiajing
 */
public class SpiritGoatProducer implements Producer
{
    /**
     * Condition for a SpiritGoat to reproduce
     */
    private Condition reproduceCondition;
    private NPCController npcController;

    /**
     * Constructor
     * @param reproduceCondition the Condition for SpiritGoat to reproduce
     */
    public SpiritGoatProducer(Condition reproduceCondition, NPCController npcController)
    {
        this.reproduceCondition = reproduceCondition;
        this.npcController = npcController;
    }
    /**
     * Method for SpiritGoat to produce an Offspring
     * @return a new SpiritGoat with ability to produce Offspring of themselves
     */
    @Override
    public Actor create()
    {
        return new SpiritGoat(reproduceCondition, this, npcController);
    }

    /**
     * Method for SpiritGoat to produce an Item that can hatch into something
     * @param itemCondition the Condition for Item to hatch into something
     * @return SpiritGoat with ability to produce an Item
     */
    @Override
    public Actor create(Condition itemCondition)
    {
        return null;
    }
}
