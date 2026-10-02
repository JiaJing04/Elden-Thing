package game.attributes;

/**
 * Use this enum to represent abilities.
 * Example #1: if the player is capable jumping over walls, you can attach Ability.WALL_JUMP to the player class
 *
 * @author hewjiajing
 * @version 1.0
 */
public enum Ability
{
    /**
     * Ability to cure other objects.
     */
    CURE_ABILITY,

    /**
     * Ability to be followed.
     */
    CAN_BE_FOLLOWED,

    /**
     * Ability to listen.
     */
    LISTEN_ABILITY,

    /**
     * Ability to move.
     */
    MOVE_ABILITY,

    /**
     * Ability to consume a consumable.
     */
    CONSUME,

    /**
     * Ability to purchase.
     */
    PURCHASE_ABILITY,

    /**
     * Ability to craft.
     */
    CRAFT_ABILITY,

    /**
     * Ability to teleport.
     */
    TELEPORT_ABILITY,

    /**
     * Ability to sell.
     */
    SELL_ABILITY
}