package parsed;

import java.util.ArrayList;

/**
 * Created by sprinklej on 2016-10-15.
 */
public class Identification {
    private ArrayList<String> creator; //0..*
    private ArrayList<String> cType;

    private ArrayList<String> rights; //0..*

    private String encoding;
    private String eEncoder;
    private String eDate;   // yyyy-mm-dd
    private String eDescription;
    private String eSoftware;
    private String eSupports;
    private String esType; // supports attributes
    private String esElement;
    private String esAttribute;
    private String esValue;

    private String source;
    private ArrayList<String> relation; //0..*
    //private String miscellaneous; //TODO add Support 0..1

    // CONSTRUCTOR
    public Identification() {
        creator = new ArrayList<String>();
        cType = new ArrayList<String>();
        rights = new ArrayList<String>();
        relation = new ArrayList<String>();
    }

    // GETTERS
    public ArrayList<String> getCreator() {
        return creator;
    }

    public ArrayList<String> getcType() {
        return cType;
    }

    public ArrayList<String> getRights() {
        return rights;
    }

    public String getEncoding() {
        return encoding;
    }

    public String getSource() {
        return source;
    }

    public ArrayList<String> getRelation() {
        return relation;
    }



    //SETTERS
    public void setEncoding(String aEncoding) {
        encoding = aEncoding;
    }

    public void setSource(String aSource) {
        source = aSource;
    }


    // ADD TO
    public void addToCreator(String aCreator) {
        creator.add(aCreator);
    }

    public void addTocType(String acType) {
        cType.add(acType);
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
        string += "cTypes: " + cType + "\n";
        string += "creators: " + creator + "\n";
        string += "rights: " + rights + "\n";
        string += "encoding: " + encoding + "\n";
        string += "source: " + source + "\n";
        string += "relation: " + relation;
        return string;
    }


}
