/*
 * Parses the body of a MusicXML file
 *
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
package parser;

import org.codehaus.stax2.XMLStreamReader2;


import parsed.*;

import javax.xml.stream.events.XMLEvent;

public class ParseXMLBody {
    private ParseHelper parseHelper;

    private Score score;
    private XMLStreamReader2 xmlStreamReader;

    private ComplexElement currentPart;
    private ComplexElement currentMeasure;

    private ComplexElement attributes;
    private ComplexElement key;
    private ComplexElement time;
    private ComplexElement interchangeable;
    private ComplexElement clef;
    private ComplexElement staffDetails;
    private ComplexElement staffTuning;
    private ComplexElement transpose;
    private ComplexElement measureStyle;
    private ComplexElement beatRepeat;
    private ComplexElement slash;

    private ComplexElement backup;

    private ComplexElement barline;

    private ComplexElement direction;

    private ComplexElement directionType;
    private ComplexElement accordionRegistration;
    private ComplexElement dynamics;
    private ComplexElement harpPedals;
    private ComplexElement pedalTuning;
    private ComplexElement metronome;
    private ComplexElement metronomeNote;
    private ComplexElement metronomeTuplet;
    private ComplexElement percussion;
    private ComplexElement stick;
    private ComplexElement scordatura;
    private ComplexElement accord;
    private ComplexElement sound;
    private ComplexElement midiInstrument;
    private ComplexElement play;

    private ComplexElement figuredBass;
    private ComplexElement figure;

    private ComplexElement forward;

    private ComplexElement grouping;

    private ComplexElement harmony;
    private ComplexElement root;
    private ComplexElement bass;
    private ComplexElement degree;
    private ComplexElement frame;
    private ComplexElement frameNote;

    private ComplexElement note;
    private ComplexElement pitch;
    private ComplexElement rest;
    private ComplexElement unpitched;
    private ComplexElement timeModification;
    private ComplexElement noteHeadText;
    private ComplexElement notations;
    private ComplexElement articulations;
    private ComplexElement ornaments;
    private ComplexElement technical;
    private ComplexElement arrow;
    private ComplexElement bend;
    private ComplexElement harmonic;
    private ComplexElement hole;
    private ComplexElement tuplet;
    private ComplexElement tupletActual;
    private ComplexElement tupletNormal;
    private ComplexElement lyric;

    private ComplexElement print;
    private ComplexElement pageLayout;
    private ComplexElement pageMargins;
    private ComplexElement systemLayout;
    private ComplexElement systemMargins;
    private ComplexElement systemDividers;
    private ComplexElement staffLayout;
    private ComplexElement measureLayout;
    private ComplexElement partNameDisplay;
    private ComplexElement partAbbrevDisplay;



    // CONSTRUCTOR
    public ParseXMLBody(XMLStreamReader2 aXmlStreamReader) {
        xmlStreamReader = aXmlStreamReader;
        parseHelper = new ParseHelper();
    }

    // SETTER
    public void setScore(Score aScore) {
        score = aScore;
    }


    // ------------------------- MAIN PARSER FOR THE BODY PART OF THE XML -------------------------
    public void parseBody() {
        // get top body element either PART or MEASURE
        // part - partwise
        if ((score.getScoreType() == XMLConsts.PARTWISE)
                && (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART))) {
            currentPart = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, currentPart);
            XMLParser.getElements(xmlStreamReader, () -> partwiseStart(), () -> partwiseEnd());
        }
        // measure - timewise
        else if ((score.getScoreType() == XMLConsts.TIMEWISE)
                && (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MEASURE))) {
            currentMeasure = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, currentMeasure);
            XMLParser.getElements(xmlStreamReader, () -> timewiseStart(), () -> timewiseEnd());
        }
    }



    // ------------------------------------- PARTWISE SECTION -------------------------------------
    // PART
    private boolean partwiseStart() {
        // measure
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MEASURE)) {
            currentMeasure = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, currentMeasure);
            XMLParser.getElements(xmlStreamReader, () -> partMeasureStart(), () -> partMeasureEnd());
        }
        return false;
    }
    private boolean partwiseEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART)) {
            score.addToBody(new ElementWrapper(true, currentPart));
            return true;
        }
        return false;
    }

    // MEASURE - SUBTREE OF PART
    private boolean partMeasureStart() {
        // attributes
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ATTRIBUTES)) {
            attributes = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, attributes);
            XMLParser.getElements(xmlStreamReader, () -> attributesStart(), () -> attributesEnd());
        }
        // backup
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.BACKUP)) {
            backup = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, backup);
            XMLParser.getElements(xmlStreamReader, () -> backupStart(), () -> backupEnd());
        }
        // barline
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.BARLINE)) {
            barline = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, barline);
            XMLParser.getElements(xmlStreamReader, () -> barlineStart(), () -> barlineEnd());
        }
        // direction
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.DIRECTION)) {
            direction = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, direction);
            XMLParser.getElements(xmlStreamReader, () -> directionStart(), () -> directionEnd());
        }
        // figured-base
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.FIGURED_BASE)) {
            figuredBass = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, figuredBass);
            XMLParser.getElements(xmlStreamReader, () -> figuredBassStart(), () -> figuredBassEnd());
        }
        // forward
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.FORWARD)) {
            forward = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, forward);
            XMLParser.getElements(xmlStreamReader, () -> forwardStart(), () -> forwardEnd());
        }
        // grouping
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.GROUPING)) {
            grouping = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, grouping);
            XMLParser.getElements(xmlStreamReader, () -> groupingStart(), () -> groupingEnd());
        }
        // harmony
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.HARMONY)) {
            harmony = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, harmony);
            XMLParser.getElements(xmlStreamReader, () -> harmonyStart(), () -> harmonyEnd());
        }
        // link
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.LINK)) {
            Element e = parseHelper.getElement(xmlStreamReader);
            e.addToAttributes(new Attribute(XMLConsts.XMLNS + ":" + XMLConsts.XLINK, XMLConsts.XLINKURL));
            currentMeasure.addToElements(new ElementWrapper(false, e));
        }
        // note
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.NOTE)) {
            note = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, note);
            XMLParser.getElements(xmlStreamReader, () -> noteStart(), () -> noteEnd());
        }
        // print
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PRINT)) {
            print = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, print);
            XMLParser.getElements(xmlStreamReader, () -> printStart(), () -> printEnd());
        }
        // sound
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SOUND)) {
            sound = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, sound);
            XMLParser.getElements(xmlStreamReader, () -> soundStart(), () -> soundMeasureEnd());
        }
        // bookmark
        else {
            currentMeasure.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        }
        return false;
    }
    private boolean partMeasureEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MEASURE)) {
            currentPart.addToElements(new ElementWrapper(true, currentMeasure));
            return true;
        }
        return false;
    }



    // ------------------------------------- TIMEWISE SECTION -------------------------------------
    // MEASURE
    private boolean timewiseStart() {
        // part
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART)) {
            currentPart = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, currentPart);
            XMLParser.getElements(xmlStreamReader, () -> measurePartStart(), () -> measurePartEnd());
        }
        return false;
    }
    private boolean timewiseEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MEASURE)) {
            score.addToBody(new ElementWrapper(true, currentMeasure));
            return true;
        }
        return false;
    }

    // PART - SUBTREE OF MEASURE
    private boolean measurePartStart() {
        // attributes
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ATTRIBUTES)) {
            attributes = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, attributes);
            XMLParser.getElements(xmlStreamReader, () -> attributesStart(), () -> partAttributesEnd());
        }
        // backup
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.BACKUP)) {
            backup = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, backup);
            XMLParser.getElements(xmlStreamReader, () -> backupStart(), () -> partBackupEnd());
        }
        // barline
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.BARLINE)) {
            barline = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, barline);
            XMLParser.getElements(xmlStreamReader, () -> barlineStart(), () -> partBarlineEnd());
        }
        // direction
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.DIRECTION)) {
            direction = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, direction);
            XMLParser.getElements(xmlStreamReader, () -> directionStart(), () -> partDirectionEnd());
        }
        // figured-bass
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.FIGURED_BASE)) {
            figuredBass = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, figuredBass);
            XMLParser.getElements(xmlStreamReader, () -> figuredBassStart(), () -> partFiguredBassEnd());
        }
        // forward
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.FORWARD)) {
            forward = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, forward);
            XMLParser.getElements(xmlStreamReader, () -> forwardStart(), () -> partForwardEnd());
        }
        // grouping
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.GROUPING)) {
            grouping = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, grouping);
            XMLParser.getElements(xmlStreamReader, () -> groupingStart(), () -> partGroupingEnd());
        }
        // harmony
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.HARMONY)) {
            harmony = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, harmony);
            XMLParser.getElements(xmlStreamReader, () -> harmonyStart(), () -> partHarmonyEnd());
        }
        // link
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.LINK)) {
            Element e = parseHelper.getElement(xmlStreamReader);
            e.addToAttributes(new Attribute(XMLConsts.XMLNS + ":" + XMLConsts.XLINK, XMLConsts.XLINKURL));
            currentPart.addToElements(new ElementWrapper(false, e));
        }
        // note
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.NOTE)) {
            note = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, note);
            XMLParser.getElements(xmlStreamReader, () -> noteStart(), () -> partNoteEnd());
        }
        // print
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PRINT)) {
            print = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, print);
            XMLParser.getElements(xmlStreamReader, () -> printStart(), () -> partPrintEnd());
        }
        // sound
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SOUND)) {
            sound = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, sound);
            XMLParser.getElements(xmlStreamReader, () -> soundStart(), () -> partSoundEnd());
        }
        // bookmark
        else {
            currentPart.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        }

        // TODO - right here
        return false;
    }
    private boolean measurePartEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART)) {
            currentMeasure.addToElements(new ElementWrapper(true, currentPart));
            return true;
        }
        return false;
    }



    // ----------------------------------- GENERAL BODY SECTION -----------------------------------
    // ATTRIBUTES - Subtree of MEASURE/PART
    private boolean attributesStart() {
        // key
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.KEY)) {
            key = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, key);
            XMLParser.getElements(xmlStreamReader, () -> keyStart(), () -> keyEnd());
        }
        // time
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.TIME)) {
            time = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, time);
            XMLParser.getElements(xmlStreamReader, () -> timeStart(), () -> timeEnd());
        }
        // clef
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.CLEF)) {
            clef = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, clef);
            XMLParser.getElements(xmlStreamReader, () -> clefStart(), () -> clefEnd());
        }
        // staff-details
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.STAFF_DETAILS)) {
            staffDetails = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, staffDetails);
            XMLParser.getElements(xmlStreamReader, () -> staffDetailsStart(), () -> staffDetailsEnd());
        }
        // transpose
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.TRANSPOSE)) {
            transpose = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, transpose);
            XMLParser.getElements(xmlStreamReader, () -> transposeStart(), () -> transposeEnd());
        }
        // measure-style
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MEASURE_STYLE)) {
            measureStyle = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, measureStyle);
            XMLParser.getElements(xmlStreamReader, () -> measureStyleStart(), () -> measureStyleEnd());
        }
        // footnote, level, divisions, staves, part-symbol, instruments, directive
        else {
            attributes.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        }
        return false;
    }
    private boolean attributesEnd() {  // Subtree of MEASURE
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ATTRIBUTES)) {
            currentMeasure.addToElements(new ElementWrapper(true, attributes));
            return true;
        }
        return false;
    }
    private boolean partAttributesEnd() { // Subtree of PART
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ATTRIBUTES)) {
            currentPart.addToElements(new ElementWrapper(true, attributes));
            return true;
        }
        return false;
    }

    // KEY - Subtree of ATTRIBUTES
    private boolean keyStart() {
        // key-step, key-alter, key-accidental, cancel, fiths, mode, key-octave
        key.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean keyEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.KEY)) {
            attributes.addToElements(new ElementWrapper(true, key));
            return true;
        }
        return false;
    }

    // TIME - Subtree of ATTRIBUTES
    private boolean timeStart() {
        //interchangeable
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.INTERCHANGEABLE)) {
            interchangeable = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, interchangeable);
            XMLParser.getElements(xmlStreamReader, () -> interchangeableStart(), () -> interchangeableEnd());
        }
        // senza-misura, beats, beat-type,
        else {
            time.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        }
        return false;
    }
    private boolean timeEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.TIME)) {
            attributes.addToElements(new ElementWrapper(true, time));
            return true;
        }
        return false;
    }

    // INTERCHANGEABLE - Subtree of TIME
    private boolean interchangeableStart() {
        interchangeable.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean interchangeableEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.INTERCHANGEABLE)) {
            time.addToElements(new ElementWrapper(true, interchangeable));
            return true;
        }
        return false;
    }

    // CLEF - Subtree of ATTRIBUTES
    private boolean clefStart() {
        // sign, line, clef-octave-change
        clef.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean clefEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.CLEF)) {
            attributes.addToElements(new ElementWrapper(true, clef));
            return true;
        }
        return false;
    }

    // STAFF-DETAILS - Subtree of ATTRIBUTES
    private boolean staffDetailsStart() {
        //staff-tuning
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.STAFF_TUNING)) {
            staffTuning = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, staffTuning);
            XMLParser.getElements(xmlStreamReader, () -> staffTuningStart(), () -> staffTuningEnd());
        }
        // staff-type, staff-lines, capo, staff-size
        else {
            staffDetails.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        }
        return false;
    }
    private boolean staffDetailsEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.STAFF_DETAILS)) {
            attributes.addToElements(new ElementWrapper(true, staffDetails));
            return true;
        }
        return false;
    }

    // STAFF-TUNING - Subtree of STAFF-DETAILS
    private boolean staffTuningStart() {
        // tuning-step, tuning-alter, tuning-octave
        staffTuning.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean staffTuningEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.STAFF_TUNING)) {
            staffDetails.addToElements(new ElementWrapper(true, staffTuning));
            return true;
        }
        return false;
    }

    // TRANSPOSE - Subtree of ATTRIBUTES
    private boolean transposeStart() {
        // diatonic, chromatic, octave-change, double
        transpose.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean transposeEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.TRANSPOSE)) {
            attributes.addToElements(new ElementWrapper(true, transpose));
            return true;
        }
        return false;
    }

    // MEASURE-STYLE - Subtree of ATTRIBUTES
    private boolean measureStyleStart() {
        // beat-repeat
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.BEAT_REPEAT)) {
            beatRepeat = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, beatRepeat);
            XMLParser.getElements(xmlStreamReader, () -> beatRepeatStart(), () -> beatRepeatEnd());
        }
        // slash
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SLASH)) {
            slash = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, slash);
            XMLParser.getElements(xmlStreamReader, () -> slashStart(), () -> slashEnd());
        }
        // measure-repeat, multiple-rest,
        else {
            measureStyle.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        }
        return false;
    }
    private boolean measureStyleEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MEASURE_STYLE)) {
            attributes.addToElements(new ElementWrapper(true, measureStyle));
            return true;
        }
        return false;
    }

    // BEAT-REPEAT - Subtree of MEASURE-STYLE
    private boolean beatRepeatStart() {
        // slash-type, slash-dot
        beatRepeat.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean beatRepeatEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.BEAT_REPEAT)) {
            measureStyle.addToElements(new ElementWrapper(true, beatRepeat));
            return true;
        }
        return false;
    }

    // SLASH - Subtree of MEASURE-STYLE
    private boolean slashStart() {
        // slash-type, slash-dot
        slash.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean slashEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SLASH)) {
            measureStyle.addToElements(new ElementWrapper(true, slash));
            return true;
        }
        return false;
    }



    // BACKUP - Subtree of MEASURE/PART
    private boolean backupStart() {
        // duration, footnote, level
        backup.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean backupEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.BACKUP)) {
            currentMeasure.addToElements(new ElementWrapper(true, backup));
            return true;
        }
        return false;
    }
    private boolean partBackupEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.BACKUP)) {
            currentPart.addToElements(new ElementWrapper(true, backup));
            return true;
        }
        return false;
    }


    // BARLINE - Subtree of MEASURE/PART
    private boolean barlineStart() {
        // bar-style, footnote, level, wavy-line, segno, coda, fermata, ending, repeat
        barline.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean barlineEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.BARLINE)) {
            currentMeasure.addToElements(new ElementWrapper(true, barline));
            return true;
        }
        return false;
    }
    private boolean partBarlineEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.BARLINE)) {
            currentPart.addToElements(new ElementWrapper(true, barline));
            return true;
        }
        return false;
    }


    // DIRECTION - Subtree of MEASURE/PART
    private boolean directionStart() {
        // direction-type
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.DIRECTION_TYPE)) {
            directionType = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, directionType);
            XMLParser.getElements(xmlStreamReader, () -> directionTypeStart(), () -> directionTypeEnd());
        }
        // sound
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SOUND)) {
            sound = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, sound);
            XMLParser.getElements(xmlStreamReader, () -> soundStart(), () -> soundEnd());
        }
        // offset, footnote, level, voice, staff
        else {
            direction.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        }
        return false;
    }
    private boolean directionEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.DIRECTION)) {
            currentMeasure.addToElements(new ElementWrapper(true, direction));
            return true;
        }
        return false;
    }
    private boolean partDirectionEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.DIRECTION)) {
            currentPart.addToElements(new ElementWrapper(true, direction));
            return true;
        }
        return false;
    }

    // DIRECTION-TYPE - Subtree of DIRECTION
    private boolean directionTypeStart() {
        // accordion-registration
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ACCORDION_REGISTRATION)) {
            accordionRegistration = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, accordionRegistration);
            XMLParser.getElements(xmlStreamReader, () -> accordionRegistrationStart(), () -> accordionRegistrationEnd());
        }
        // dynamics
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.DYNAMICS)) {
            dynamics = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, dynamics);
            XMLParser.getElements(xmlStreamReader, () -> dynamicsStart(), () -> dynamicsEnd());
        }
        // harp-pedals
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.HARP_PEDALS)) {
            harpPedals = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, harpPedals);
            XMLParser.getElements(xmlStreamReader, () -> harpPedalsStart(), () -> harpPedalsEnd());
        }
        // metronome
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.METRONOME)) {
            metronome = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, metronome);
            XMLParser.getElements(xmlStreamReader, () -> metronomeStart(), () -> metronomeEnd());
        }
        // percussion
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PERCUSSION)) {
            percussion = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, percussion);
            XMLParser.getElements(xmlStreamReader, () -> percussionStart(), () -> percussionEnd());
        }
        // scordatura
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SCORDATURA)) {
            scordatura = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, scordatura);
            XMLParser.getElements(xmlStreamReader, () -> scordaturaStart(), () -> scordaturaEnd());
        }
        // bracket, coda, damp, damp-all, dashes, eyeglasses, image, octave-shift, other-direction, pedal,
        // principal-voice, rehearsal, segno, string-mute, wedge, words
        else {
            directionType.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        }
        return false;
    }
    private boolean directionTypeEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.DIRECTION_TYPE)) {
            direction.addToElements(new ElementWrapper(true, directionType));
            return true;
        }
        return false;
    }

    // ACCORDION-REGISTRATION - Subtree of DIRECTION-TYPE
    private boolean accordionRegistrationStart() {
        // accordion-high, accordion-middle, accordion-low
        accordionRegistration.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean accordionRegistrationEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ACCORDION_REGISTRATION)) {
            directionType.addToElements(new ElementWrapper(true, accordionRegistration));
            return true;
        }
        return false;
    }

    // DYNAMICS - Subtree of DIRECTION-TYPE
    private boolean dynamicsStart() {
        // f, ff, fff, ffff, fffff, ffffff, fp, fz, mf, mp, other-dynamics, p, pp, ppp, pppp, ppppp, pppppp,
        // rf, rfz, sf, sffz, sfp, sfpp, sfz
        dynamics.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean dynamicsEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.DYNAMICS)) {
            directionType.addToElements(new ElementWrapper(true, dynamics));
            return true;
        }
        return false;
    }
    private boolean dynamicsNotationsEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.DYNAMICS)) {
            notations.addToElements(new ElementWrapper(true, dynamics));
            return true;
        }
        return false;
    }


    // HARP-PEDALS - Subtree of DIRECTION-TYPE
    private boolean harpPedalsStart() {
        // pedal-tuning
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PEDAL_TUNING)) {
            pedalTuning = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, pedalTuning);
            XMLParser.getElements(xmlStreamReader, () -> pedalTuningStart(), () -> pedalTuningEnd());
        }
        return false;
    }
    private boolean harpPedalsEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.HARP_PEDALS)) {
            directionType.addToElements(new ElementWrapper(true, harpPedals));
            return true;
        }
        return false;
    }

    // PEDAL-TUNING - Subtree of HARP-PEDAL
    private boolean pedalTuningStart() {
        pedalTuning.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean pedalTuningEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PEDAL_TUNING)) {
            harpPedals.addToElements(new ElementWrapper(true, pedalTuning));
            return true;
        }
        return false;
    }

    // METRONOME - Subtree of DIRECTION-TYPE
    private boolean metronomeStart() {
        // metronome-note
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.METRONOME_NOTE)) {
            metronomeNote = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, metronomeNote);
            XMLParser.getElements(xmlStreamReader, () -> metronomeNoteStart(), () -> metronomeNoteEnd());
        }
        // metronome-relation, beat-unit, beat-unit-dot, per-minute
        else {
            metronome.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        }
        return false;
    }
    private boolean metronomeEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.METRONOME)) {
            directionType.addToElements(new ElementWrapper(true, metronome));
            return true;
        }
        return false;
    }

    // METRONOME-NOTE - Subtree of METRONOME
    private boolean metronomeNoteStart() {
        //metronome-tuplet
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.METRONOME_TUPLET)) {
            metronomeTuplet = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, metronomeTuplet);
            XMLParser.getElements(xmlStreamReader, () -> metronomeTupletStart(), () -> metronomeTupletEnd());
        }
        // metronome-type, metronome-dot, metronome-beam
        else {
            metronomeNote.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        }
        return false;
    }
    private boolean metronomeNoteEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.METRONOME_NOTE)) {
            metronome.addToElements(new ElementWrapper(true, metronomeNote));
            return true;
        }
        return false;
    }

    // METRONOME-TUPLET - Subtree of METRONOME-NOTE
    private boolean metronomeTupletStart() {
        metronomeTuplet.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean metronomeTupletEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.METRONOME_TUPLET)) {
            metronomeNote.addToElements(new ElementWrapper(true, metronomeTuplet));
            return true;
        }
        return false;
    }


    // PERCUSSION -Subtree of DIRECTION-TYPE
    private boolean percussionStart() {
        // stick
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.STICK)) {
            stick = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, stick);
            XMLParser.getElements(xmlStreamReader, () -> stickStart(), () -> stickEnd());
        }
        // beater, effect, glass, membrane, metal, other-percussion, pitched, stick-location, timpani, wood
        else {
            percussion.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        }
        return false;
    }
    private boolean percussionEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PERCUSSION)) {
            directionType.addToElements(new ElementWrapper(true, percussion));
            return true;
        }
        return false;
    }

    // STICK - Subtree of PERCUSSION
    private boolean stickStart() {
        // stick-type, stick-material
        stick.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean stickEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.STICK)) {
            percussion.addToElements(new ElementWrapper(true, stick));
            return true;
        }
        return false;
    }

    // SCORDATURA - Subtree of DIRECTION-TYPE
    private boolean scordaturaStart() {
        // accord
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ACCORD)) {
            accord = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, accord);
            XMLParser.getElements(xmlStreamReader, () -> accordStart(), () -> accordEnd());
        }
        return false;
    }
    private boolean scordaturaEnd(){
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SCORDATURA)) {
            directionType.addToElements(new ElementWrapper(true, scordatura));
            return true;
        }
        return false;
    }

    // ACCORD - Subtree of SCORDATURA
    private boolean accordStart() {
        // tuning-step, tuning-alter, tuning-octave
        accord.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean accordEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ACCORD)) {
            scordatura.addToElements(new ElementWrapper(true, accord));
            return true;
        }
        return false;
    }

    // SOUND - Subtree of DIRECTION
    private boolean soundStart() {
        // midi-instrument
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MIDI_INSTRUMENT)) {
            midiInstrument = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, midiInstrument);
            XMLParser.getElements(xmlStreamReader, () -> midiInstrumentStart(), () -> midiInstrumentEnd());
        }
        // play
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PLAY)) {
            play = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, play);
            XMLParser.getElements(xmlStreamReader, () -> playStart(), () -> playEnd());
        }
        // midi-device, offset
        else {
            sound.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        }
        return false;
    }
    private boolean soundEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SOUND)) {
            direction.addToElements(new ElementWrapper(true, sound));
            return true;
        }
        return false;
    }
    private boolean soundMeasureEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SOUND)) {
            currentMeasure.addToElements(new ElementWrapper(true, sound));
            return true;
        }
        return false;
    }
    private boolean partSoundEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SOUND)) {
            currentPart.addToElements(new ElementWrapper(true, sound));
            return true;
        }
        return false;
    }

    // MIDI-INSTRUMENT - Subtree of sound
    private boolean midiInstrumentStart() {
        // midi-channel, midi-name,  midi-bank, midi-program, midi-unpitched, volume, pan, elevation
        midiInstrument.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean midiInstrumentEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MIDI_INSTRUMENT)) {
            sound.addToElements(new ElementWrapper(true, midiInstrument));
            return true;
        }
        return false;
    }

    // PLAY - Subtree of sound
    private boolean playStart() {
        // ipa, mute, other-play, semi-pitched
        play.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean playEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PLAY)) {
            sound.addToElements(new ElementWrapper(true, play));
            return true;
        }
        return false;
    }
    private boolean playNoteEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PLAY)) {
            note.addToElements(new ElementWrapper(true, play));
            return true;
        }
        return false;
    }


    // FIGURED-BASS - Subtree of MEASURE/PART
    private boolean figuredBassStart() {
        // figure
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.FIGURE)) {
            figure = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, figure);
            XMLParser.getElements(xmlStreamReader, () -> figureStart(), () -> figureEnd());
        }
        // duration, footnote, level
        else {
            figuredBass.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        }
        return false;
    }
    private boolean figuredBassEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.FIGURED_BASE)) {
            currentMeasure.addToElements(new ElementWrapper(true, figuredBass));
            return true;
        }
        return false;
    }
    private boolean partFiguredBassEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.FIGURED_BASE)) {
            currentPart.addToElements(new ElementWrapper(true, figuredBass));
            return true;
        }
        return false;
    }

    // FIGURE - Subtree of FIGURED-BASS
    private boolean figureStart() {
        // prefix, figure-number, suffix, extend
        figure.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean figureEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.FIGURE)) {
            figuredBass.addToElements(new ElementWrapper(true, figure));
            return true;
        }
        return false;
    }


    // FORWARD - Subtree of MEASURE/PART
    private boolean forwardStart() {
        // duration, footnote, level, voice, staff
        forward.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean forwardEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.FORWARD)) {
            currentMeasure.addToElements(new ElementWrapper(true, forward));
            return true;
        }
        return false;
    }
    private boolean partForwardEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.FORWARD)) {
            currentPart.addToElements(new ElementWrapper(true, forward));
            return true;
        }
        return false;
    }


    // GROUPING - Subtree of MEASURE/PART
    private boolean groupingStart() {
        // feature
        grouping.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean groupingEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.GROUPING)) {
            currentMeasure.addToElements(new ElementWrapper(true, grouping));
            return true;
        }
        return false;
    }
    private boolean partGroupingEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.GROUPING)) {
            currentPart.addToElements(new ElementWrapper(true, grouping));
            return true;
        }
        return false;
    }


    // HARMONY - Subtree of MEASURE/PART
    private boolean harmonyStart() {
        // root
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ROOT)) {
            root = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, root);
            XMLParser.getElements(xmlStreamReader, () -> rootStart(), () -> rootEnd());
        }
        // bass
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.BASS)) {
            bass = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, bass);
            XMLParser.getElements(xmlStreamReader, () -> bassStart(), () -> bassEnd());
        }
        // degree
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.DEGREE)) {
            degree = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, degree);
            XMLParser.getElements(xmlStreamReader, () -> degreeStart(), () -> degreeEnd());
        }
        // frame
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.FRAME)) {
            frame = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, frame);
            XMLParser.getElements(xmlStreamReader, () -> frameStart(), () -> frameEnd());
        }
        // function, kind, inversion offset
        else {
            harmony.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        }
        return false;
    }
    private boolean harmonyEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.HARMONY)) {
            currentMeasure.addToElements(new ElementWrapper(true, harmony));
            return true;
        }
        return false;
    }
    private boolean partHarmonyEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.HARMONY)) {
            currentPart.addToElements(new ElementWrapper(true, harmony));
            return true;
        }
        return false;
    }

    // ROOT - Subtree of HARMONY
    private boolean rootStart() {
        // root-step, root-alter
        root.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean rootEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ROOT)) {
            harmony.addToElements(new ElementWrapper(true, root));
            return true;
        }
        return false;
    }

    // BASS - Subtree of HARMONY
    private boolean bassStart() {
        // bass-step, bass-alter
        bass.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean bassEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.BASS)) {
            harmony.addToElements(new ElementWrapper(true, bass));
            return true;
        }
        return false;
    }

    // DEGREE - Subtree of HARMONY
    private boolean degreeStart() {
        // degree-value, degree-alter, degree-type
        degree.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean degreeEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.DEGREE)) {
            harmony.addToElements(new ElementWrapper(true, degree));
            return true;
        }
        return false;
    }

    // FRAME - Subtree of HARMONY
    private boolean frameStart() {
        // frame-note
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.FRAME_NOTE)) {
            frameNote = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, frameNote);
            XMLParser.getElements(xmlStreamReader, () -> frameNoteStart(), () -> frameNoteEnd());
        }
        // frame-strings, frame-frets, first-fret
        else {
            frame.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        }
        return false;
    }
    private boolean frameEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.FRAME)) {
            harmony.addToElements(new ElementWrapper(true, frame));
            return true;
        }
        return false;
    }

    // FRAME-NOTE - Subtree of FRAME
    private boolean frameNoteStart() {
        // string, fret, fingering, barre
        frameNote.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean frameNoteEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.FRAME_NOTE)) {
            frame.addToElements(new ElementWrapper(true, frameNote));
            return true;
        }
        return false;
    }


    // NOTE - Subtree of MEASURE/PART
    private boolean noteStart() {
        // pitch
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PITCH)) {
            pitch = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, pitch);
            XMLParser.getElements(xmlStreamReader, () -> pitchStart(), () -> pitchEnd());
        }
        // rest
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.REST)) {
            rest = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, rest);
            XMLParser.getElements(xmlStreamReader, () -> restStart(), () -> restEnd());
        }
        // unpitched
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.UNPITCHED)) {
            unpitched = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, unpitched);
            XMLParser.getElements(xmlStreamReader, () -> unpitchedStart(), () -> unpitchedEnd());
        }
        // time-modification
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.TIME_MODIFICATION)) {
            timeModification = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, timeModification);
            XMLParser.getElements(xmlStreamReader, () -> timeModificationStart(), () -> timeModificationEnd());
        }
        // notehead-text
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.NOTEHEAD_TEXT)) {
            noteHeadText = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, noteHeadText);
            XMLParser.getElements(xmlStreamReader, () -> noteHeadTextStart(), () -> noteHeadTextEnd());
        }
        // notations
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.NOTATIONS)) {
            notations = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, notations);
            XMLParser.getElements(xmlStreamReader, () -> notationsStart(), () -> notationsEnd());
        }
        // lyric
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.LYRIC)) {
            lyric = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, lyric);
            XMLParser.getElements(xmlStreamReader, () -> lyricStart(), () -> lyricEnd());
        }
        //play
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PLAY)) {
            play = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, play);
            XMLParser.getElements(xmlStreamReader, () -> playStart(), () -> playNoteEnd());
        }
        // chord, duration, tie, cue, grace, instrument, footnote, level, voice, type, dot, accidental
        // stem, notehead, staff, beam,
        else {
            note.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        }
        return false;
    }
    private boolean noteEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.NOTE)) {
            currentMeasure.addToElements(new ElementWrapper(true, note));
            return true;
        }
        return false;
    }
    private boolean partNoteEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.NOTE)) {
            currentPart.addToElements(new ElementWrapper(true, note));
            return true;
        }
        return false;
    }

    // PITCH - Subtree of NOTE
    private boolean pitchStart() {
        // step, alter, octave
        pitch.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean pitchEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PITCH)) {
            note.addToElements(new ElementWrapper(true, pitch));
            return true;
        }
        return false;
    }

    // REST - Subtree of NOTE
    private boolean restStart() {
        // display-step, display-octave
        rest.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean restEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.REST)) {
            note.addToElements(new ElementWrapper(true, rest));
            return true;
        }
        return false;
    }

    // UNPITCHED - Subtree of NOTE
    private boolean unpitchedStart() {
        // display-step, display-octave
        unpitched.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean unpitchedEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.UNPITCHED)) {
            note.addToElements(new ElementWrapper(true, unpitched));
            return true;
        }
        return false;
    }

    // TIME-MODIFICATION - Subtree of NOTE
    private boolean timeModificationStart() {
        // actual-notes, normal-notes, normal-type, normal-dot
        timeModification.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean timeModificationEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.TIME_MODIFICATION)) {
            note.addToElements(new ElementWrapper(true, timeModification));
            return true;
        }
        return false;
    }

    // NOTEHEAD-TEXT - Subtree of NOTE
    private boolean noteHeadTextStart() {
        // accidental-text, display-text
        noteHeadText.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean noteHeadTextEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.NOTEHEAD_TEXT)) {
            note.addToElements(new ElementWrapper(true, noteHeadText));
            return true;
        }
        return false;
    }

    // NOTATIONS - Subtree of NOTE
    private boolean notationsStart() {
        // articulations
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ARTICULATIONS)) {
            articulations = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, articulations);
            XMLParser.getElements(xmlStreamReader, () -> articulationsStart(), () -> articulationsEnd());
        }
        // dynamics
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.DYNAMICS)) {
            dynamics = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, dynamics);
            XMLParser.getElements(xmlStreamReader, () -> dynamicsStart(), () -> dynamicsNotationsEnd());
        }
        // ornaments
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ORNAMENTS)) {
            ornaments = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, ornaments);
            XMLParser.getElements(xmlStreamReader, () -> ornamentsStart(), () -> ornamentsEnd());
        }
        // technical
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.TECHNICAL)) {
            technical = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, technical);
            XMLParser.getElements(xmlStreamReader, () -> technicalStart(), () -> technicalEnd());
        }
        // tuplet
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.TUPLET)) {
            tuplet = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, tuplet);
            XMLParser.getElements(xmlStreamReader, () -> tupletStart(), () -> tupletEnd());
        }
        // footnote, level, accidental-mark, arpeggiate, fermata, glissando, non-arpeggiate, other-notation,
        // slide, slur, tied
        else {
            notations.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        }

        return false;
    }
    private boolean notationsEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.NOTATIONS)) {
            note.addToElements(new ElementWrapper(true, notations));
            return true;
        }
        return false;
    }

    // ARTICULATIONS - Subtree of NOTATIONS
    private boolean articulationsStart() {
        // accent, breath-mark, caesura, detached-legato, doit, falloff, other-articulation, plop, scoop,
        // spiccato, staccatissimo, staccato, stress, strong-accent, tenuto, unstress
        articulations.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean articulationsEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ARTICULATIONS)) {
            notations.addToElements(new ElementWrapper(true, articulations));
            return true;
        }
        return false;
    }

    // ORNAMENTS - Subtree of NOTATIONS
    private boolean ornamentsStart() {
        // delayed-inverted-turn, delayed-turn, inverted-mordent, mordent, other-ornament, schleifer, shake,
        // tremolo, trill-mark, turn, vertical-turn, wavy-line, accidental-mark
        ornaments.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean ornamentsEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ORNAMENTS)) {
            notations.addToElements(new ElementWrapper(true, ornaments));
            return true;
        }
        return false;
    }

    // TECHNICAL - Subtree of NOTATIONS
    private boolean technicalStart() {
        // arrow
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ARROW)) {
            arrow = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, arrow);
            XMLParser.getElements(xmlStreamReader, () -> arrowStart(), () -> arrowEnd());
        }
        // bend
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.BEND)) {
            bend = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, bend);
            XMLParser.getElements(xmlStreamReader, () -> bendStart(), () -> bendEnd());
        }
        // harmonic
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.HARMONIC)) {
            harmonic = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, harmonic);
            XMLParser.getElements(xmlStreamReader, () -> harmonicStart(), () -> harmonicEnd());
        }
        // hole
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.HOLE)) {
            hole = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, hole);
            XMLParser.getElements(xmlStreamReader, () -> holeStart(), () -> holeEnd());
        }
        // double-tongue, down-bow, fingering, fingernails, fret, hammer-on, handbell, heel, open-string,
        // other-technical, pluck, pull-off, snap-pizzicato, stopped, string, tap, thumb-position, toe,
        // triple-tongue, up-bow
        else {
            technical.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        }
        return false;
    }
    private boolean technicalEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.TECHNICAL)) {
            notations.addToElements(new ElementWrapper(true, technical));
            return true;
        }
        return false;
    }

    // ARROW - Subtree of TECHNICAL
    private boolean arrowStart() {
        // circular-arrow, arrow-direction, arrow-style
        arrow.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean arrowEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ARROW)) {
            technical.addToElements(new ElementWrapper(true, arrow));
            return true;
        }
        return false;
    }

    // BEND - Subtree of TECHNICAL
    private boolean bendStart() {
        // bend-alter, pre-bend, release, with-bar
        bend.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean bendEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.BEND)) {
            technical.addToElements(new ElementWrapper(true, bend));
            return true;
        }
        return false;
    }

    // HARMONIC - Subtree of TECHNICAL
    private boolean harmonicStart() {
        //artificial, natural, base-pitch, sounding-pitch, touching-pitch
        harmonic.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean harmonicEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.HARMONIC)) {
            technical.addToElements(new ElementWrapper(true, harmonic));
            return true;
        }
        return false;
    }

    // HOLE - Subtree of TECHNICAL
    private boolean holeStart() {
        // hole-type, hole-closed, hole-shape
        hole.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean holeEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.HOLE)) {
            technical.addToElements(new ElementWrapper(true, hole));
            return true;
        }
        return false;
    }

    // TUPLET - Subtree of NOTATIONS
    private boolean tupletStart() {
        // tuplet-actual
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.TUPLET_ACTUAL)) {
            tupletActual = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, tupletActual);
            XMLParser.getElements(xmlStreamReader, () -> tupletActualStart(), () -> tupletActualEnd());
        }
        // tuplet-normal
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.TUPLET_NORMAL)) {
            tupletNormal = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, tupletNormal);
            XMLParser.getElements(xmlStreamReader, () -> tupletNormalStart(), () -> tupletNormalEnd());
        }
        return false;
    }
    private boolean tupletEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.TUPLET)) {
            notations.addToElements(new ElementWrapper(true, tuplet));
            return true;
        }
        return false;
    }

    // TUPLET-ACTUAL - Subtree of TUPLET
    private boolean tupletActualStart() {
        // tuplet-number, tuplet-type, tuplet-dot
        tupletActual.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean tupletActualEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.TUPLET_ACTUAL)) {
            tuplet.addToElements(new ElementWrapper(true, tupletActual));
            return true;
        }
        return false;
    }

    // TUPLET-NORMAL - Subtree of TUPLET
    private boolean tupletNormalStart() {
        // tuplet-number, tuplet-type, tuplet-dot
        tupletNormal.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean tupletNormalEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.TUPLET_NORMAL)) {
            tuplet.addToElements(new ElementWrapper(true, tupletNormal));
            return true;
        }
        return false;
    }

    // LYRIC - Subtree of NOTE
    private boolean lyricStart() {
        // extend, humming, laughing, syllabic, text, elision, end-line, end-paragraph, footnote, level
        lyric.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));

        return false;
    }
    private boolean lyricEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.LYRIC)) {
            note.addToElements(new ElementWrapper(true, lyric));
            return true;
        }
        return false;
    }


    // PRINT - Subtree of MEASURE/PART
    private boolean printStart() {
        // page-layout
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PAGE_LAYOUT)) {
            pageLayout = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, pageLayout);
            XMLParser.getElements(xmlStreamReader, () -> pageLayoutStart(), () -> pageLayoutEnd());
        }
        // system-layout
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SYSTEM_LAYOUT)) {
            systemLayout = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, systemLayout);
            XMLParser.getElements(xmlStreamReader, () -> systemLayoutStart(), () -> systemLayoutEnd());
        }
        // staff-layout
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.STAFF_LAYOUT)) {
            staffLayout = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, staffLayout);
            XMLParser.getElements(xmlStreamReader, () -> staffLayoutStart(), () -> staffLayoutEnd());
        }
        // measure-layout
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MEASURE_LAYOUT)) {
            measureLayout = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, measureLayout);
            XMLParser.getElements(xmlStreamReader, () -> measureLayoutStart(), () -> measureLayoutEnd());
        }
        // part-name-display
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_NAME_DISPLAY)) {
            partNameDisplay = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, partNameDisplay);
            XMLParser.getElements(xmlStreamReader, () -> partNameDisplayStart(), () -> partNameDisplayEnd());
        }
        // part-abbreviation-display
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_ABBREVIATION_DISPLAY)) {
            partAbbrevDisplay = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, partAbbrevDisplay);
            XMLParser.getElements(xmlStreamReader, () -> partAbbrevDisplayStart(), () -> partAbbrevDisplayEnd());
        }
        // measure-numbering
        else {
            print.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        }
        return false;
    }
    private boolean printEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PRINT)) {
            currentMeasure.addToElements(new ElementWrapper(true, print));
            return true;
        }
        return false;
    }
    private boolean partPrintEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PRINT)) {
            currentPart.addToElements(new ElementWrapper(true, print));
            return true;
        }
        return false;
    }

    // PAGE-LAYOUT - Subtree of PRINT
    private boolean pageLayoutStart() {
        // page-margins
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PAGE_MARGINS)) {
            pageMargins = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, pageMargins);
            XMLParser.getElements(xmlStreamReader, () -> pageMarginsStart(), () -> pageMarginsEnd());
        }
        // page-height, page-width
        else {
            pageLayout.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        }
        return false;
    }
    private boolean pageLayoutEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PAGE_LAYOUT)) {
            print.addToElements(new ElementWrapper(true, pageLayout));
            return true;
        }
        return false;
    }

    // PAGE-MARGINS - Subtree of PAGE-LAYOUT
    private boolean pageMarginsStart() {
        // left-margin, right-margin, top-margin, bottom-margin
        pageMargins.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean pageMarginsEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PAGE_MARGINS)) {
            pageLayout.addToElements(new ElementWrapper(true, pageMargins));
            return true;
        }
        return false;
    }

    // SYSTEM-LAYOUT - Subtree of PRINT
    private boolean systemLayoutStart() {
        // system-margins
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SYSTEM_MARGINS)) {
            systemMargins = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, systemMargins);
            XMLParser.getElements(xmlStreamReader, () -> systemMarginsStart(), () -> systemMarginsEnd());
        }
        // system-dividers
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SYSTEM_DIVIDERS)) {
            systemDividers = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, systemDividers);
            XMLParser.getElements(xmlStreamReader, () -> systemDividersStart(), () -> systemDividersEnd());
        }
        // system-distance, top-system-distance,
        else {
            systemLayout.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        }
        return false;
    }
    private boolean systemLayoutEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SYSTEM_LAYOUT)) {
            print.addToElements(new ElementWrapper(true, systemLayout));
            return true;
        }
        return false;
    }

    // SYSTEM-MARGINS - Subtree of SYSTEM-LAYOUT
    private boolean systemMarginsStart() {
        // left-margin, right-margin
        systemMargins.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean systemMarginsEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SYSTEM_MARGINS)) {
            systemLayout.addToElements(new ElementWrapper(true, systemMargins));
            return true;
        }
        return false;
    }

    // SYSTEM-DIVIDERS - Subtree of SYSTEM-LAYOUT
    private boolean systemDividersStart() {
        // left-divider, right-divider
        systemDividers.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean systemDividersEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SYSTEM_DIVIDERS)) {
            systemLayout.addToElements(new ElementWrapper(true, systemDividers));
            return true;
        }
        return false;
    }

    // STAFF-LAYOUT - Subtree of PRINT
    private boolean staffLayoutStart() {
        // staff-distance
        staffLayout.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean staffLayoutEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.STAFF_LAYOUT)) {
            print.addToElements(new ElementWrapper(true, staffLayout));
            return true;
        }
        return false;
    }

    // MEASURE-LAYOUT - Subtree of PRINT
    private boolean measureLayoutStart() {
        // measure-distance
        measureLayout.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean measureLayoutEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MEASURE_LAYOUT)) {
            print.addToElements(new ElementWrapper(true, measureLayout));
            return true;
        }
        return false;
    }

    // PART-NAME-DISPLAY - Subtree of PRINT
    private boolean partNameDisplayStart() {
        // accidental-text, display-text
        partNameDisplay.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean partNameDisplayEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_NAME_DISPLAY)) {
            print.addToElements(new ElementWrapper(true, partNameDisplay));
            return true;
        }
        return false;
    }

    // part-abbreviation-display - Subtree of PRINT
    private boolean partAbbrevDisplayStart() {
        // accidental-text, display-text
        partAbbrevDisplay.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean partAbbrevDisplayEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_ABBREVIATION_DISPLAY)) {
            print.addToElements(new ElementWrapper(true, partAbbrevDisplay));
            return true;
        }
        return false;
    }
}
