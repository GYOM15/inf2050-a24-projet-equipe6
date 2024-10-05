package domaine;

import java.util.ArrayList;

public class FormationContinu {
    private ArrayList<String> categories;

    public FormationContinu() {
        categories = new ArrayList();
        categories.add("cours");
        categories.add("atelier");
        categories.add("séminaire");
        categories.add("colloque");
        categories.add("conférence");
        categories.add("lecture dirigée");
        categories.add("présentation");
        categories.add("groupe de discussion");
        categories.add("projet de recherche");
        categories.add("rédaction professionnelle");
    }

    public ArrayList<String> getCategories() {
        return categories;
    }
}
