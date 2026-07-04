package xaos.setup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pure filesystem tests against @TempDir paths; the dialog methods of
 * FirstRunSetup are private and never reached from here.
 */
class FirstRunSetupTest {

    @TempDir
    Path tmp;

    /** Creates a full data tree: graphics (with a nested subfolder), audio, fonts. */
    private Path makeSourceData() throws IOException {
        Path data = tmp.resolve("towns").resolve("data");
        Files.createDirectories(data.resolve("graphics").resolve("sub"));
        Files.createDirectories(data.resolve("audio"));
        Files.createDirectories(data.resolve("fonts"));
        Files.write(data.resolve("graphics").resolve("atlas.png"), new byte[] { 1, 2, 3 });
        Files.write(data.resolve("graphics").resolve("sub").resolve("cursor.png"), new byte[] { 4, 5 });
        Files.write(data.resolve("audio").resolve("fx_click.ogg"), new byte[] { 6 });
        Files.write(data.resolve("fonts").resolve("cantarell_17.fnt"), new byte[] { 7, 8 });
        return data;
    }

    @Test
    void assetsPresentWhenAllThreeFoldersHaveContent() throws IOException {
        Path data = makeSourceData();
        assertTrue(FirstRunSetup.assetsPresent(data));
    }

    @Test
    void assetsMissingWhenOneFolderAbsent() throws IOException {
        Path data = makeSourceData();
        Files.delete(data.resolve("fonts").resolve("cantarell_17.fnt"));
        Files.delete(data.resolve("fonts"));
        assertFalse(FirstRunSetup.assetsPresent(data));
    }

    @Test
    void assetsMissingWhenOneFolderEmpty() throws IOException {
        Path data = makeSourceData();
        Files.delete(data.resolve("audio").resolve("fx_click.ogg"));
        assertFalse(FirstRunSetup.assetsPresent(data));
    }

    @Test
    void validateAcceptsInstallRoot() throws IOException {
        Path data = makeSourceData();
        Path installRoot = data.getParent();
        assertEquals(data, FirstRunSetup.validateSelection(installRoot));
    }

    @Test
    void validateAcceptsDataFolderDirectly() throws IOException {
        Path data = makeSourceData();
        assertEquals(data, FirstRunSetup.validateSelection(data));
    }

    @Test
    void validateRejectsUnrelatedFolderAndNull() throws IOException {
        Path junk = Files.createDirectories(tmp.resolve("junk"));
        assertNull(FirstRunSetup.validateSelection(junk));
        assertNull(FirstRunSetup.validateSelection(null));
    }

    @Test
    void copyCopiesNestedFoldersAndBytes() throws IOException {
        Path source = makeSourceData();
        Path target = tmp.resolve("game").resolve("data");

        FirstRunSetup.copyAssets(source, target);

        assertTrue(FirstRunSetup.assetsPresent(target));
        assertArrayEquals(new byte[] { 1, 2, 3 }, Files.readAllBytes(target.resolve("graphics").resolve("atlas.png")));
        assertArrayEquals(new byte[] { 4, 5 },
                Files.readAllBytes(target.resolve("graphics").resolve("sub").resolve("cursor.png")));
        assertArrayEquals(new byte[] { 6 }, Files.readAllBytes(target.resolve("audio").resolve("fx_click.ogg")));
        assertArrayEquals(new byte[] { 7, 8 },
                Files.readAllBytes(target.resolve("fonts").resolve("cantarell_17.fnt")));
    }

    @Test
    void copySkipsFoldersAlreadyPresent() throws IOException {
        Path source = makeSourceData();
        Path target = tmp.resolve("game").resolve("data");
        Files.createDirectories(target.resolve("graphics"));
        Files.write(target.resolve("graphics").resolve("marker.txt"), new byte[] { 9 });

        FirstRunSetup.copyAssets(source, target);

        // The pre-existing non-empty graphics folder is kept untouched.
        assertTrue(Files.exists(target.resolve("graphics").resolve("marker.txt")));
        assertFalse(Files.exists(target.resolve("graphics").resolve("atlas.png")));
        // The other two folders are still copied.
        assertTrue(Files.exists(target.resolve("audio").resolve("fx_click.ogg")));
        assertTrue(Files.exists(target.resolve("fonts").resolve("cantarell_17.fnt")));
    }

    @Test
    void copyLeavesNoTmpDirsAndCleansStaleOnes() throws IOException {
        Path source = makeSourceData();
        Path target = tmp.resolve("game").resolve("data");
        // Stale leftover from a hypothetical interrupted earlier run.
        Files.createDirectories(target.resolve("graphics.tmp").resolve("partial"));
        Files.write(target.resolve("graphics.tmp").resolve("partial").resolve("junk.png"), new byte[] { 0 });

        FirstRunSetup.copyAssets(source, target);

        assertTrue(FirstRunSetup.assetsPresent(target));
        for (String name : FirstRunSetup.REQUIRED_FOLDERS) {
            assertFalse(Files.exists(target.resolve(name + ".tmp")), name + ".tmp should be gone");
        }
    }

    @Test
    void copyThrowsWhenSourceLacksAFolder() throws IOException {
        Path source = makeSourceData();
        Files.delete(source.resolve("fonts").resolve("cantarell_17.fnt"));
        Files.delete(source.resolve("fonts"));
        Path target = tmp.resolve("game").resolve("data");

        assertThrows(IOException.class, () -> FirstRunSetup.copyAssets(source, target));
        assertFalse(FirstRunSetup.assetsPresent(target));
    }
}
