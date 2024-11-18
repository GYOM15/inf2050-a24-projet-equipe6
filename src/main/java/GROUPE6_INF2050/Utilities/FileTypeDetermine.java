package GROUPE6_INF2050.Utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


/**
 * Classe permettant de déterminer le type d'un fichier en fonction de son extension.
 */
public class FileTypeDetermine {

    /**
     * Méthode permettant de déterminer le type d'un fichier donné.
     *
     * @param filename Le nom du fichier dont on souhaite déterminer le type.
     * @return Le type du fichier sous forme de chaîne de caractères.
     * @throws IOException Si le type ne peut être déterminé il nous ramène une exception.
     */
    public String determineFileType(String filename) throws IOException {
        File file = new File(filename);
        String fileType = Files.probeContentType(file.toPath());
        if (fileType == null) {
            throw new IOException("Impossible de déterminer le type du fichier.");
        }
        return fileType;
    }
}
