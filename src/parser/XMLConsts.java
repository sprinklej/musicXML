/*
 *
 * More info about AssertionError's at
 * http://www.javapractices.com/topic/TopicAction.do?Id=2
 */

package parser;

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
    public static final String OPUS = "opus";

    //MOVEMENT
    public static final String MOVEMENT_NUM = "movement-number";
    public static final String MOVEMENT_TITLE = "movement-title";

    //IDENTIFICATION
    public static final String IDENTIFICATION = "identification";
    public static final String ENCODING = "encoding";
    public static final String MISCELLANEOUS = "miscellaneous";
    public static final String MISCELLANEOUS_FIELD = "miscellaneous-field";

    //DEFAULTS
    public static final String DEFAULTS = "defaults";
    public static final String SCALING = "scaling";
    public static final String APPEARANCE = "appearance";

    //PAGE-LAYOUT
    public static final String PAGE_LAYOUT = "page-layout";

    //PAGE-MARGINS
    public static final String PAGE_MARGINS = "page-margins";

    //SYSTEM-LAYOUT
    public static final String SYSTEM_LAYOUT = "system-layout";
    public static final String SYSTEM_MARGINS = "system-margins";
    public static final String SYSTEM_DIVIDERS = "system-dividers";

    // STAFF-LAYOUT
    public static final String STAFF_LAYOUT = "staff-layout";

    //CREDIT
    public static final String CREDIT = "credit";
    public static final String LINK = "link";

    //PART-LIST
    public static final String PART_LIST = "part-list";
    public static final String SCORE_PART = "score-part";
    public static final String PART_NAME_DISPLAY = "part-name-display";
    public static final String PART_ABBREVIATION_DISPLAY = "part-abbreviation-display";
    public static final String SCORE_INSTRUMENT = "score-instrument";
    public static final String MIDI_INSTRUMENT = "midi-instrument";

    public static final String PART_GROUP = "part-group";
    public static final String GROUP_NAME_DISPLAY = "group-name-display";
    public static final String GROUP_ABBREVIATION_DISPLAY = "group-abbreviation-display";
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
    public static final String DIRECTION = "direction";
    public static final String FIGURED_BASE = "figured-bass";
    public static final String FORWARD = "forward";
    public static final String GROUPING = "grouping";
    public static final String HARMONY = "harmony";
    public static final String NOTE = "note";
    public static final String SOUND = "sound";
    public static final String UNPITCHED = "unpitched";
    public static final String TIME_MODIFICATION = "time-modification";
    public static final String NOTEHEAD_TEXT = "notehead-text";
    public static final String ARTICULATIONS = "articulations";
    public static final String ORNAMENTS = "ornaments";
    public static final String TECHNICAL = "technical";
    public static final String ARROW = "arrow";
    public static final String BEND = "bend";
    public static final String HARMONIC = "harmonic";
    public static final String HOLE = "hole";
    public static final String TUPLET = "tuplet";
    public static final String TUPLET_ACTUAL = "tuplet-actual";
    public static final String TUPLET_NORMAL = "tuplet-normal";

    public static final String PRINT = "print";
    public static final String MEASURE_LAYOUT = "measure-layout";

    public static final String KEY = "key";
    public static final String TIME = "time";
    public static final String CLEF = "clef";
    public static final String STAFF_DETAILS = "staff-details";
    public static final String TRANSPOSE = "transpose";

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

    public static final String PITCH = "pitch";
    public static final String REST = "rest";
    public static final String NOTATIONS = "notations";
    public static final String LYRIC = "lyric";

    public static final String DYNAMICS = "dynamics";
    public static final String NAME = "name";
    public static final String TYPE = "type";
    public static final String VERSION = "version";
    public static final String SLASH = "slash";


    private XMLConsts(){
        throw new AssertionError();
    }

}
