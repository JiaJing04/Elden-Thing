package game.attributes;

/**
 * Use this enum class to represent a status.
 * Example #1: if the player is sleeping, you can attack a Status.SLEEP to the player class
 * @author Riordan D. Alfredo
 * Modified by: hewjiajing
 */
public enum Status
{
    /**
     * Actor is hostile towards enemies.
     */
    HOSTILE_TO_ENEMY,

    /**
     * Object is cursed.
     */
    CURSED,

    /**
     * Free to plant seeds.
     */
    FREE_TO_PLANT,

    /**
     * Entity is blessed.
     */
    BLESSED,

    /**
     * Entity is pet edible.
     */
    PET_EDIBLE,

    /**
     * Entity is crimson.
     */
    CRIMSON,

    /**
     * Entity is crimson end.
     */
    CRIMSON_END,

    /**
     * Entity is cannot be attacked.
     */
    CANNOT_BE_ATTACKED,

    /**
     * Represents the Duskfall phase in the game's phase cycle.
     */
    DUSKFALL_PHASE,

    /**
     * Represents the Dawnveil phase in the game's phase cycle.
     */
    DAWNVEIL_PHASE,

    /**
     * Represents the Crimson Eclipse phase in the game's phase cycle.
     */
    CRIMSON_ECLIPSE_PHASE
}
