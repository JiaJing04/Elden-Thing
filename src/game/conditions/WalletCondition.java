package game.conditions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * A class representing a condition that checks an actor's wallet balance.
 *
 * @author yujiaming
 * @version 1.0
 */
public class WalletCondition implements Condition {

    /**
     * The threshold of the wallet balance wanted to compare.
     */
    private final int threshold;

    /**
     * The operator used to compare the wallet balance and the threshold.
     */
    private final ComparisonOperator comparisonOperator;

    /**
     * Constructor
     *
     * @param threshold The threshold of the wallet balance wanted to compare.
     * @param comparisonOperator The operator used to compare the wallet balance
     *                           and the threshold.
     */
    public WalletCondition(int threshold, ComparisonOperator comparisonOperator){
        this.threshold = threshold;
        this.comparisonOperator = comparisonOperator;
    }

    /**
     * Checks if the actor's wallet balance satisfies the condition.
     *
     * @param actor The actor whose wallet balance is being checked.
     * @param gameMap The game map the actor is on.
     * @return True if the condition is satisfied, false otherwise.
     */
    @Override
    public boolean satisfies(Actor actor, GameMap gameMap) {

        int targetWalletBalance = actor.getBalance();

        return switch (comparisonOperator){
            case E -> targetWalletBalance == threshold;
            case GE -> targetWalletBalance >= threshold;
            case LE -> targetWalletBalance <= threshold;
            case G -> targetWalletBalance > threshold;
            case L -> targetWalletBalance < threshold;
        };
    }
}
