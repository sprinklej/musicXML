package parser;


/**
 * Created by sprinklej on 2016-10-02.
 *
 * http://www.javapractices.com/topic/TopicAction.do?Id=2
 */
public final class XMLConsts {

    // TYPE
    public static final String PARTWISE = "score-partwise";
    public static final String TIMEWISE = "score-timewise";

    // GENERAL
    public static final String ID = "id";
    public static final String NUMBER = "number";
    public static final String TYPE = "type";
    public static final String START = "start";
    public static final String STOP = "stop";

    //HEADER
    public static final String WORK = "work";
    public static final String WORK_NUM = "work-number";
    public static final String WORK_TITLE = "work-title";

    public static final String MOVEMENT_NUM = "movement-number";
    public static final String MOVEMENT_TITLE = "movement-title";

    public static final String IDENTIFICATION = "identification";
    public static final String CREATOR = "creator";
    public static final String COMPOSER = "composer";
    //RIGHTS
    //ENCODING SUBTREE-> ENCODING-DATE, ENCODER, SOFTWARE, ENCODING-DESCRIPTION
    //SOURCE

    //DEFAULTS
    //CREDIT

    //HEADER - PART-LIST
    public static final String PART_LIST = "part-list";
    public static final String SCORE_PART = "score-part";
    public static final String PART_NAME = "part-name";
    public static final String PART_ABBREV = "part-abbreviation";
    public static final String PART_GROUP = "part-group";
    public static final String GROUP_NAME = "group-name";


    // PART
    public static final String PART = "part";


    // MEASURE
    public static final String MEASURE = "measure";

    // TIMEWISE

    private XMLConsts(){
        throw new AssertionError();
    }

}
