package game.actors.boss;

import edu.monash.fit2099.engine.actors.Actor;
import game.utilities.Utils;

import java.util.ArrayList;

/**
 * Class representing a tree branch
 *
 * @author ngziwei
 * @version 2.0
 */
public class Branch implements TreePart{

    private final Actor origin;

    private final ArrayList<TreePart> treeParts = new ArrayList<>();

    /**
     * A constant which represents the Branch itself damage points.
     */
    private static final int DAMAGE_POINT = 3;

    /**
     * Constructor for Branch
     *
     * @param origin the origin the branch attached to.
     */
    public Branch(Actor origin) {
        this.origin = origin;
    }

    /**
     * This method returns the damage point brought by the Branch
     *
     * @return the damage point brought by the Branch
     */
    @Override
    public int getDamage() {

        int totalDamage = 0;

        // Accumulates the damage points of the tree parts attached to it
        for (TreePart treePart : getTreeParts()){
            totalDamage += treePart.getDamage();
        }

        totalDamage += DAMAGE_POINT;

        return totalDamage;
    }

    /**
     * This method gets the tree parts of the branch
     *
     * @return the tree parts of the branch
     */
    public ArrayList<TreePart> getTreeParts() {
        return treeParts;
    }

    /**
     * This method shows the growing effect of a branch.
     *
     * @return the growing effect of a branch.
     */
    @Override
    public String grow() {

        int randNum = Utils.generateRandomNumber(100);

        TreePart newGrowPart;

        StringBuilder result = new StringBuilder(this + " is growing...\n");

        // 50% to grow a Leaf and Branch respectively
        if (randNum <= 50){
            newGrowPart = new Leaf(origin);
        }else{
            newGrowPart = new Branch(origin);
        }

        getTreeParts().add(newGrowPart);
        result.append(String.format("it grows a %s...\n",newGrowPart));

        // Each existing tree part grows
        for (TreePart treePart : getTreeParts()){
            result.append(treePart.grow());
        }

        return result.toString();
    }

    /**
     * This method describes Branch
     *
     * @return the description of Branch
     */
    @Override
    public String toString() {
        return "Branch";
    }
}
