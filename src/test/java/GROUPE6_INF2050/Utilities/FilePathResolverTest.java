package GROUPE6_INF2050.Utilities;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class FilePathResolverTest {
    @Test
    public void testResolvePath_FileDoesNotExist() {
        // Tenter de résoudre un chemin qui n'existe pas
        String nonExistentFile = "nonExistentFile.json";

        // Vérifier que l'exception est lancée
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            FilePathResolver.resolvePath(nonExistentFile);
        });

        assertEquals("Unable to locate the resource: " + nonExistentFile, exception.getMessage());
    }

    @Test
    public void testResolvePath_FileExistsInParentDirectory() throws IOException {
        Path tempDir = Files.createTempDirectory("testDir");

        Path tempFile = Files.createTempFile(tempDir, "testFile", ".json");

        File movedFile = new File(System.getProperty("user.dir"), tempFile.getFileName().toString());
        Files.move(tempFile, movedFile.toPath());

        String resolvedPath = FilePathResolver.resolvePath(movedFile.getName());
        assertEquals(movedFile.getAbsolutePath(), resolvedPath);

        Files.delete(movedFile.toPath());
        Files.delete(tempDir);
    }
}