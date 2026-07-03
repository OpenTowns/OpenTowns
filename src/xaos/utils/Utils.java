package xaos.utils;

import java.awt.Point;
import java.util.Random;

public final class Utils {

    public static Random random = new Random();

    private Utils() { /*static utility class*/ }

    /**
     * Replaces the game RNG with a seeded one (deterministic test mode).
     * Every random draw in the game goes through this single instance, so
     * seeding it before worldgen fixes the whole run. The shipped game never
     * calls this and stays non-deterministic.
     */
    public static void setRandomSeed(long seed) {
        random = new Random(seed);
    }

    // **DICE UTILS**
    // The dice bodies live in UtilsDice (drawing from the single RNG above);
    // these delegators keep the existing call sites unchanged.
    public static int getRandomBetween(int iMin, int iMax) {
        return UtilsDice.getRandomBetween(iMin, iMax);
    }

    public static int launchDice(int iNumber, int iSides) {
        return UtilsDice.launchDice(iNumber, iSides);
    }

    public static int launchDice(int iNumber, int iSides, int add) {
        return UtilsDice.launchDice(iNumber, iSides, add);
    }

    public static int launchDice(String str) {
        return UtilsDice.launchDice(str);
    }

    public static Point getDiceMinMax(String str) {
        return UtilsDice.getDiceMinMax(str);
    }

}
