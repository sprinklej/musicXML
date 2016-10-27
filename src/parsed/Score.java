package parsed;

import parsed.header.Identification;
import parsed.header.Work;
import parser.PartListWrapper;
import parser.XMLConsts;

import java.util.ArrayList;

/**
 * Created by sprinklej on 2016-10-02.
 * FROM XSD: The score-partwise element is the root element for a partwise MusicXML score. It includes a score-header
 * group followed by a series of parts with measures inside. The document-attributes attribute group includes
 * the version attribute.
 * FROM XSD: The score-timewise element is the root element for a timewise MusicXML score. It includes a score-header
 * group followed by a series of measures with parts inside. The document-attributes attribute group includes
 * the version attribute.
 */
public class Score {
    private String scoreType;                   // partwise or timewise
    private String scoreVersion;                // Optional - The musicxml version
    private Work work;                          // minOccurs=0
    private String movementNumber;              // minOccurs=0
    private String movementTitle;               // minOccurs=0
    private Identification identification;      // minOccurs=0
    private Defaults defaults;                  // minOccurs=0
    private ArrayList<Credit> credit;           // minOccurs=0 maxOccurs="unbounded"
    private ArrayList<PartListWrapper> partList;// minOccurs=1 maxOccurs=1


    public Score(String aScoreType){
        scoreType = aScoreType;
        credit = new ArrayList<Credit>();
        partList = new ArrayList<PartListWrapper>();
    }


    // GETTERS
    public String getScoreType() {
        return scoreType;
    }

    public String getScoreVersion() {
        return scoreVersion;
    }

    public Work getWork() {
        return work;
    }

    public String getMovementNumber() {
        return movementNumber;
    }

    public String getMovementTitle() {
        return movementTitle;
    }

    public Identification getIdentification() {
        return identification;
    }

    public Defaults getDefaults() {
        return defaults;
    }

    public ArrayList<Credit> getCredit() {
        return credit;
    }

    public ArrayList<PartListWrapper> getPartList() {
        return partList;
    }



    // SETTERS
    public void setScoreType(String aScoreType){
        if (aScoreType != XMLConsts.PARTWISE || aScoreType != XMLConsts.TIMEWISE) {
            System.out.println("ERROR: Could not change score type");
            return;
        } else {
            scoreType = aScoreType;
        }
    }

    public void setScoreVersion(String aVersion) {
        scoreVersion = aVersion;
    }


    public void setWork(Work aWork) {
        work = aWork;
    }

    public void setMovementNumber(String aMovementNumber) {
        movementNumber = aMovementNumber;
    }

    public void setMovementTitle(String aMovementTitle) {
        movementTitle = aMovementTitle;
    }

    public void setIdentification(Identification aIdentification) {
        identification = aIdentification;
    }

    public void setDefaults(Defaults aDefaults) {
        defaults = aDefaults;
    }

    // ADD TO ARRAYLIST
    public void addToCredit(Credit aCredit) {
        credit.add(aCredit);
    }

    public void addToPartList(PartListWrapper aPartListWrapper) {
        partList.add(aPartListWrapper);
    }


    // TOSTRING
    public String toString() {
        String string = "";
        string += "****Score-Type: " + scoreType + "****\n";
        string += "movement-number: " + movementNumber + "\n";
        string += "movement-title: " + movementTitle;
        return string;
    }
}
