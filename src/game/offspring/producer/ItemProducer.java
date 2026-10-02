package game.offspring.producer;

import edu.monash.fit2099.engine.items.Item;
import game.conditions.Condition;

/**
 * Class representing a producer to produce an Item
 *
 * @author yujiaming
 * @version 1.0
 */
public abstract class ItemProducer {

    /**
     * Condition to produce
     */
    private Condition condition;

    /**
     * The producer this item producer produces
     */
    private Producer producer;

    /**
     * Constructor
     *
     * @param condition Condition to produce
     * @param producer The producer this item producer produces
     */
    public ItemProducer(Condition condition, Producer producer) {
        this.condition = condition;
        this.producer = producer;
    }

    /**
     * This method gets the producer this item producer produces
     *
     * @return the producer this item producer produces
     */
    public Producer getProducer(){
        return producer;
    }

    /**
     * This method gets the condition to produce
     *
     * @return the condition to produce
     */
    public Condition getCondition(){
        return condition;
    }

    /**
     * This method produce an item
     *
     * @return the item be produced
     */
    public abstract Item turnIntoItem();

}
