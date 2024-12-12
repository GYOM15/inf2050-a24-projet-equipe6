package GROUPE6_INF2050.Utilities;

import GROUPE6_INF2050.Reporting.StatisticsData;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsFileManagerTest {
    private StatisticsFileManager statisticsFileManager;
    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = File.createTempFile("src/test/java/resources/statisticsTest", ".json");
        tempFile.deleteOnExit();
        statisticsFileManager = new StatisticsFileManager(tempFile.getAbsolutePath());
    }

    @AfterEach
    void tearDown() throws IOException {
        if (tempFile.exists()) {
            Files.delete(tempFile.toPath());
        }
    }

    @Test
    void testLoadStatistics_WhenFileDoesNotExist() throws IOException {
        Files.deleteIfExists(tempFile.toPath());
        StatisticsData statisticsData = statisticsFileManager.loadStatistics();
        assertNotNull(statisticsData);
        assertEquals(0, statisticsData.getTotalDeclarations());
        assertTrue(tempFile.exists());
    }

    @Test
    void testSaveAndLoadStatistics() throws IOException {
        StatisticsData statisticsData = new StatisticsData();
        statisticsData.incrementTotalDeclarations(10);
        statisticsData.incrementCompleteDeclarations(5);
        statisticsData.incrementMaleDeclarations(3);
        statisticsFileManager.saveStatistics(statisticsData);
        StatisticsData loadedStatistics = statisticsFileManager.loadStatistics();
        assertEquals(10, loadedStatistics.getTotalDeclarations());
        assertEquals(5, loadedStatistics.getCompleteDeclarations());
        assertEquals(3, loadedStatistics.getMaleDeclarations());
    }

    @Test
    void testLoadStatistics_WhenFileContainsValidData() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("totalDeclarations", 15);
        jsonObject.put("completeDeclarations", 8);
        jsonObject.put("maleDeclarations", 6);
        Files.writeString(tempFile.toPath(), jsonObject.toString());
        StatisticsData loadedStatistics = statisticsFileManager.loadStatistics();
        assertEquals(15, loadedStatistics.getTotalDeclarations());
        assertEquals(8, loadedStatistics.getCompleteDeclarations());
        assertEquals(6, loadedStatistics.getMaleDeclarations());
    }

    @Test
    void testSaveStatistics_CreatesValidFile() throws IOException {
        StatisticsData statisticsData = new StatisticsData();
        statisticsData.incrementTotalDeclarations(20);
        statisticsData.incrementFemaleDeclarations(12);
        statisticsFileManager.saveStatistics(statisticsData);
        assertTrue(tempFile.exists());
        String fileContent = Files.readString(tempFile.toPath());
        JSONObject jsonObject = JSONObject.fromObject(fileContent);
        assertEquals(20, jsonObject.getInt("totalDeclarations"));
        assertEquals(12, jsonObject.getInt("femaleDeclarations"));
    }
}