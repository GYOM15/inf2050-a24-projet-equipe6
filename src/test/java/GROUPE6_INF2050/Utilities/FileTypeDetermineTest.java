package GROUPE6_INF2050.Utilities;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

public class FileTypeDetermineTest {

    @Test
    public void testDetermineFileType_ValidFile() throws IOException {
        File tempFile = File.createTempFile("testFile", ".txt");
        Files.writeString(tempFile.toPath(), "Ceci est un test.");
        FileTypeDetermine fileTypeDetermine = new FileTypeDetermine();
        String fileType = fileTypeDetermine.determineFileType(tempFile.getAbsolutePath());
        assertEquals("text/plain", fileType);
        Files.delete(tempFile.toPath());
    }

    @Test
    public void testDetermineFileType_UnknownFileType() {
        File tempFile = new File("unknownFileType");
        try {
            if (!tempFile.createNewFile()) {
                throw new IOException("Le fichier n'a pas pu être créé.");
            }
            FileTypeDetermine fileTypeDetermine = new FileTypeDetermine();
            IOException exception = assertThrows(IOException.class, () -> {
                fileTypeDetermine.determineFileType(tempFile.getAbsolutePath());
            });

            assertEquals("Impossible de déterminer le type du fichier.", exception.getMessage());
        } catch (IOException e) {
            fail("Une exception inattendue a été lancée : " + e.getMessage());
        } finally {
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
}