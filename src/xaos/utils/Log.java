package xaos.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.Calendar;

public final class Log {

    public final static int LEVEL_DEBUG = 1;
    public final static int LEVEL_ERROR = 2;

    public static void log(int type, String message, String sClass) {
        Calendar cal = Calendar.getInstance();
        DateFormat df = DateFormat.getDateTimeInstance();
        StringBuffer sMessage = new StringBuffer();
        if (df != null) {
            sMessage.append("["); //$NON-NLS-1$
            sMessage.append(df.format(cal.getTime()));
            sMessage.append("]"); //$NON-NLS-1$
        }
        sMessage.append("["); //$NON-NLS-1$
        sMessage.append(sClass);
        sMessage.append("] "); //$NON-NLS-1$
        sMessage.append(message);

        switch (type) {
            case LEVEL_DEBUG:
                System.out.println(sMessage.toString());
                break;
            case LEVEL_ERROR:
                System.err.println(sMessage.toString());
                File f = errorLogFile(System.getProperty("towns.home", ""), "error.log"); //$NON-NLS-1$ //$NON-NLS-2$
                try {
                    FileOutputStream fos = new FileOutputStream(f, true);
                    fos.write(("\r\n" + sMessage.toString()).getBytes()); //$NON-NLS-1$
                    fos.close();
                } catch (Exception e) {
                    System.err.println(Messages.getString("Log.4")); //$NON-NLS-1$
                }
                break;
            default:
        }
    }

    /**
     * Where the error log goes: rooted at the app home (towns.home, set by the
     * packaged launcher to $APPDIR) when present, otherwise relative to the
     * working directory as before. Read straight from the system property, not
     * via Towns.resolveHome, to keep this class free of game-package imports
     * (xaos.setup and xaos.launcher use xaos.utils and stay decoupled from the
     * game packages).
     */
    static File errorLogFile(String home, String fileName) {
        if (home == null || home.trim().isEmpty()) {
            return new File(fileName);
        }
        return new File(home.trim(), fileName);
    }
}
