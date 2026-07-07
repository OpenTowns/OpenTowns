package xaos.utils;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Log.errorLogFile is the pure decision behind where LEVEL_ERROR appends
 * error.log: at the app home (towns.home, set by the packaged launcher to
 * $APPDIR) when present, otherwise relative to the working directory exactly
 * as before. A packaged macOS launcher's working directory is "/"
 * (JDK-8306974), so the old bare relative path made the crash log unwritable
 * there. No filesystem access is needed here; the method only builds a File.
 */
class LogPathTest {

    @Test
    void unsetHomeKeepsTheOldWorkingDirectoryRelativePath() {
        // gradlew run / test / runHeadless never set towns.home: the resolved
        // path must be exactly the bare relative name, byte-for-byte.
        assertEquals(new File("error.log"), Log.errorLogFile("", "error.log"));
        assertEquals("error.log", Log.errorLogFile("", "error.log").getPath());
    }

    @Test
    void nullOrBlankHomeAlsoKeepsTheRelativePath() {
        assertEquals(new File("error.log"), Log.errorLogFile(null, "error.log"));
        assertEquals(new File("error.log"), Log.errorLogFile("   ", "error.log"));
    }

    @Test
    void setHomeRootsTheLogAtTheAppHome() {
        assertEquals(new File("/Apps/Towns.app/Contents/app", "error.log"),
                Log.errorLogFile("/Apps/Towns.app/Contents/app", "error.log"));
    }

    @Test
    void homeIsTrimmedLikeTheOtherTownsHomeReaders() {
        // FirstRunSetup.dataDir, LauncherConfig and Towns.getHome all trim the
        // property; the log path must agree with them.
        assertEquals(new File("/opt/towns", "error.log"),
                Log.errorLogFile("  /opt/towns  ", "error.log"));
    }
}
