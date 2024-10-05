package domaine;

import exception.ValidException;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JSONDecrypteur extends FormationContinu {

    private final String inputFile;
    private final String outputFile;
    private JSONObject jsonObj;
    private FormationContinu categorie;

    public JSONDecrypteur(String inputFile) {
        this.jsonObj = null;
        this.outputFile = null;
        this.inputFile = inputFile;
        categorie = new FormationContinu();
    }

    public boolean load() throws JSONException, ValidException {

        boolean result;

        try {
            String stringJson = IOUtils.toString(new FileInputStream(this.inputFile),  "UTF-8");
            JSONObject jsonObj = (JSONObject) JSONSerializer.toJSON(stringJson);
            this.jsonObj = jsonObj;
            result = true;
        }catch(FileNotFoundException e){
            throw new ValidException("Le fichier d'entré n'exixte pas");
        }catch(JSONException e){
            throw new ValidException("Le fichier json n'est pas valide");
        }catch(IOException e){
            throw new ValidException(e.toString());
        }
        return result;
    }

    public void cycleSupporte() {
        JSONArray activites = (JSONArray) JSONSerializer.toJSON(jsonObj.getString("activites"));
        String cycle = jsonObj.getString("cycle");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate date1 = LocalDate.parse("2023-04-01");
        LocalDate date2 = LocalDate.parse("2025-04-01");
        if (cycle.equals("2023-2025")) {

            for (int i = 0; activites.size() > i; i++) {

                try {
                    LocalDate date = LocalDate.parse(activites.getJSONObject(i).getString("date"), formatter);

                    if (date.isAfter(date1) && date.isBefore(date2)) {
                        System.out.println("good");
                    } else {
                        System.out.println("La date n'est pas compris entre le 1er avril 2023 et 2025.");
                    }
                } catch (Exception e) {

                }
            }
        } else {
            System.out.println("Seulement le cycle 2023-2025 est supporté");
        }
    }

    public void verificationNumeroPermis(){
        String numeroDePermis = jsonObj.getString("numero_de_permis");

        if(numeroDePermis.length() == 5){
            if(numeroDePermis.charAt(0) == 'A' || numeroDePermis.charAt(0) == 'T' || numeroDePermis.charAt(0) == 'M' || numeroDePermis.charAt(0) == 'K'){
                for (int i = 1; numeroDePermis.length() > i; i++) {
                    for (int j = 0; "1234567890".length() > j; j++) {
                        if ("1234567890".charAt(j) == numeroDePermis.charAt(i)) {
                            System.out.println("good");
                        }
                    }
                }
            }else{
                System.out.println("Le numéro de permis ne comprend pas dans son premier caractère de lettre parmis A,T,M,K.");
            }
        }else {
            System.out.println("Le numéro de permis est trop long il doit avoir un lettre et 4 chiffres");
        }
    }

    public void verificationCategorie(){
        JSONArray activites = (JSONArray) JSONSerializer.toJSON(jsonObj.getString("activites"));

        for(int i = 0;activites.size() > i;i++){
            int cpt = 0;
            for(int j = 0;categorie.getCategories().size() > j;j++){
                if(activites.getJSONObject(i).getString("categorie").equals(categorie.getCategories().get(j))){
                    j = categorie.getCategories().size()+1;
                }else{
                    cpt +=1;
                }
            }
            if(cpt == categorie.getCategories().size()){
                System.out.println("La catégorie "+activites.getJSONObject(i).getString("categorie")+
                        " de l'activité "+ i +" n'est pas dans la liste de catégorie");
            }
        }
    }

    public void verificationHeureTransferee(){
        int heureTransfereDuCyclePrecedent = jsonObj.getInt("heures_transferees_du_cycle_precedent");
        if(heureTransfereDuCyclePrecedent < 0){
            System.out.println("L'heure transferé est négatif");
            heureTransfereDuCyclePrecedent = 0;
        }else if(heureTransfereDuCyclePrecedent > 7){
            System.out.println("L'heure ne doit pas dépaser 7h donc calculé pour 7h");
            heureTransfereDuCyclePrecedent = 7;
        }
    }

    public void validationDHeureDeCour(){
        JSONArray activites = (JSONArray) JSONSerializer.toJSON(jsonObj.getString("activites"));
        int heureTotal = 0;
        for(int i = 0; activites.size() > i; i++){
            heureTotal = heureTotal + activites.getJSONObject(i).getInt("heures");
        }
        if (heureTotal < 40){
            System.out.println("L'heure total ne peut pas être plus petit que 40h");
        }
    }
}
