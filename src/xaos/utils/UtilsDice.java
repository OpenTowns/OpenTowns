package xaos.utils;

import java.awt.Point;
import java.util.StringTokenizer;

public final class UtilsDice {

    private UtilsDice() { /*static utility class*/ }

	// **************
    // * DICE UTILS *
    // **************
    /**
     * Returns a random number between the 2 params
     *
     * @param iMin Min
     * @param iMax Max
     * @return a random number between the 2 params. If iMin > iMax then return
     * iMin
     */
    public static int getRandomBetween(int iMin, int iMax) {
        if (iMin >= iMax) {
            return iMin;
        }

        return Utils.random.nextInt(iMax - iMin + 1) + iMin;
    }

    /**
     * Launch a dice of N sides X times and return the result
     *
     * @param iNumber Number of times the dice was launched
     * @param iSides Number of sides of the dice
     * @return
     */
    public static int launchDice(int iNumber, int iSides) {
        return launchDice(iNumber, iSides, 0);
    }

    /**
     * Launch a dice of N sides X times and then adds a qtty
     *
     * @param iNumber Number of times the dice was launched
     * @param iSides Number of sides of the dice
     * @param add Number to add (or substract if negative) to the end result
     * @return
     */
    public static int launchDice(int iNumber, int iSides, int add) {
        int result = add;

        for (int i = 0; i < iNumber; i++) {
            result += getRandomBetween(1, iSides);
        }

        return result;
    }

    /**
     * Launch N dices taking a String as an input ex: 4d8+34 (this wil launch 4
     * times a dice of 8 sides, then addes 34 to the result
     *
     * Input string can be passed with commas ',' Ex: 2d8,3d6+1,1d3 (lauch 2
     * dices of 8 sides, then 3 dices of 6 sides, then adds 1, then launch 1
     * dice of 3 sides
     *
     * @param str
     * @return
     */
    public static int launchDice(String str) {
        if (str == null || str.trim().length() == 0) {
            return 0;
        }

        int result = 0;
        String sStr = str.toUpperCase();

        if (sStr.indexOf(',') == -1) {
            // No tokens, just 1 string
            int iIndexD = sStr.indexOf('D');
            if (iIndexD != -1) {
                try {
                    int iNumber = Integer.parseInt(sStr.substring(0, iIndexD).trim());
                    // Buscamos si hay un "+"
                    int iIndexPlus = sStr.indexOf('+');
                    if (iIndexPlus == -1) {
                        // No hay '+', miramos si hay un "-"
                        iIndexPlus = sStr.indexOf('-');
                        if (iIndexPlus == -1) {
                            return launchDice(iNumber, Integer.parseInt(sStr.substring(iIndexD + 1).trim()));
                        } else {
                            return launchDice(iNumber, Integer.parseInt(sStr.substring(iIndexD + 1, (iIndexPlus + 1 - iIndexD)).trim()), -Integer.parseInt(sStr.substring(iIndexPlus + 1)));
                        }
                    } else {
                        return launchDice(iNumber, Integer.parseInt(sStr.substring(iIndexD + 1, (iIndexPlus + 1 - iIndexD)).trim()), Integer.parseInt(sStr.substring(iIndexPlus + 1)));
                    }
                } catch (Exception e) {
                    Log.log(Log.LEVEL_ERROR, Messages.getString("Utils.0") + sStr + "] [" + e.toString() + "]", "Utils"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
                    return 0;
                }
            } else {
                try {
                    // No existe la 'D', quizá es un +X o -X directo
                    if (sStr.charAt(0) == '-') {
                        return launchDice(0, 0, -Integer.parseInt(sStr.substring(1)));
                    } else {
                        if (sStr.charAt(0) == '+') {
                            return launchDice(0, 0, Integer.parseInt(sStr.substring(1)));
                        } else {
                            return launchDice(0, 0, Integer.parseInt(sStr.substring(0)));
                        }
                    }
                } catch (Exception e) {
                    Log.log(Log.LEVEL_ERROR, Messages.getString("Utils.0") + sStr + "] [" + e.toString() + "]", "Utils"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
                    return 0;
                }
            }
        } else {
            // Recorremos los tokens y vamos sumando, llamándose a si misma
            StringTokenizer tokenizer = new StringTokenizer(sStr, ","); //$NON-NLS-1$
            String token;
            while (tokenizer.hasMoreTokens()) {
                token = tokenizer.nextToken();
                result += launchDice(token);
            }
        }

        return result;
    }

    /**
     * Returns the minimum and maximum values of a dice String
     *
     * @param str
     * @return the minimum and maximum values of a dice String
     */
    public static Point getDiceMinMax(String str) {
        if (str == null || str.trim().length() == 0) {
            return new Point(0, 0);
        }

        Point result = new Point(0, 0);
        String sStr = str.toUpperCase();

        if (sStr.indexOf(',') == -1) {
            // No tokens, just 1 string
            int iIndexD = sStr.indexOf('D');
            if (iIndexD != -1) {
                try {
                    int iNumber = Integer.parseInt(sStr.substring(0, iIndexD).trim());
                    // Buscamos si hay un "+"
                    int iIndexPlus = sStr.indexOf('+');
                    if (iIndexPlus == -1) {
                        // No hay '+', miramos si hay un "-"
                        iIndexPlus = sStr.indexOf('-');
                        if (iIndexPlus == -1) {
                            return new Point(iNumber, iNumber * Integer.parseInt(sStr.substring(iIndexD + 1).trim()));
                        } else {
                            int iMinus = Integer.parseInt(sStr.substring(iIndexPlus + 1));
                            return new Point(iNumber - iMinus, (iNumber * Integer.parseInt(sStr.substring(iIndexD + 1, (iIndexPlus + 1 - iIndexD)).trim())) - iMinus);
                        }
                    } else {
                        int iAdd = Integer.parseInt(sStr.substring(iIndexPlus + 1));
                        return new Point(iNumber + iAdd, (iNumber * Integer.parseInt(sStr.substring(iIndexD + 1, (iIndexPlus + 1 - iIndexD)).trim())) + iAdd);
                    }
                } catch (Exception e) {
                    return new Point(0, 0);
                }
            } else {
                try {
                    // No existe la 'D', quizá es un +X o -X directo
                    if (sStr.charAt(0) == '-') {
                        int iMinus = -Integer.parseInt(sStr.substring(1));
                        return new Point(iMinus, iMinus);
                    } else {
                        if (sStr.charAt(0) == '+') {
                            int iAdd = Integer.parseInt(sStr.substring(1));
                            return new Point(iAdd, iAdd);
                        } else {
                            int iAdd = Integer.parseInt(sStr.substring(0));
                            return new Point(iAdd, iAdd);
                        }
                    }
                } catch (Exception e) {
                    Log.log(Log.LEVEL_ERROR, Messages.getString("Utils.3") + sStr + "] [" + e.toString() + "]", "Utils"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
                    return new Point(0, 0);
                }
            }
        } else {
            // Recorremos los tokens y vamos sumando, llamándose a si misma
            StringTokenizer tokenizer = new StringTokenizer(sStr, ","); //$NON-NLS-1$
            String token;
            Point pointTmp;
            while (tokenizer.hasMoreTokens()) {
                token = tokenizer.nextToken();
                pointTmp = getDiceMinMax(token);
                result.x += pointTmp.x;
                result.y += pointTmp.y;
            }
        }

        return result;
    }

}
