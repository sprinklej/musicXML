package parsed;

import parser.PartListWrapper;
import parser.XMLConsts;

import java.util.ArrayList;

/**
 * Created by sprinklej on 2016-10-02.
 */
public class Score {
    private String xmlVersDocType;
    private String scoreType; //TODO - Attributes
    private Work work;
    private String movementNumber; // NO Attributes
    private String movementTitle;  // NO Attributes
    private Identification identification; // NO Attributes
    private Defaults defaults;
    private ArrayList<Credit> credit;
    private ArrayList<PartListWrapper> partList;


    public Score(String axmlVersDocType, String aScoreType){
        xmlVersDocType = axmlVersDocType;
        scoreType = aScoreType;
        credit = new ArrayList<Credit>();
        partList = new ArrayList<PartListWrapper>();
    }


    // GETTERS
    public String getScoreType() {
        return scoreType;
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
