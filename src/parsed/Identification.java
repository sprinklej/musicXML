package parsed;

import java.util.ArrayList;

/**
 * Created by sprinklej on 2016-10-15.
 */
public class Identification {
    private ArrayList<Creator> creator; //0..*
    private ArrayList<String> rights; //0..*
    // ENCODING SUBTREE private String encoding;
    private String eEncoder;
    private String eDate;   // yyyy-mm-dd
    private String eDescription;
    private String eSoftware;
    // TODO SUPPORTS is its own subtree ***************************************
    //private String eSupports;
    //private String esType; // supports attributes
    //private String esElement;
    //private String esAttribute;
    //private String esValue;

    private String source;
    private ArrayList<String> relation; //0..*
    //private String miscellaneous; //TODO add Support 0..1

    // CONSTRUCTOR
    public Identification() {
        creator = new ArrayList<Creator>();
        rights = new ArrayList<String>();
        relation = new ArrayList<String>();
    }

    // GETTERS
    public ArrayList<Creator> getCreator() {
        return creator;
    }

    public ArrayList<String> getRights() {
        return rights;
    }

    /*public String getEncoding() {
        return encoding;
    }*/
    public String geteEncoder() {
        return eEncoder;
    }

    public String geteDate() {
        return eDate;
    }

    public String geteDescription() {
        return eDescription;
    }

    public String geteSoftware() {
        return eSoftware;
    }

    public String getSource() {
        return source;
    }

    public ArrayList<String> getRelation() {
        return relation;
    }



    //SETTERS
    public void seteEncoder(String aeEncoder) {
        eEncoder = aeEncoder;
    }

    public void seteDate(String aeDate) {
        eDate = aeDate;
    }

    public void seteDescription(String aeDescription) {
        eDescription = aeDescription;
    }

    public void seteSoftware(String aeSoftware) {
        eSoftware = aeSoftware;
    }


    public void setSource(String aSource) {
        source = aSource;
    }


    // ADD TO
    public void addToCreator(Creator aCreator) {
        creator.add(aCreator);
    }

    public void addToRights(String aRights) {
        rights.add(aRights);
    }

    public void addToRelation(String aRelation) {
        relation.add(aRelation);
    }


    // TOSTRING
    public String toString() {
        String string = "";
        //string += "cTypes: " + cType + "\n";
        //string += "creators: " + creator + "\n";
        //string += "encoding: " + encoding + "\n";
        string += "rights: " + rights + "\n";
        string += "source: " + source + "\n";
        string += "relation: " + relation;
        return string;
    }


}
