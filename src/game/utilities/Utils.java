package game.utilities;

import java.util.Random;

/**
 * Class representing utilities methods.
 *
 * @author hewjiajing
 * @version 1.0
 */
public final class Utils
{
    /**
     * This method generates a random number in the range provided.
     *
     * @param number The upper bound (exclusive) for the random number.
     * @return A random integer between 0 (inclusive) and number (exclusive).
     */
    public static int generateRandomNumber(int number)
    {
        Random random = new Random();

        int randomNumber = random.nextInt(number);

        return randomNumber;
    }
}