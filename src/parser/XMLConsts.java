package parser;


/**
 * Created by sprinklej on 2016-10-02.
 *
 * http://www.javapractices.com/topic/TopicAction.do?Id=2
 */
public final class XMLConsts {

    //TYPE
    public static final String PARTWISE = "score-partwise";
    public static final String TIMEWISE = "score-timewise";


    //GENERAL
    public static final String ID = "id";
    public static final String NUMBER = "number";
    public static final String TYPE = "type";
    public static final String START = "start";
    public static final String STOP = "stop";


    //**HEADER-INFO**
    //WORK
    public static final String WORK = "work";
    public static final String WORK_NUM = "work-number";
    public static final String WORK_TITLE = "work-title";
    public static final String OPUS = "opus";

    //MOVEMENT
    public static final String MOVEMENT_NUM = "movement-number";
    public static final String MOVEMENT_TITLE = "movement-title";

    //IDENTIFICATION
    public static final String IDENTIFICATION = "identification";
    public static final String CREATOR = "creator";
    public static final String RIGHTS = "rights";
    public static final String ENCODING = "encoding";
    public static final String ENCODER = "encoder";
    public static final String ENCODING_DATE = "encoding-date";
    public static final String SOFTWARE = "software";
    public static final String SUPPORTS = "supports";
    public static final String ELEMENT = "element";
    public static final String ATTRIBUTE = "attribute";
    public static final String VALUE = "value";
    public static final String ENCODING_DESCRIPTION = "encoding-description";
    public static final String SOURCE = "source";
    public static final String RELATION = "relation";

    //DEFAULTS
    public static final String DEFAULTS = "defaults";
    public static final String SCALING = "scaling";
    public static final String STAFF_LAYOUT = "staff-layout";
    public static final String APPEARANCE = "appearance";
    public static final String MUSIC_FONT = "music-font";
    public static final String WORD_FONT = "word-font";
    public static final String LYRIC_FONT = "lyric-font";
    public static final String LYRIC_LANGUAGE = "lyric-language";



    //CREDIT
    public static final String CREDIT = "credit";
    public static final String CREDIT_TYPE = "credit-type";
    public static final String LINK = "link";
    public static final String BOOKMARK = "bookmark";
    public static final String CREDIT_IMAGE = "credit-image";
    public static final String CREDIT_WORDS = "credit-words";

    //PART-LIST
    public static final String PART_LIST = "part-list";
    public static final String SCORE_PART = "score-part";
    public static final String PART_NAME = "part-name";
    public static final String PART_ABBREV = "part-abbreviation";
    public static final String PART_GROUP = "part-group";
    public static final String GROUP_NAME = "group-name";


    //**BODY**
    //PART
    public static final String PART = "part";


    //MEASURE
    public static final String MEASURE = "measure";
    public static final String WIDTH = "width";

    //BODY
    public static final String PRINT = "print";

    public static final String PAGE_LAYOUT = "page-layout";
    public static final String PAGE_HEIGHT = "page-height";
    public static final String PAGE_WIDTH = "page-width";

    public static final String PAGE_MARGINS = "page-margins";
    public static final String LEFT_MARGIN = "left-margin";
    public static final String RIGHT_MARGIN = "right-margin";
    public static final String TOP_MARGIN = "top-margin";
    public static final String BOTTOM_MARGIN = "bottom-margin";

    public static final String SYSTEM_LAYOUT = "system-layout";
    //public static final String LEFT_MARGIN = "left-margin";
    //public static final String RIGHT_MARGIN = "right-margin";
    public static final String TOP_SYSTEM_DISTANCE = "top-system-distance";
    public static final String MEASURE_NUMBERING = "measure-numbering";

    public static final String ATTRIBUTES = "attributes";
    public static final String DIVISIONS = "divisions";
    public static final String KEY = "key";
    public static final String FIFTHS = "fifths";
    public static final String MODE = "mode";
    public static final String TIME = "time";
    public static final String BEATS = "beats";
    public static final String BEAT_TYPE = "beat-type";
    public static final String CLEF = "clef";
    public static final String SIGN = "sign";
    public static final String LINE = "line";
    public static final String STAFF_DETAILS = "staff-details";
    public static final String STAFF_SIZE = "staff-size";
    public static final String TRANSPOSE = "transpose";
    public static final String DIATONIC = "diatonic";
    public static final String CHROMATIC = "chromatic";
    public static final String OCTAVE_CHANGE = "octave-change";

    public static final String DIRECTION = "direction";
    public static final String DIRECTION_TYPE = "direction-type";
    public static final String WORDS = "words";
    public static final String METRONOME = "metronome";
    public static final String BEAT_UNIT = "beat-unit";
    public static final String PER_MINUTE = "per-minute";
    public static final String SOUND = "sound";

    public static final String NOTE = "note";
    public static final String CHORD = "chord";
    public static final String PITCH = "pitch";
    public static final String STEP = "step";
    public static final String ALTER = "alter";
    public static final String OCTAVE = "octave";
    public static final String REST = "rest";
    public static final String DURATION = "duration";
    public static final String TIE = "tie";
    public static final String VOICE = "voice";
    //public static final String TYPE = "type";
    public static final String DOT = "dot";
    public static final String STEM = "stem";
    public static final String NOTATIONS = "notations";
    public static final String TIED = "tied";
    public static final String LYRIC = "lyric";
    public static final String SYLLABIC = "syllabic";
    public static final String TEXT = "text";
    //public static final String DIRECTION = "direction";
    //public static final String DIRECTION_TYPE = "direction-type";
    public static final String WEDGE = "wedge";
    public static final String OFFSET = "offset";


    private XMLConsts(){
        throw new AssertionError();
    }

}
