package parser;

import parsed.Score;

/**
 * Created by sprinklej on 2016-10-07.
 * -- XML Body Structure --
 * -partwise-
 * part - {1,*}
 *  measure - {1,*}
 *      attributes
 *      backup
 *      barline
 *      bookmark
 *      direction
 *      figured-bass
 *      forward
 *      grouping
 *      harmony
 *      link
 *      note
 *      print
 *      sound
 *
 * -timewise-
 * measure - {1,*}
 *  part - {1,*}
 *      attributes
 *      backup
 *      barline
 *      bookmark
 *      direction
 *      figured-bass
 *      forward
 *      grouping
 *      harmony
 *      link
 *      note
 *      print
 *      sound
 */
public class ParseXMLBody {
    private Score score;
    private boolean isPartwise;

    public ParseXMLBody(Score aScore) {
        score = aScore;

        if (score.getScoreType().contentEquals(XMLConsts.PARTWISE)){
            isPartwise = true;
        } else {
            isPartwise = false;
        }
    }


    public void partwiseBody() {

    }

    public void timewiseBody() {

    }

}
