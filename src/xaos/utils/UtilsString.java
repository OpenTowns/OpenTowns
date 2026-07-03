package xaos.utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.StringTokenizer;

import xaos.main.Game;

public final class UtilsString {

    private UtilsString() { /*static utility class*/ }

	// ****************
    // * NUMBER UTILS *
    // ****************
    /**
     * Returns a number from a String
     *
     * @param sNumber
     * @param defaultNumber
     * @return
     */
    public static int getInteger(String sNumber, int defaultNumber) {
        if (sNumber == null || sNumber.trim().length() == 0) {
            return defaultNumber;
        }

        try {
            return Integer.parseInt(sNumber);
        } catch (NumberFormatException nfe) {
            Log.log(Log.LEVEL_ERROR, Messages.getString("Utils.1") + sNumber + Messages.getString("Utils.2") + defaultNumber + "]", "Utils"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        }

        return defaultNumber;
    }

	// ***************
    // * COLOR UTILS *
    // ***************
    /**
     * Returns a java.awt.Color from a String
     *
     * @param sColor Color string (ex: Green or GREEN)
     * @return a java.awt.Color from a String. Color.BLACK if parameter is null
     * or empty
     */
    public static ColorGL getColorFromString(String sColor) {
        if (sColor == null || sColor.length() == 0) {
            return new ColorGL(null);
        }

        sColor = sColor.toUpperCase();
        if (sColor.equals("GREEN")) { //$NON-NLS-1$
            return new ColorGL(Color.GREEN);
        } else if (sColor.equals("DARK_GREEN")) { //$NON-NLS-1$
            return new ColorGL(Color.GREEN.darker());
        } else if (sColor.equals("BLACK")) { //$NON-NLS-1$
            return new ColorGL(Color.BLACK);
        } else if (sColor.equals("BLUE")) { //$NON-NLS-1$
            return new ColorGL(Color.BLUE);
        } else if (sColor.equals("WHITE")) { //$NON-NLS-1$
            return new ColorGL(Color.WHITE);
        } else if (sColor.equals("ORANGE")) { //$NON-NLS-1$
            return new ColorGL(Color.ORANGE);
        } else if (sColor.equals("PINK")) { //$NON-NLS-1$
            return new ColorGL(Color.PINK);
        } else if (sColor.equals("YELLOW")) { //$NON-NLS-1$
            return new ColorGL(Color.YELLOW);
        } else if (sColor.equals("GRAY")) { //$NON-NLS-1$
            return new ColorGL(Color.GRAY);
        } else if (sColor.equals("LIGHT_GRAY")) { //$NON-NLS-1$
            return new ColorGL(Color.LIGHT_GRAY);
        } else if (sColor.equals("DARK_GRAY")) { //$NON-NLS-1$
            return new ColorGL(Color.DARK_GRAY);
        } else if (sColor.equals("RED")) { //$NON-NLS-1$
            return new ColorGL(Color.RED);
        } else if (sColor.equals("BROWN")) { //$NON-NLS-1$
            return new ColorGL(new Color(0.5f, 0.25f, 0f));
        } else {
            // Sacamos los 3 colores
            int r = 0, g = 0, b = 0;
            boolean coloresOK = false;
            try {
                StringTokenizer tokenizer = new StringTokenizer(sColor, ","); //$NON-NLS-1$
                if (tokenizer.hasMoreTokens()) {
                    r = Integer.parseInt(tokenizer.nextToken());
                    if (tokenizer.hasMoreTokens()) {
                        g = Integer.parseInt(tokenizer.nextToken());
                        if (tokenizer.hasMoreTokens()) {
                            b = Integer.parseInt(tokenizer.nextToken());
                            coloresOK = true;
                        }
                    }
                }

                if (coloresOK) {
                    return new ColorGL(new Color(r, g, b));
                } else {
                    Log.log(Log.LEVEL_ERROR, Messages.getString("Utils.6") + sColor + "]", "Utils"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                    return new ColorGL(null);
                }
            } catch (Exception e) {
                Log.log(Log.LEVEL_ERROR, Messages.getString("Utils.6") + sColor + "] [" + e.toString() + "]", "Utils"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
                return new ColorGL(null);
            }
        }
    }

	// ***************
    // * STRING UTILS *
    // ***************
    /**
     * Retorna una cadena haciendo cambios dinámicos (sustituyendo ciertas
     * cadenas por otras)
     */
    public static String getDynamicString(String sString) {
        String sAux;
        int iIndex = sString.indexOf("__MUSIC__"); //$NON-NLS-1$

        if (iIndex != -1) {
            // Música ON?
            String sMusicON = Game.isMusicON() ? Messages.getString("Utils.4") : Messages.getString("Utils.5"); //$NON-NLS-1$ //$NON-NLS-2$
            sAux = sString.substring(0, iIndex) + sMusicON + sString.substring(iIndex + "__MUSIC__".length()); //$NON-NLS-1$
            return getDynamicString(sAux);
        } else {
            iIndex = sString.indexOf("__FX__"); //$NON-NLS-1$
            if (iIndex != -1) {
                // FX ON?
                String sFXON = Game.isFXON() ? Messages.getString("Utils.4") : Messages.getString("Utils.5"); //$NON-NLS-1$ //$NON-NLS-2$
                sAux = sString.substring(0, iIndex) + sFXON + sString.substring(iIndex + "__FX__".length()); //$NON-NLS-1$
                return getDynamicString(sAux);
            } else {
                iIndex = sString.indexOf("__MOUSE_SCROLL__"); //$NON-NLS-1$
                if (iIndex != -1) {
                    // Mouse scroll ON?
                    String sMouseScrollON = Game.isMouseScrollON() ? Messages.getString("Utils.4") : Messages.getString("Utils.5"); //$NON-NLS-1$ //$NON-NLS-2$
                    sAux = sString.substring(0, iIndex) + sMouseScrollON + sString.substring(iIndex + "__MOUSE_SCROLL__".length()); //$NON-NLS-1$
                    return getDynamicString(sAux);
                } else {
                    iIndex = sString.indexOf("__MOUSE_SCROLL_EARS__"); //$NON-NLS-1$
                    if (iIndex != -1) {
                        // Mouse scroll EARS ON?
                        String sMouseScrollEarsON = Game.isMouseScrollEarsON() ? Messages.getString("Utils.4") : Messages.getString("Utils.5"); //$NON-NLS-1$ //$NON-NLS-2$
                        sAux = sString.substring(0, iIndex) + sMouseScrollEarsON + sString.substring(iIndex + "__MOUSE_SCROLL_EARS__".length()); //$NON-NLS-1$
                        return getDynamicString(sAux);
                    } else {
                        iIndex = sString.indexOf("__MOUSE_2D_CUBES__"); //$NON-NLS-1$
                        if (iIndex != -1) {
                            String sMouse2DCubes = Game.isMouse2DCubesON() ? Messages.getString("Utils.4") : Messages.getString("Utils.5"); //$NON-NLS-1$ //$NON-NLS-2$
                            sAux = sString.substring(0, iIndex) + sMouse2DCubes + sString.substring(iIndex + "__MOUSE_2D_CUBES__".length()); //$NON-NLS-1$
                            return getDynamicString(sAux);
                        } else {
                            iIndex = sString.indexOf("__DISABLE_ITEMS__"); //$NON-NLS-1$
                            if (iIndex != -1) {
                                // Disabled items ON?
                                String sDisabledItemsON = Game.isDisabledItemsON() ? Messages.getString("Utils.4") : Messages.getString("Utils.5"); //$NON-NLS-1$ //$NON-NLS-2$
                                sAux = sString.substring(0, iIndex) + sDisabledItemsON + sString.substring(iIndex + "__DISABLE_ITEMS__".length()); //$NON-NLS-1$
                                return getDynamicString(sAux);
                            } else {
                                iIndex = sString.indexOf("__DISABLE_GODS__"); //$NON-NLS-1$
                                if (iIndex != -1) {
                                    // Disabled gods ON?
                                    String sDisabledGodsON = Game.isDisabledGodsON() ? Messages.getString("Utils.4") : Messages.getString("Utils.5"); //$NON-NLS-1$ //$NON-NLS-2$
                                    sAux = sString.substring(0, iIndex) + sDisabledGodsON + sString.substring(iIndex + "__DISABLE_GODS__".length()); //$NON-NLS-1$
                                    return getDynamicString(sAux);
                                } else {
                                    iIndex = sString.indexOf("__PAUSE_START__"); //$NON-NLS-1$
                                    if (iIndex != -1) {
                                        // Pause at start ON?
                                        String sPauseStartON = Game.isPauseStartON() ? Messages.getString("Utils.4") : Messages.getString("Utils.5"); //$NON-NLS-1$ //$NON-NLS-2$
                                        sAux = sString.substring(0, iIndex) + sPauseStartON + sString.substring(iIndex + "__PAUSE_START__".length()); //$NON-NLS-1$
                                        return getDynamicString(sAux);
                                    } else {
                                        iIndex = sString.indexOf("__SAVE_DAYS__"); //$NON-NLS-1$
                                        if (iIndex != -1) {
                                            int iDays = Game.getAutosaveDays();
                                            String sAutosave;
                                            if (iDays == 0) {
                                                sAutosave = Messages.getString("Utils.8"); //$NON-NLS-1$
                                            } else if (iDays == 1) {
                                                sAutosave = Messages.getString("Utils.7"); //$NON-NLS-1$
                                            } else {
                                                sAutosave = iDays + Messages.getString("Utils.9"); //$NON-NLS-1$
                                            }
                                            sAux = sString.substring(0, iIndex) + sAutosave + sString.substring(iIndex + "__SAVE_DAYS__".length()); //$NON-NLS-1$
                                            return getDynamicString(sAux);
                                        } else {
                                            iIndex = sString.indexOf("__SIEGES__"); //$NON-NLS-1$
                                            if (iIndex != -1) {
                                                String sSiegeDifficulty;
                                                if (Game.getSiegeDifficulty() == Game.SIEGE_DIFFICULTY_OFF) {
                                                    sSiegeDifficulty = Messages.getString("Utils.8"); //$NON-NLS-1$
                                                } else if (Game.getSiegeDifficulty() == Game.SIEGE_DIFFICULTY_EASY) {
                                                    sSiegeDifficulty = Messages.getString("Utils.11"); //$NON-NLS-1$
                                                } else if (Game.getSiegeDifficulty() == Game.SIEGE_DIFFICULTY_NORMAL) {
                                                    sSiegeDifficulty = Messages.getString("Utils.12"); //$NON-NLS-1$
                                                } else if (Game.getSiegeDifficulty() == Game.SIEGE_DIFFICULTY_HARD) {
                                                    sSiegeDifficulty = Messages.getString("Utils.14"); //$NON-NLS-1$
                                                } else if (Game.getSiegeDifficulty() == Game.SIEGE_DIFFICULTY_HARDER) {
                                                    sSiegeDifficulty = Messages.getString("Utils.16"); //$NON-NLS-1$
                                                } else {
                                                    sSiegeDifficulty = Messages.getString("Utils.15"); //$NON-NLS-1$
                                                }
                                                sAux = sString.substring(0, iIndex) + sSiegeDifficulty + sString.substring(iIndex + "__SIEGES__".length()); //$NON-NLS-1$
                                                return getDynamicString(sAux);
                                            } else {
                                                iIndex = sString.indexOf("__SIEGE_PAUSE__"); //$NON-NLS-1$
                                                if (iIndex != -1) {
                                                    String sSiegePauseON = Game.isSiegePause() ? Messages.getString("Utils.4") : Messages.getString("Utils.5"); //$NON-NLS-1$ //$NON-NLS-2$
                                                    sAux = sString.substring(0, iIndex) + sSiegePauseON + sString.substring(iIndex + "__SIEGE_PAUSE__".length()); //$NON-NLS-1$
                                                    return getDynamicString(sAux);
                                                } else {
                                                    iIndex = sString.indexOf("__CARAVAN_PAUSE__"); //$NON-NLS-1$
                                                    if (iIndex != -1) {
                                                        String sCaravanPauseON = Game.isCaravanPause() ? Messages.getString("Utils.4") : Messages.getString("Utils.5"); //$NON-NLS-1$ //$NON-NLS-2$
                                                        sAux = sString.substring(0, iIndex) + sCaravanPauseON + sString.substring(iIndex + "__CARAVAN_PAUSE__".length()); //$NON-NLS-1$
                                                        return getDynamicString(sAux);
                                                    } else {
                                                        iIndex = sString.indexOf("__VOLMUSIC__"); //$NON-NLS-1$
                                                        if (iIndex != -1) {
                                                            String sVolMusic = (Game.getVolumeMusic() * 10) + "%"; //$NON-NLS-1$
                                                            sAux = sString.substring(0, iIndex) + sVolMusic + sString.substring(iIndex + "__VOLMUSIC__".length()); //$NON-NLS-1$
                                                            return getDynamicString(sAux);
                                                        } else {
                                                            iIndex = sString.indexOf("__VOLFX__"); //$NON-NLS-1$
                                                            if (iIndex != -1) {
                                                                String sVolFX = (Game.getVolumeFX() * 10) + "%"; //$NON-NLS-1$
                                                                sAux = sString.substring(0, iIndex) + sVolFX + sString.substring(iIndex + "__VOLFX__".length()); //$NON-NLS-1$
                                                                return getDynamicString(sAux);
                                                            } else {
                                                                iIndex = sString.indexOf("__MOD__"); //$NON-NLS-1$
                                                                if (iIndex != -1) {
                                                                    // Mod, miramos el nombre
                                                                    String sSubStr = sString.substring(iIndex);
                                                                    int iIndexEndMod = sSubStr.indexOf("__/MOD__"); //$NON-NLS-1$
                                                                    if (iIndexEndMod != -1) {
                                                                        // Tenemos mod y /mod
                                                                        String sModName = sSubStr.substring("__MOD__".length(), iIndexEndMod); //$NON-NLS-1$
                                                                        String sModON = Game.isModLoaded(sModName) ? Messages.getString("Utils.4") : Messages.getString("Utils.5"); //$NON-NLS-1$ //$NON-NLS-2$
                                                                        sAux = sString.substring(0, iIndex) + sModON + sSubStr.substring(iIndexEndMod + "__/MOD__".length()); //$NON-NLS-1$
                                                                    } else {
                                                                        // Raro, raro, borramos el __MOD__ para que siga mirando dynamic strings
                                                                        sAux = sString.substring(0, iIndex) + sString.substring(iIndex + "__MOD__".length()); //$NON-NLS-1$
                                                                    }
                                                                    return getDynamicString(sAux);
                                                                } else {
                                                                    iIndex = sString.indexOf("__BURY__"); //$NON-NLS-1$
                                                                    if (iIndex != -1) {
                                                                        String sBury = Game.isAllowBury() ? Messages.getString("Utils.4") : Messages.getString("Utils.5"); //$NON-NLS-1$ //$NON-NLS-2$
                                                                        sAux = sString.substring(0, iIndex) + sBury + sString.substring(iIndex + "__BURY__".length()); //$NON-NLS-1$
                                                                        return getDynamicString(sAux);
                                                                    } else {
                                                                        iIndex = sString.indexOf("__CPUPF__"); //$NON-NLS-1$
                                                                        if (iIndex != -1) {
                                                                            String sLevel = Integer.toString(Game.getPathfindingCPULevel());
                                                                            sAux = sString.substring(0, iIndex) + sLevel + sString.substring(iIndex + "__CPUPF__".length()); //$NON-NLS-1$
                                                                            return getDynamicString(sAux);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return sString;
    }

    /**
     * Devuelve un array de strings a partir de un string con elementos
     * separados por comas ","
     *
     * @param sChain Cadena
     * @return un array de strings a partir de un string con elementos separados
     * por comas ",". Null en caso de error/cadena vacía
     */
    public static ArrayList<String> getArray(String sChain) {
        if (sChain != null && sChain.trim().length() > 0) {
            ArrayList<String> alReturn = new ArrayList<String>();
            StringTokenizer tokenizer = new StringTokenizer(sChain.trim(), ","); //$NON-NLS-1$
            while (tokenizer.hasMoreElements()) {
                alReturn.add(tokenizer.nextToken());
            }

            return alReturn;
        }

        return null;
    }

    /**
     * Devuelve un array de Integers a partir de un string con elementos
     * separados por comas ","
     *
     * @param sChain Cadena
     * @return un array de Integers a partir de un string con elementos
     * separados por comas ",". Null en caso de error/cadena vacía
     */
    public static ArrayList<Integer> getArrayIntegers(String sChain) throws Exception {
        if (sChain != null && sChain.trim().length() > 0) {
            ArrayList<Integer> alReturn = new ArrayList<Integer>();
            StringTokenizer tokenizer = new StringTokenizer(sChain.trim(), ","); //$NON-NLS-1$
            while (tokenizer.hasMoreElements()) {
                String sToken = tokenizer.nextToken();
                try {
                    alReturn.add(Integer.valueOf(sToken));
                } catch (Exception e) {
                    throw new Exception(Messages.getString("Utils.22") + tokenizer.nextToken() + Messages.getString("Utils.24")); //$NON-NLS-1$ //$NON-NLS-2$
                }
            }

            return alReturn;
        }

        return null;
    }

}
