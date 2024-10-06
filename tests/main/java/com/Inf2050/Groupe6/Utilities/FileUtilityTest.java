package main.java.com.Inf2050.Groupe6.Utilities;

import main.java.com.Inf2050.Groupe6.Exceptions.Groupe6INF2050Exception;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilityTest {

    private final String inputFile = "input_test.json";
    private final String outputFile = "output_test.json";

    @Test
    void testConstructor() {
        FileUtility fileUtility = new FileUtility(inputFile, outputFile) {
            @Override
            public void loadAndValid() throws Groupe6INF2050Exception {
            }
        };
        assertEquals(inputFile, fileUtility.inputFile);
        assertEquals(outputFile, fileUtility.outputFile);
    }


}