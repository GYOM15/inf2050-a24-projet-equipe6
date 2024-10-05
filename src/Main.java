import domaine.JSONDecrypteur;
import exception.ValidException;

import java.io.FileNotFoundException;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args)throws FileNotFoundException, ValidException {

//        if (args.length != 1){
//            System.out.println("nombre d'arguments invalide...");
//            exit(1);
//        }

        JSONDecrypteur obj = new JSONDecrypteur("src/FormationEntree.json");
        obj.load();

        //obj.cycleSupporte();

        //obj.verificationNumeroPermis();

        obj.verificationCategorie();
        }

}
