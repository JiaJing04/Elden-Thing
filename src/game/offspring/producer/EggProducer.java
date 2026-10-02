package game.offspring.producer;

import edu.monash.fit2099.engine.items.Item;
import game.items.Egg;
import game.conditions.Condition;
import game.offspring.EggHatchable;

/**
 * This class represents a producer to produce an egg
 *
 * @author yujiaming
 * @version 1.0
 */
public class EggProducer extends ItemProducer{

    /**
     * The name of egg produced
     */
    private String name;

    /**
     * The parent of the egg
     */
    private EggHatchable parent;

    /**
     * Constructor
     *
     * @param name The name of egg produced
     * @param parent The parent of the egg
     * @param condition The hatching condition
     * @param producer The producer
     */
    public EggProducer(String name, EggHatchable parent, Condition condition, Producer producer) {
        super(condition, producer);
        this.name = name;
        this.parent = parent;
    }

    /**
     * This method produces the egg
     *
     * @return an egg instance
     */
    @Override
    public Item turnIntoItem() {
        return new Egg(name, parent, getCondition(), getProducer());
    }
}
