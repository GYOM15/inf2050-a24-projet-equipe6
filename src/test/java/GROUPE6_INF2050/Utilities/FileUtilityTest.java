package GROUPE6_INF2050.Utilities;


import GROUPE6_INF2050.Exceptions.Groupe6INF2050Exception;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileUtilityTest {

    private final String INPUT_FILE = "src/test/java/resources/FileUtilityTest/inputFileTest_Default.json";
    private final String OUTPUT_FILE = "src/test/java/resources/outputFileTest.json";

    @Test
    void testConstructor() {
        FileUtility fileUtility = new FileUtility(INPUT_FILE, OUTPUT_FILE) {
            @Override
            public void loadAndValid() throws Groupe6INF2050Exception {
            }
        };
        assertEquals(INPUT_FILE, fileUtility.inputFile);
        assertEquals(OUTPUT_FILE, fileUtility.outputFile);
    }


}