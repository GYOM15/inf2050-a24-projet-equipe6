package GROUPE6_INF2050.Utilities;

import java.io.File;

public class FilePathResolver {
    public static String resolvePath(String relativePath) {
        File currentDir = new File(System.getProperty("user.dir"));
        while (currentDir != null) {
            File potentialResource = new File(currentDir, relativePath);
            if (potentialResource.exists()) {
                return potentialResource.getAbsolutePath();
            }
            currentDir = currentDir.getParentFile();
        }
        throw new RuntimeException("Unable to locate the resource: " + relativePath);
    }
}