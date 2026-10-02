package game.actors.boss;

import edu.monash.fit2099.engine.actors.Actor;

/**
 * Class representing a tree leaf
 *
 * @author ngziwei
 * @version 1.0
 */
public class Leaf implements TreePart{

    private final Actor origin;

    /**
     * A constant which represents the Leaf itself damage points.
     */
    private static final int DAMAGE_POINT = 1;

    /**
     * Constructor for Leaf
     *
     * @param origin the origin the leaf attached to.
     */
    public Leaf(Actor origin) {
        this.origin = origin;
    }

    /**
     * This method returns the damage point brought by the Leaf
     *
     * @return the damage point brought by the Leaf
     */
    @Override
    public int getDamage() {
        return DAMAGE_POINT;
    }

    /**
     * This method gets the origin of the leaf
     *
     * @return the origin of the leaf
     */
    public Actor getOrigin() {
        return origin;
    }

    /**
     * This method shows the effect of a leaf when its origin grow
     *
     * @return the effect of a leaf when its origin grow
     */
    @Override
    public String grow() {

        int healingPoint = 5;

        getOrigin().heal(healingPoint);

        String growingEffect = String.format("%s heals %s by %s hp\n",this,
                                            origin,healingPoint);

        return growingEffect;
    }

    /**
     * This method describes Leaf
     *
     * @return the description of Leaf
     */
    @Override
    public String toString() {
        return "Leaf";
    }
}
