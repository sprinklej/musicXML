package parser;


/**
 * Created by sprinklej on 2016-10-02.
 *
 * http://www.javapractices.com/topic/TopicAction.do?Id=2
 */
public final class XMLConsts {
    //TYPE
    public static final String PARTWISE = "partwise";
    public static final String TIMEWISE = "timewise";
    public static final String SCORE_PARTWISE = "score-partwise";
    public static final String SCORE_TIMEWISE = "score-timewise";


    //GENERAL
    public static final String START = "start";
    public static final String STOP = "stop";
    public static final String NAMESPACEURL = "http://www.w3.org/XML/1998/namespace";
    public static final String XML = "xml";
    public static final String XLINKURL = "http://www.w3.org/1999/xlink";
    public static final String XMLNS = "xmlns";
    public static final String XLINK = "xlink";




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
    public static final String ENCODING_DESCRIPTION = "encoding-description";
    public static final String SOURCE = "source";
    public static final String RELATION = "relation";
    public static final String MISCELLANEOUS = "miscellaneous";
    public static final String MISCELLANEOUS_FIELD = "miscellaneous-field";

    //DEFAULTS
    public static final String DEFAULTS = "defaults";
    public static final String SCALING = "scaling";
    public static final String MILLIMETERS = "millimeters";
    public static final String TENTHS = "tenths";
    public static final String APPEARANCE = "appearance";
    public static final String LINE_WIDTH = "line-width";
    public static final String NOTE_SIZE = "note-size";
    public static final String DISTANCE = "distance";
    public static final String OTHER_APPEARANCE = "other-appearance";
    public static final String MUSIC_FONT = "music-font";
    public static final String WORD_FONT = "word-font";
    public static final String LYRIC_FONT = "lyric-font";
    public static final String LYRIC_LANGUAGE = "lyric-language";





    //PAGE-LAYOUT
    public static final String PAGE_LAYOUT = "page-layout";
    public static final String PAGE_HEIGHT = "page-height";
    public static final String PAGE_WIDTH = "page-width";

    //PAGE-MARGINS
    public static final String PAGE_MARGINS = "page-margins";
    public static final String LEFT_MARGIN = "left-margin";
    public static final String RIGHT_MARGIN = "right-margin";
    public static final String TOP_MARGIN = "top-margin";
    public static final String BOTTOM_MARGIN = "bottom-margin";


    //SYSTEM-LAYOUT
    public static final String SYSTEM_LAYOUT = "system-layout";
    public static final String SYSTEM_MARGINS = "system-margins";
    public static final String SYSTEM_DISTANCE = "system-distance";
    public static final String TOP_SYSTEM_DISTANCE = "top-system-distance";
    public static final String SYSTEM_DIVIDERS = "system-dividers";
    public static final String LEFT_DIVIDER = "left-divider";
    public static final String RIGHT_DIVIDER = "right-divider";

    // STAFF-LAYOUT
    public static final String STAFF_LAYOUT = "staff-layout";
    public static final String STAFF_DISTANCE = "staff-distance";





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
    public static final String PART_NAME_DISPLAY = "part-name-display";
    public static final String PART_ABBREVIATION = "part-abbreviation";
    public static final String PART_ABBREVIATION_DISPLAY = "part-abbreviation-display";
    public static final String GROUP = "group";
    public static final String SCORE_INSTRUMENT = "score-instrument";
    public static final String MIDI_DEVICE = "midi-device";
    public static final String MIDI_INSTRUMENT = "midi-instrument";

    public static final String PART_GROUP = "part-group";
    public static final String GROUP_NAME = "group-name";
    public static final String GROUP_NAME_DISPLAY = "group-name-display";
    public static final String ACCIDENTAL_TEXT = "accidental-text";
    public static final String DISPLAY_TEXT = "display-text";
    public static final String GROUP_ABBREVIATION = "group-abbreviation";
    public static final String GROUP_ABBREVIATION_DISPLAY = "group-abbreviation-display";
    public static final String GROUP_SYMBOL = "group-symbol";
    public static final String GROUP_BARLINE = "group-barline";
    public static final String GROUP_TIME = "group-time";
    public static final String FOOTNOTE = "footnote";
    public static final String LEVEL = "level";

    public static final String VIRTUAL_INSTRUMENT = "virtual-instrument";


    //**BODY**
    //PART
    public static final String PART = "part";
    public static final String MEASURE = "measure";

    public static final String ATTRIBUTES = "attributes";
    public static final String INTERCHANGEABLE = "interchangeable";
    public static final String MEASURE_STYLE = "measure-style";
    public static final String STAFF_TUNING = "staff-tuning";
    public static final String BEAT_REPEAT = "beat-repeat";



    public static final String BACKUP = "backup";
    public static final String BARLINE = "barline";
  //public static final String BOOKMARK = "bookmark";
    public static final String DIRECTION = "direction";
    public static final String FIGURED_BASE = "figured-bass";
    public static final String FORWARD = "forward";
    public static final String GROUPING = "grouping";
    public static final String HARMONY = "harmony";
  //public static final String LINK = "link";
    public static final String NOTE = "note";
    public static final String SOUND = "sound";




    public static final String PRINT = "print";




    public static final String MEASURE_NUMBERING = "measure-numbering";


    //public static final String DIVISIONS = "divisions";
    public static final String KEY = "key";
    public static final String FIFTHS = "fifths";
    public static final String MODE = "mode";
    public static final String TIME = "time";
    //public static final String BEATS = "beats";
    public static final String BEAT_TYPE = "beat-type";
    public static final String CLEF = "clef";
    //public static final String SIGN = "sign";
    //public static final String LINE = "line";
    public static final String STAFF_DETAILS = "staff-details";
    public static final String STAFF_SIZE = "staff-size";
    public static final String TRANSPOSE = "transpose";
    public static final String DIATONIC = "diatonic";
    public static final String CHROMATIC = "chromatic";
    public static final String OCTAVE_CHANGE = "octave-change";

    //public static final String DIRECTION = "direction";
    public static final String DIRECTION_TYPE = "direction-type";
    public static final String ACCORDION_REGISTRATION = "accordion-registration";
    public static final String HARP_PEDALS = "harp-pedals";
    public static final String PEDAL_TUNING = "pedal-tuning";
    public static final String METRONOME = "metronome";
    public static final String METRONOME_NOTE = "metronome-note";
    public static final String METRONOME_TUPLET = "metronome-tuplet";
    public static final String PERCUSSION = "percussion";
    public static final String STICK = "stick";
    public static final String SCORDATURA = "scordatura";
    public static final String ACCORD = "accord";
    public static final String PLAY = "play";

    public static final String FIGURE = "figure";
    public static final String ROOT = "root";
    public static final String BASS = "bass";
    public static final String DEGREE = "degree";
    public static final String FRAME = "frame";
    public static final String FRAME_NOTE = "frame-note";





    public static final String WORDS = "words";
    public static final String BEAT_UNIT = "beat-unit";
    public static final String PER_MINUTE = "per-minute";
    //public static final String SOUND = "sound";

    //public static final String NOTE = "note";
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
    //public static final String TEXT = "text";
    //public static final String DIRECTION = "direction";
    //public static final String DIRECTION_TYPE = "direction-type";
    public static final String WEDGE = "wedge";
    public static final String OFFSET = "offset";




    // ATTRIBUTES
    // Full list of attributes here http://usermanuals.musicxml.com/MusicXML/Content/AT-MusicXML.htm
    public static final String ACCELERATE = "accelerate";
    public static final String ADDITIONAL = "additional ";
    public static final String AFTER_BARLINE = "after-barline";
    public static final String ALTERNATE = "alternate";
    public static final String APPROACH = "approach";
    public static final String ATTACK = "attack";
    public static final String ATTRIBUTE = "attribute";

    public static final String BEATS = "beats";
    public static final String BEZIER_OFFSET = "bezier-offset";
    public static final String BEZIER_OFFSET2 = "bezier-offset2";
    public static final String BEZIER_X = "bezier-x";
    public static final String BEZIER_X2 = "bezier-x2";
    public static final String BEZIER_Y = "bezier-y";
    public static final String BEZIER_Y2 = "bezier-y2";
    public static final String BLANK_PAGE = "blank-page";
    public static final String BOTTOM_STAFF = "bottom-staff";
    public static final String BRACKET = "bracket";
    public static final String BRACKET_DEGREES = "bracket-degrees";

    public static final String CANCEL = "cancel";
    public static final String CAUTIONARY = "cautionary";
    public static final String CODA = "coda";
    public static final String COLOR = "color";

    public static final String DACAPO = "dacapo";
    public static final String DALSEGNO = "dalsegno";
    public static final String DAMPER_PEDAL = "damper-pedal";
    public static final String DASH_LENGTH = "dash-length";
    public static final String DEFAULT_X = "default-x";
    public static final String DEFAULT_Y = "default-y";
    public static final String DEPARTURE = "departure";
    public static final String DIR = "dir";
    //public static final String DIRECTION = "direction";
    public static final String DIRECTIVE = "directive";
    public static final String DIVISIONS = "divisions";
    public static final String DYNAMICS = "dynamics";

    public static final String EDITORIAL = "editorial";
    public static final String ELEMENT = "element";
    public static final String ELEVATION = "elevation";
    public static final String ENCLOSURE = "enclosure";
    public static final String END_DYNAMICS = "end-dynamics";
    public static final String END_LENGTH = "end-length";

    public static final String FAN = "fan";
    public static final String FILLED = "filled";
    public static final String FINE = "fine";
    public static final String FIRST_BEAT = "first-beat";
    public static final String FONT_FAMILY = "font-family";
    public static final String FONT_SIZE = "font-size";
    public static final String FONT_STYLE = "font-style";
    public static final String FONT_WEIGHT = "font-weight";
    public static final String FORWARD_REPEAT = "forward-repeat";

    public static final String HALIGN = "halign";
    public static final String HEIGHT = "height";

    public static final String ID = "id";
    public static final String IMPLICIT = "implicit";

    public static final String JUSTIFY = "justify";

    public static final String LAST_BEAT = "last-beat";
    public static final String LETTER_SPACING = "letter-spacing";
    public static final String LINE = "line";
    public static final String LINE_END = "line-end";
    public static final String LINE_HEIGHT = "line-height";
    public static final String LINE_SHAPE = "line-shape";
    public static final String LINE_THROUGH = "line-through";
    public static final String LINE_TYPE = "line-type";
    public static final String LOCATION = "location";
    public static final String LONG = "long";

    public static final String MAKE_TIME = "make-time";
    //public static final String MEASURE = "measure"; - ALSO a major element
    public static final String MEMBER_OF = "member-of";
    public static final String NAME = "name";
    public static final String NEW_PAGE = "new-page";
    public static final String NEW_SYSTEM = "new-system";
    public static final String NIENTE = "niente";
    public static final String NON_CONTROLLING = "non-controlling";
    public static final String NUMBER = "number";

    public static final String ORIENTATION = "orientation";
    public static final String OVERLINE = "overline";

    public static final String PAGE = "page";
    public static final String PAGE_NUMBER = "page-number";
    public static final String PAN = "pan";
    public static final String PARENTHESES = "parentheses";
    public static final String PARENTHESES_DEGREE = "parentheses-degrees";
    public static final String PIZZICATO = "pizzicato";
    public static final String PLACEMENT = "placement";
    public static final String PLUS_MINUS = "plus-minus";
    public static final String PORT = "port";
    public static final String POSITION = "position";
    public static final String PRINT_DOT = "print-dot";
    public static final String PRINT_FRAME = "print-frame";
    public static final String PRINT_LYRIC = "print-lyric";
    public static final String PRINT_OBJECT = "print-object";
    public static final String PRINT_SPACING = "print-spacing";

    public static final String REFERENCE = "reference";
    public static final String RELATIVE_X = "relative-x";
    public static final String RELATIVE_Y = "relative-y";
    public static final String RELEASE = "release";
    public static final String REPEATOR = "repeater";
    public static final String ROTATION = "rotation";

    public static final String SECOND_BEAT = "second-beat";
    public static final String SEGNO = "segno";
    public static final String SEPARATOR = "separator";
    public static final String SHOW_FRETS = "show-frets";
    public static final String SHOW_NUMBER = "show-number";
    public static final String SHOW_TYPE = "show-type";
    public static final String SIGN = "sign";
    public static final String SIZE = "size";
    public static final String SLASH = "slash";
    public static final String SLASHES = "slashes";
    public static final String SOFT_PEDAL = "soft-pedal";
    public static final String SOSTENUTO_PEDAL = "sostenuto-pedal";
    //public static final String SOUND = "sound";
    //public static final String SOURCE = "source"; - also an element
    public static final String SPACE_LENGTH = "space-length";
    public static final String SPREAD = "spread";
    public static final String STACK_DEGREES = "stack-degrees";
    public static final String STAFF_SPACING = "staff-spacing";
    public static final String START_NOTE = "start-note";
    public static final String STEAL_TIME_FOLLOWING = "steal-time-following";
    public static final String STEAL_TIME_PREVIOUS = "steal-time-previous";
    public static final String STRING = "string";
    public static final String SUBSTITUTION = "substitution";
    public static final String SYMBOL = "symbol";

    public static final String TEMPO = "tempo";
    public static final String TEXT = "text";
    public static final String TEXT_X = "text-x";
    public static final String TEXT_Y = "text-y";
    public static final String TIME_ONLY = "time-only";
    public static final String TIMES = "times";
    public static final String TIP = "tip";
    public static final String TOCODA = "tocoda";
    public static final String TOP_STAFF = "top-staff";
    public static final String TRILL_STEP = "trill-step";
    public static final String TWO_NOTE_TURN = "two-note-turn";
    public static final String TYPE = "type";

    public static final String UNDERLINE = "underline";
    public static final String UNPLAYED = "unplayed";
    public static final String USE_DOTS = "use-dots";
    public static final String USE_STEMS = "use-stems";
    public static final String USE_SYMBOLS = "use-symbols";

    public static final String VALIGN = "valign";
    public static final String VALUE = "value";
    public static final String VERSION = "version";

    public static final String WIDTH = "width";
    public static final String WINGED = "winged";

    //LINK ATTRIBUTES
    public static final String XLINKHREF = "http://www.w3.org/1999/xlink";
    public static final String XLINK_HREF = "href";       // use is required - the URL
    public static final String XLINK_TYPE = "type";       // the value is fixed to "simple"
    public static final String XLINK_ROLE = "role";
    public static final String XLINK_TITLE = "title";
    public static final String XLINK_SHOW = "show";       // default value of "replace"
    public static final String XLINK_ACTUATE = "actuate"; // default value of "onRequest"

    private XMLConsts(){
        throw new AssertionError();
    }

}
