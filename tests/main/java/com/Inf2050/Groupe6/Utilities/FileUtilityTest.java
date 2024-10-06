package main.java.com.Inf2050.Groupe6.Utilities;

import main.java.com.Inf2050.Groupe6.Exceptions.Groupe6INF2050Exception;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilityTest {
    @Test
    void testConstructor() {
        String inputFile = "input_test.json";
        String outputFile = "output_test.json";

        // Création d'une instance de FileTreatment avec une sous-classe anonyme
        FileUtility fileUtility = new FileUtility(inputFile, outputFile) {
            @Override
            public void loadAndValid() throws Groupe6INF2050Exception {
            }
        };
        // Vérifiez que les attributs sont correctement initialisés
        assertEquals(inputFile, fileUtility.inputFile);
        assertEquals(outputFile, fileUtility.outputFile);
    }


}