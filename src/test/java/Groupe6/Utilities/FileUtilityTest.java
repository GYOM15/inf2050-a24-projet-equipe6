package Groupe6.Utilities;

import main.java.com.Inf2050.Groupe6.Exceptions.Groupe6INF2050Exception;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilityTest {

    private final String inputFile = "tests/main/Vendor/inputTest.json";
    private final String outputFile = "tests/main/Vendor/outputTest.json";

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