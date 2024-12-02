package GROUPE6_INF2050.Utilities;

import GROUPE6_INF2050.Exceptions.Groupe6INF2050Exception;

/**
 * Est une classe abstraite de laquelle hériteront toutes les classes qui s'occupent du traitement de fichiers
 * Les classes filles auront toutes besoin d'un fichier en entrée et en sortie.
 */
public  abstract class FileUtility {

    public final String inputFile;
    public String outputFile;

    /**
     * Constructeur de la classe FileUtility
     *Il initialise les fichiers d'entrée et de sortie
     *
     * @param inputFile Le fichier JSON en entrée
     * @param outputFile    Le fichier JSON de sortie
     */
    public FileUtility(String inputFile, String outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    /**
     * Cette méthode sera utilisée par toutes les classes filles pour charger et valider les fichiers en entrée.
     *
     * @throws Groupe6INF2050Exception  Ramène une exception si une erreur est trouvée
     */
    public abstract void loadAndValid() throws Groupe6INF2050Exception;
}
