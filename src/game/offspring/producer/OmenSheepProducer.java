package game.offspring.producer;

import edu.monash.fit2099.engine.actors.Actor;
import game.actors.OmenSheep;
import game.behaviours.controllers.NPCController;
import game.conditions.Condition;

/**
 * Class for OmenSheep Producer
 *
 * @author yujiaming
 * @version 2.0
 * Modified by: hewjiajing
 */
public class OmenSheepProducer implements Producer
{
    /**
     * Condition to reproduce for OmenSheep
     */
    private Condition reproduceCondition;
    private NPCController npcController;

    /**
     * Constructor
     *
     * @param reproduceCondition Condition to reproduce for OmenSheep
     */
    public OmenSheepProducer(Condition reproduceCondition, NPCController npcController)
    {
        this.reproduceCondition = reproduceCondition;
        this.npcController = npcController;
    }
    /**
     * Method to create OmenSheep to produce Offspring of themselves
     * The Offspring will inherit the Condition of parent to produce Offspring
     * Currently returns as null as REQ does not require
     *
     * @return null
     */
    @Override
    public Actor create()
    {
        // uncomment for OmenSheep to produce Offspring
        // new OmenSheep(reproduceCondition, this);
        return null;
    }

    /**
     * Method to create OmenSheep that can produce an Item (Egg)
     *
     * @param itemCondition condition for OmenSheep to reproduce an Item
     * @return OmenSheep with ability to produce Item
     */
    @Override
    public Actor create(Condition itemCondition)
    {
        return new OmenSheep(reproduceCondition, itemCondition, this, npcController);
    }
}
