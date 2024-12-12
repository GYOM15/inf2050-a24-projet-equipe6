package GROUPE6_INF2050.Handlers;

import GROUPE6_INF2050.Exceptions.Groupe6INF2050Exception;
import GROUPE6_INF2050.Reporting.StatisticsData;
import GROUPE6_INF2050.Utilities.StatisticsFileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationRunnerTest {
    private static final String INPUT_FILE = "src/test/java/resources/ApplicationRunnerTest/inputFileTestApplicationRunner.json";
    private static final String OUTPUT_FILE = "src/test/java/resources/outputFileTest.json";
    private static final String STATISTIC_FILE = "src/test/java/resources/statisticsTest.json";
    private ApplicationRunner applicationRunner;

    @BeforeEach
    void setUp() {
        applicationRunner = new ApplicationRunner();
        StatisticsFileManager statisticsFileManager = new StatisticsFileManager(STATISTIC_FILE);
    }

    @Test
    void testValidateArguments_ValidSingleOption() {
        String[] args = {"-S"};
        assertDoesNotThrow(() -> applicationRunner.run(args));
    }

    @Test
    void testValidateArguments_InvalidSingleOption() {
        String[] args = {"-INVALID"};
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> applicationRunner.run(args));
        assertTrue(exception.getMessage().contains("Option invalide"));
    }

    @Test
    void testValidateArguments_ValidFileArguments() {
        String[] args = {INPUT_FILE, OUTPUT_FILE};
        assertDoesNotThrow(() -> applicationRunner.run(args));
    }

    @Test
    void testValidateArguments_InvalidNumberOfArguments() {
        String[] args = {INPUT_FILE};
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> applicationRunner.run(args));
        assertTrue(exception.getMessage().contains("Option invalide. Utilisez '-S' ou '-SR' uniquement lorsque vous fournissez un seul argument."));
    }

    @Test
    void testProcessInputFile_JsonFile() throws IOException {
        String[] args = {INPUT_FILE, OUTPUT_FILE};
        assertDoesNotThrow(() -> {
            applicationRunner.run(args);
        });
    }

    @Test
    void testFinalizeActions_SaveStatistics() throws IOException {
        String[] args = {"-S"};
        StatisticsFileManager statisticsFileManager = new StatisticsFileManager(STATISTIC_FILE);
        applicationRunner.run(args);
        assertNotNull(statisticsFileManager.loadStatistics());
    }

    @Test
    void testFinalizeActions_ResetStatistics() throws IOException {
        String[] args = {"-SR"};
        StatisticsFileManager mockStatisticsFileManager = new StatisticsFileManager("src/test/java/resources/ApplicationRunnerTest/statisticsTest.json");
        StatisticsData statisticsData = new StatisticsData();
        statisticsData.incrementInvalidPermitDeclarations(5);
        applicationRunner.run(args);
        StatisticsData updatedStatistics = mockStatisticsFileManager.loadStatistics();
        assertEquals(0, updatedStatistics.getInvalidPermitDeclarations());
    }

    @Test
    void testFinalizeActions_DisplayStatistics() throws IOException {
        String[] args = {"-S"};
        StatisticsFileManager mockStatisticsFileManager = new StatisticsFileManager("src/test/java/resources/ApplicationRunnerTest/statisticsTest.json");
        StatisticsData statisticsData = mockStatisticsFileManager.loadStatistics();
        statisticsData.incrementIncompleteOrInvalidDeclarations(2);
        applicationRunner.run(args);
        assertNotNull(statisticsData, "Les statistiques doivent être affichées.");
        assertEquals(2, statisticsData.getIncompleteOrInvalidDeclarations());
    }
}