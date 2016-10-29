package parsed.header.identification;

import parsed.header.TypedText;

import java.util.ArrayList;

/**
 * Created by sprinklej on 2016-10-15.
 * FROM XSD: Identification contains basic metadata about the score. It includes the information in MuseData headers
 * that may apply at a score-wide, movement-wide, or part-wide level. The creator, rights, source, and relation
 * elements are based on Dublin Core.
 */
public class Identification {
    private ArrayList<TypedText> creator;            // minOccurs=0 maxOccurs="unbounded"
    private ArrayList<TypedText> rights;             // minOccurs=0 maxOccurs="unbounded"
    private Encoding encoding;                       // minOccurs=0
    private String source;                           // minOccurs=0
    private ArrayList<TypedText> relation;           // minOccurs=0 maxOccurs="unbounded"
    //private boolean miscellaneous;                 // minOccurs=0 - miscellaneous could be anything
    private ArrayList<MiscellaneousField> miscField; // minOccurs=0 maxOccurs="unbounded"

    // CONSTRUCTOR
    public Identification() {
        creator = new ArrayList<TypedText>();
        rights = new ArrayList<TypedText>();
        relation = new ArrayList<TypedText>();
        miscField = new ArrayList<MiscellaneousField>();
    }


    // GETTERS
    public ArrayList<TypedText> getCreator() {
        return creator;
    }

    public ArrayList<TypedText> getRights() {
        return rights;
    }

    public Encoding getEncoding() {
        return encoding;
    }

    public String getSource() {
        return source;
    }

    public ArrayList<TypedText> getRelation() {
        return relation;
    }
/*
    public boolean getMiscellaneous() {
        return miscellaneous;
    }
*/
    public ArrayList<MiscellaneousField> getMiscField() {
        return miscField;
    }


    //SETTERS
    public void setEncoding(Encoding aEncoding) {
        encoding = aEncoding;
    }

    public void setSource(String aSource) {
        source = aSource;
    }
/*
    public void setMiscellaneous(boolean aMisc) {
        miscellaneous = aMisc;
    }
*/

    // ADD TO
    public void addToCreator(TypedText aCreator) {
        creator.add(aCreator);
    }

    public void addToRights(TypedText aRights) {
        rights.add(aRights);
    }

    public void addToRelation(TypedText aRelation) {
        relation.add(aRelation);
    }

    public void addToMiscField(MiscellaneousField aMiscField) {
        miscField.add(aMiscField);
    }
}

