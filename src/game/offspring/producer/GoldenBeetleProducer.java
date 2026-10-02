package game.offspring.producer;

import edu.monash.fit2099.engine.actors.Actor;
import game.actors.GoldenBeetle;
import game.behaviours.controllers.NPCController;
import game.conditions.Condition;

/**
 * Class for GoldenBeetleProducer
 *
 * @author yujiaming
 * @version 2.0
 * Modified by: hewjiajing
 */
public class GoldenBeetleProducer implements Producer
{
    /**
     * Condition for GoldenBeetle to reproduce
     */
    private Condition reproduceCondition;
    private NPCController npcController;

    /**
     * Constructor
     *
     * @param reproduceCondition Condition for GoldenBeetle to reproduce
     */
    public GoldenBeetleProducer(Condition reproduceCondition, NPCController npcController)
    {
        this.reproduceCondition = reproduceCondition;
        this.npcController = npcController;
    }

    /**
     * Method to create a new GoldenBeetle that can produce Offspring
     * currently return as null as requirement does not specify GoldenBeetle
     * able to produce Offspring
     *
     * @return null
     */
    @Override
    public Actor create()
    {
        // uncomment below for GoldenBeetle to produce offspring of self
        // return new GoldenBeetle(reproduceCondition, this);
        return null;
    }

    /**
     * Method to create a new GoldenBeetle that can produce an Item (Egg)
     *
     * @param itemCondition the Egg hatching condition
     * @return a new GoldenBeetle with ability of reproducing an Item (Egg)
     */
    @Override
    public Actor create(Condition itemCondition)
    {
        return new GoldenBeetle(reproduceCondition, itemCondition, this, npcController);
    }
}