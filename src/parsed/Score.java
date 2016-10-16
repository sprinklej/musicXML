package parsed;

import parser.PartListWrapper;
import parser.XMLConsts;

import java.util.ArrayList;

/**
 * Created by sprinklej on 2016-10-02.
 */
public class Score {
    private String scoreType; //TODO - Attributes
    private Work work;
    private String movementNumber; // NO Attributes
    private String movementTitle;  // NO Attributes
   // private String creatorComposer;
    private ArrayList<PartListWrapper> partList;


    public Score(String aScoreType){
        scoreType = aScoreType;
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


    // ADD TO PART-LIST
    public void addToPartList(PartListWrapper aPartListWrapper) {
        partList.add(aPartListWrapper);
    }

    public String toString() {
        String string = "";
        string += "****Score-Type: " + scoreType + "****\n";
        string += "movement-number: " + movementNumber + "\n";
        string += "movement-title: " + movementTitle;
        return string;
    }
}
