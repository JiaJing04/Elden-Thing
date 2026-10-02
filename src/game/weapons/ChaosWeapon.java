package game.weapons;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;

/**
 * Class representing an intrinsic weapon called a ChaosWeapon
 *
 * @author ngziwei
 * @version 2.0
 */
public class ChaosWeapon extends IntrinsicWeapon {

    /**
     * Constructor
     *
     * @param damage the damage point of the ChaosWeapon
     */
    public ChaosWeapon(int damage) {
        super(damage, "smash", 75);
    }

}
