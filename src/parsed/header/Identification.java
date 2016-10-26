package parsed.header;

import parsed.Creator;
import parsed.TypedText;

import java.util.ArrayList;

/**
 * Created by sprinklej on 2016-10-15.
 * FROM XSD: Identification contains basic metadata about the score. It includes the information in MuseData headers
 * that may apply at a score-wide, movement-wide, or part-wide level. The creator, rights, source, and relation
 * elements are based on Dublin Core.
 */
public class Identification {
    private ArrayList<TypedText> creator; // minOccurs=0 maxOccurs="unbounded"
    private ArrayList<TypedText> rights;     // minOccurs=0 maxOccurs="unbounded"
    // ENCODING SUBTREE
    private String eEncoder;
    private String eDate;                 // yyyy-mm-dd
    private String eDescription;
    private ArrayList<String> eSoftware; // documentation says {1,1} but most  have multiple
    // TODO SUPPORTS is its own subtree ***************************************
    private String eSupports;
    //private String esType; // supports attributes
    //private String esElement;
    //private String esAttribute;
    //private String esValue;

    private String source;
    private ArrayList<String> relation; //0..*
    //private String miscellaneous; //TODO add Support 0..1

    // CONSTRUCTOR
    public Identification() {
        creator = new ArrayList<TypedText>();
        rights = new ArrayList<TypedText>();
        relation = new ArrayList<String>();
        eSoftware = new ArrayList<String>();
    }

    // GETTERS
    public ArrayList<TypedText> getCreator() {
        return creator;
    }

    public ArrayList<TypedText> getRights() {
        return rights;
    }

    public String geteEncoder() {
        return eEncoder;
    }

    public String geteDate() {
        return eDate;
    }

    public String geteDescription() {
        return eDescription;
    }

    public ArrayList<String> geteSoftware() {
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

    public void setSource(String aSource) {
        source = aSource;
    }


    // ADD TO
    public void addToCreator(TypedText aCreator) {
        creator.add(aCreator);
    }

    public void addToRights(TypedText aRights) {
        rights.add(aRights);
    }

    public void addToRelation(String aRelation) {
        relation.add(aRelation);
    }

    public void addToeSoftware(String aeSoftware) {
        eSoftware.add(aeSoftware);
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
