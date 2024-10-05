package domaine;

import net.sf.json.JSONObject;

public class CycleSupporte extends FormationContinu{
    private final String inputFile;
    private final String outputFile;
    private JSONObject jsonObj;

    public CycleSupporte(String inputFile) {
        this.inputFile = inputFile;
        this.outputFile = null;
        this.jsonObj = null;
    }



}
