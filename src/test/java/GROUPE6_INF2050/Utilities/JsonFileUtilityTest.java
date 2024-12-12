package GROUPE6_INF2050.Utilities;


import GROUPE6_INF2050.Exceptions.Groupe6INF2050Exception;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class JsonFileUtilityTest {
    private static final String INPUT_FILE = "src/test/java/resources/JsonFileUtilityTest/inputFileTest_Default.json";
    private static final String OUTPUT_FILE = "src/test/java/resources/outputFileTest.json";
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