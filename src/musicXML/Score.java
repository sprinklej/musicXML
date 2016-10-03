package musicXML;

/**
 * Created by sprinklej on 2016-10-02.
 */
public class Score {

    private String scoreType;

    public Score(String aScoreType){
        scoreType = aScoreType;
    }


    // GETTERS
    public String getScoreType() {
        return scoreType;
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
}
