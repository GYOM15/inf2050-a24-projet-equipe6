package main.java.com.Inf2050.Groupe6.Utilities;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class FileTypeDetermineTest {

    public final FileTypeDetermine fileTypeDetermine = new FileTypeDetermine();

    @Test
    void determineFileTypeSuccess()throws IOException {
        // Crée un fichier temporaire avec une extension .json
        File tempFile = File.createTempFile("test", ".json");
        tempFile.deleteOnExit(); // Assurer la suppression à la fin

        // Écrit un contenu JSON dans le fichier
        String jsonContent = "{\"key\": \"value\"}";
        Files.writeString(tempFile.toPath(), jsonContent);

        // Exécution du test
        String fileType = fileTypeDetermine.determineFileType(tempFile.getAbsolutePath());

        // Vérification
        assertEquals("application/json", fileType);
    }

    @Test
    void determineFileTypeFailed(){
        //FileTypeDetermine fileTypeDetermine = new FileTypeDetermine();
        String nonExistentFile = "non_existent_file.xyz";
        Exception exception = assertThrows(IOException.class, () -> {
            fileTypeDetermine.determineFileType(nonExistentFile);
        });
        assertEquals("Impossible de déterminer le type du fichier.", exception.getMessage());
    }

}