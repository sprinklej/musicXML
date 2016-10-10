package parsed;

import java.util.ArrayList;

/**
 * Created by sprinklej on 2016-10-06.
 */
public class Part {
    private String partID;

    private String partName;
    private String partAbbrev;
    //score-instrument
    //midi-instrument

    private ArrayList<Measure> measureList;

    public Part(String aPartID){
        partID = aPartID;
    }

    // GETTERS
    public String getPartID() {
        return partID;
    }

    public String getPartName() {
        return partName;
    }

    public String getPartAbbrev() {
        return partAbbrev;
    }


    // SETTERS
    public void setPartName(String aPartName) {
        partName = aPartName;
    }

    public void setPartAbbrev(String aPartAbbrev) {
        partAbbrev = aPartAbbrev;
    }


    // ADD TO MEASURE LIST
    public void addToMeasureList(Measure aMeasure) {
        measureList.add(aMeasure);
    }


    public String toString() {
        String string = "";

        string += "part-id: " + partID + "\n";
        string += "part-name: " + partName + "\n";
        string += "part-abbreviation: " + partAbbrev;

        return string;
    }

}