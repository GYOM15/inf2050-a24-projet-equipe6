package GROUPE6_INF2050.Utilities;


import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.io.IOUtils;
import GROUPE6_INF2050.Exceptions.Groupe6INF2050Exception;
import GROUPE6_INF2050.Handlers.ErrorHandler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Cette classe est dédiée au traitement du fichier Json
 * */
public class JsonFileUtility extends FileUtility {
    private JSONObject jsonObject;

    public JSONArray getJsonArray() {
        return jsonObject.getJSONArray("activites");
    }

    /**
     * Constructeur de la classe JsonFileUtility, hérité de la classe mère FileTreatment.
     * Il initialise les fichiers d'entrée et de sortie, il crée également un object JSON Vide
     *
     * @param inputFile Le fichier d'entrée JSON
     * @param outputFile    Le fichier de sortie JSON où seront affichés les resultats
     */
    public JsonFileUtility(String inputFile, String outputFile) {
        super(inputFile, outputFile);
        this.jsonObject = new JSONObject();
    }

    /**
     * Retourne l'objet JSON de la classe.
     *
     * @return Le contenu du 'inputFile' chargé dqns le JSONObject.
     */
    public JSONObject getJsonObject() {
        return jsonObject;
    }

    /**
     * Cette méthode valide et convertie un fichier JSON, identifiable par son chemin, vers un JSONObject manipulable
     *  @throws Groupe6INF2050Exception Ramène une exception si une erreur est trouvée
     */

    public void loadAndValid() throws Groupe6INF2050Exception {
        try {
            this.jsonObject = (JSONObject) JSONSerializer.toJSON(IOUtils.toString(new FileInputStream(inputFile), StandardCharsets.UTF_8));
        }
        catch (FileNotFoundException e) { throw new Groupe6INF2050Exception("Le fichier n'existe pas"); }
        catch (JSONException e) { throw new Groupe6INF2050Exception("Le fichier JSON n'est pas valide"); }
        catch (IOException e) { throw new Groupe6INF2050Exception("Erreur de lecture du fichier JSON"); }
    }

    /**
     * Cette méthode écrit un fichier JSON de sortie à partir des érreurs stockées dans le ErrorHandler.
     * Elle rajoute une clé "complet" et lui attribue en valeur false, s'il y'a des erreurs et true s'il n'y en a pas
     * @param errorHandler L'objet ErrorHandler est injecté ici afin de pouvoir acceder au tableau d'erreurs
     *                     et l'écire dans le fichier de sortie
     *  @throws Groupe6INF2050Exception Elle ramène une exception si une IOException est trouvée  lors de l'écriture du fichier sortie
     */

    public void save(ErrorHandler errorHandler) throws Groupe6INF2050Exception {
        JSONObject jsonOutput = createJsonOutput(errorHandler);
        writeToFile(jsonOutput);
    }

    /**
     * Crée un objet JSON contenant le statut de complétude et les erreurs.
     *
     * @param errorHandler L'objet ErrorHandler contenant les erreurs à valider.
     * @return Un JSONObject représentant le statut et les erreurs.
     */
    private JSONObject createJsonOutput(ErrorHandler errorHandler) {
        JSONObject jsonOutput = new JSONObject();
        jsonOutput.put("complet", errorHandler.hasErrors());
        JSONArray jsonErrors = new JSONArray();
        jsonErrors.addAll(errorHandler.getErrors());
        jsonOutput.put("errors", jsonErrors);
        return jsonOutput;
    }

    /**
     * Écrit l'objet JSON dans un fichier.
     *
     * @param jsonOutput L'objet JSON à écrire dans le fichier.
     * @throws Groupe6INF2050Exception En cas d'erreur lors de l'écriture du fichier.
     */
    private void writeToFile(JSONObject jsonOutput) throws Groupe6INF2050Exception {
        try (FileWriter f = new FileWriter(outputFile)) {
            f.write(jsonOutput.toString(4) + "\t\n");
            f.flush();
        } catch (IOException e) {
            throw new Groupe6INF2050Exception("Erreur lors de la sauvegarde du fichier : " + e.getMessage());
        }
    }



}
