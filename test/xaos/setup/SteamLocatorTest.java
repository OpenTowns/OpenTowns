package xaos.setup;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SteamLocatorTest {

    @Test
    void parsesRealisticWindowsLibraryFoldersVdf() {
        List<String> lines = List.of(
                "\"libraryfolders\"",
                "{",
                "\t\"0\"",
                "\t{",
                "\t\t\"path\"\t\t\"C:\\\\Program Files (x86)\\\\Steam\"",
                "\t\t\"label\"\t\t\"\"",
                "\t\t\"contentid\"\t\t\"1234567890\"",
                "\t\t\"apps\"",
                "\t\t{",
                "\t\t\t\"221020\"\t\t\"987654321\"",
                "\t\t}",
                "\t}",
                "\t\"1\"",
                "\t{",
                "\t\t\"path\"\t\t\"D:\\\\SteamLibrary\"",
                "\t}",
                "}");

        assertEquals(
                List.of(Path.of("C:\\Program Files (x86)\\Steam"), Path.of("D:\\SteamLibrary")),
                SteamLocator.parseLibraryFoldersVdf(lines));
    }

    @Test
    void parseIgnoresGarbageAndEmptyInput() {
        assertTrue(SteamLocator.parseLibraryFoldersVdf(List.of()).isEmpty());
        assertTrue(SteamLocator.parseLibraryFoldersVdf(
                List.of("not a vdf at all", "\"appid\"  \"221020\"", "{}")).isEmpty());
    }

    @Test
    void windowsRootCandidates() {
        Path home = Path.of("C:", "Users", "someone");
        assertEquals(
                List.of(
                        Path.of("C:", "Program Files (x86)", "Steam"),
                        Path.of("C:", "Program Files", "Steam")),
                SteamLocator.steamRootsFor("Windows 11", home));
    }

    @Test
    void macRootCandidates() {
        Path home = Path.of("/Users/someone");
        assertEquals(
                List.of(home.resolve("Library").resolve("Application Support").resolve("Steam")),
                SteamLocator.steamRootsFor("Mac OS X", home));
    }

    @Test
    void linuxRootCandidates() {
        Path home = Path.of("/home/someone");
        assertEquals(
                List.of(
                        home.resolve(".steam").resolve("steam"),
                        home.resolve(".local").resolve("share").resolve("Steam"),
                        home.resolve(".var").resolve("app").resolve("com.valvesoftware.Steam")
                                .resolve(".local").resolve("share").resolve("Steam")),
                SteamLocator.steamRootsFor("Linux", home));
    }
}
