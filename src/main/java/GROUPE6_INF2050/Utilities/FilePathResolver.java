/**
 * Classe utilitaire pour résoudre le chemin absolu d'un fichier ou d'une ressource à partir d'un chemin relatif.
 * Cette classe parcourt récursivement les répertoires parents en partant du répertoire de travail courant
 * pour localiser une ressource donnée.
 */
package GROUPE6_INF2050.Utilities;

import java.io.File;

public class FilePathResolver {

    /**
     * Résout le chemin absolu d'un fichier ou d'une ressource en utilisant un chemin relatif.
     *
     * @param relativePath Le chemin relatif de la ressource à localiser.
     * @return Le chemin absolu de la ressource si elle est trouvée.
     * @throws RuntimeException si la ressource ne peut pas être localisée.
     */
    public static String resolvePath(String relativePath) {
        File currentDir = new File(System.getProperty("user.dir")); // Recupère le rrépertoire de travail courant
        while (currentDir != null) {
            File potentialResource = new File(currentDir, relativePath); // Fait une recherche dans le répertoire courant
            if (potentialResource.exists()) {
                return potentialResource.getAbsolutePath();
            } currentDir = currentDir.getParentFile(); // Remonte d'un cran au répertoire parent
        }
        throw new RuntimeException("Unable to locate the resource: " + relativePath);
    }
}