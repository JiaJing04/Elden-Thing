package game.actors.boss;

/**
 * Interface to represent a Tree Part
 *
 * @author ngziwei
 * @version 1.0
 */
public interface TreePart {

    /**
     * This method returns the damage point brought by the tree part
     *
     * @return the damage point brought by the tree part
     */
    int getDamage();

    /**
     * This method shows the effect of a tree part growing
     *
     * @return the effect of a tree part growing
     */
    String grow();

}
