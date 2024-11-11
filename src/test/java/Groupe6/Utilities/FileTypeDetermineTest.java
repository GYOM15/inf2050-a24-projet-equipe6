package Groupe6.Utilities;

import org.example.Inf2050.Groupe6.Utilities.FileTypeDetermine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class FileTypeDetermineTest {

    private FileTypeDetermine fileTypeDetermine;

    @BeforeEach
    void setUp(){
        fileTypeDetermine = new FileTypeDetermine();
    }

    @Test
    void determineFileTypeSuccess()throws IOException {
        File tempFile = File.createTempFile("test", ".json");
        tempFile.deleteOnExit();
        String jsonContent = "{\"key\": \"value\"}";
        Files.writeString(tempFile.toPath(), jsonContent);
        String fileType = fileTypeDetermine.determineFileType(tempFile.getAbsolutePath());
        assertEquals("application/json", fileType);
    }

    @Test
    void determineFileTypeFailed(){
        String nonExistentFile = "non_existent_file.xyz";
        Exception exception = assertThrows(IOException.class, () -> {
            fileTypeDetermine.determineFileType(nonExistentFile);
        });
        assertEquals("Impossible de déterminer le type du fichier.", exception.getMessage());
    }

}