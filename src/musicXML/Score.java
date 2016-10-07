package musicXML;

import parser.PartListWrapper;
import parser.XMLConsts;

import java.util.ArrayList;

/**
 * Created by sprinklej on 2016-10-02.
 */
public class Score {
    private String scoreType;
    private String workNumber;
    private String workTitle;
    private String movementNumber;
    private String movementTitle;
    private String creatorComposer;
    private ArrayList<PartListWrapper> partList;


    public Score(String aScoreType){
        scoreType = aScoreType;
        partList = new ArrayList<PartListWrapper>();
    }


    // GETTERS
    public String getScoreType() {
        return scoreType;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public String getMovementNumber() {
        return movementNumber;
    }

    public String getMovementTitle() {
        return movementTitle;
    }

    public String getCreatorComposer() {
        return creatorComposer;
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

    public void setWorkNumber(String aWorkNumber) {
        workNumber = aWorkNumber;
    }

    public void setWorkTitle(String aWorkTitle) {
        workTitle = aWorkTitle;
    }

    public void setMovementNumber(String aMovementNumber) {
        movementNumber = aMovementNumber;
    }

    public void setMovementTitle(String aMovementTitle) {
        movementTitle = aMovementTitle;
    }

    public void setCreatorComposer(String aCreatorComposer) {
        creatorComposer = aCreatorComposer;
    }


    // ADD TO PART-LIST
    public void addToPartList(PartListWrapper aPartListWrapper) {
        partList.add(aPartListWrapper);
    }

    public String toString() {
        String string = "";

        string += "Score-Type: " + scoreType + "\n";
        string += "work-number: " + workNumber + "\n";
        string += "work-Title: " + workTitle + "\n";
        string += "movement-number: " + movementNumber + "\n";
        string += "movement-title: " + movementTitle + "\n";
        string += "creator-composer: " + creatorComposer;

        return string;
    }
}
