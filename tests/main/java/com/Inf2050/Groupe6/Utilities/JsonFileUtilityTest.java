package main.java.com.Inf2050.Groupe6.Utilities;

import main.java.com.Inf2050.Groupe6.Exceptions.Groupe6INF2050Exception;
import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class JsonFileUtilityTest {
    private static final String INPUT_FILE = "input_test.json";
    private static final String OUTPUT_FILE = "output_test.json";
    private JsonFileUtility jsonFileUtility;
    private ErrorHandler errorHandler;

    @BeforeEach
    void setUp() {
        jsonFileUtility = new JsonFileUtility(INPUT_FILE, OUTPUT_FILE);
        errorHandler = new ErrorHandler();
    }

    @Test
    void testGetJsonObject(){
        JsonFileUtility jsonFileTreatmentCopy = new JsonFileUtility(INPUT_FILE, OUTPUT_FILE);
        assertEquals(jsonFileTreatmentCopy.getJsonObject(), jsonFileUtility.getJsonObject());
    }

    @Test
    void testLoadAndValidWithValidJson() throws Exception {
        try (FileWriter writer = new FileWriter(INPUT_FILE)) {
            writer.write("{\"key\":\"value\"}");
        }
        assertDoesNotThrow(() -> jsonFileUtility.loadAndValid());
        assertNotNull(jsonFileUtility.getJsonObject());
        assertEquals("value", jsonFileUtility.getJsonObject().getString("key"));
    }

    @Test
    void testLoadAndValidWithInvalidJson() {
        try (FileWriter writer = new FileWriter(INPUT_FILE)) {
            writer.write("{key:value}"); // JSON invalide
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Exception exception = assertThrows(Groupe6INF2050Exception.class, () -> jsonFileUtility.loadAndValid());
        assertEquals("Le fichier JSON n'est pas valide", exception.getMessage());
    }

    @Test
    void testLoadAndValidWithNonExistentFile() {
        jsonFileUtility = new JsonFileUtility("non_existent.json", OUTPUT_FILE);
        Exception exception = assertThrows(Groupe6INF2050Exception.class, () -> jsonFileUtility.loadAndValid());
        assertEquals("Le fichier n'existe pas", exception.getMessage());
    }


    @Test
    void testSaveWithErrors() throws Exception {
        errorHandler.addError("Error 1");
        errorHandler.addError("Error 2");
        jsonFileUtility.save(errorHandler);
        String content = new String(Files.readString(Paths.get(OUTPUT_FILE)));
        assertTrue(content.contains("\"complet\": false"));
        assertTrue(content.contains("Error 1"));
        assertTrue(content.contains("Error 2"));
    }

    @Test
    void testSaveWithoutErrors() throws Exception {
        jsonFileUtility.save(errorHandler);
        String content = new String(Files.readString(Paths.get(OUTPUT_FILE)));
        assertTrue(content.contains("\"complet\": true"));
    }


}