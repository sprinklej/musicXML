package parser;

import org.codehaus.stax2.XMLStreamReader2;


import parsed.*;

import javax.xml.stream.events.XMLEvent;

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

    //private Part currentPart;
    //private Measure currentMeasure;
    //private boolean isPartwise;


    // CONSTRUCTOR
    public ParseXMLBody(XMLStreamReader2 aXmlStreamReader) {
        xmlStreamReader = aXmlStreamReader;
        parseHelper = new ParseHelper();
/*
        if (score.getScoreType().contentEquals(XMLConsts.PARTWISE)){
            isPartwise = true;
        } else {
            isPartwise = false;

        }*/
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
            // TODO
        }
    }



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
            currentPart = null;
            return true;
        }
        return false;
    }


    // MEASURE - SUBTREE OF PART
    private boolean partMeasureStart() {
        // attributes
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ATTRIBUTES)) {
            attributes = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, currentMeasure);
            XMLParser.getElements(xmlStreamReader, () -> attributesStart(), () -> partAttributesEnd());
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
        // note
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.NOTE)) {
            // TODO COMPLEX
        }
        // print
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PRINT)) {
            // TODO COMPLEX
        }
        // sound
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SOUND)) {
            // TODO COMPLEX
        }
        // bookmark, link
        else {
            currentMeasure.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        }
        return false;
    }
    private boolean partMeasureEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MEASURE)) {
            currentPart.addToElements(new ElementWrapper(true, currentMeasure));
            currentMeasure = null;
            return true;
        }
        return false;
    }



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
    private boolean partAttributesEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ATTRIBUTES)) {
            currentMeasure.addToElements(new ElementWrapper(true, attributes));
            attributes = null;
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


    // HARMONY - Subtree of MEASURE/PART
    private boolean harmonyStart() {
        // TODO WORKING RIGHT HERE
        //else {
        harmony.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        //}
        return false;
    }
    private boolean harmonyEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.HARMONY)) {
            currentMeasure.addToElements(new ElementWrapper(true, harmony));
            return true;
        }
        return false;
    }

/*
    // MAIN PARSER FOR A PARTWISE BODY
    public void partwiseBody() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART)) {
            // get ID number
            String num = "";
            for (int i = 0; i < xmlStreamReader.getAttributeCount(); i++) {
                if (xmlStreamReader.getAttributeLocalName(i).contentEquals(XMLConsts.ID)) {
                    num = xmlStreamReader.getAttributeValue(i);
                    break;
                }
            }

            // find the part in the partlist where the ID matches

           // for (PartListWrapper item : score.getPartList()) {
             //   if ((item.getIsPart() == true)
               //         && (item.getPart().getPartID().contentEquals(num))) { // relying on java to short-circuit
                 //   currentPart = item.getPart();
               // }
           // }


            System.out.println("PART: " + num + " -START-");
            XMLParser.getElements(xmlStreamReader, () -> partwisePartStart(), () -> partwisePartEnd());
        }
    }

    public void timewiseBody() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MEASURE)) {
            // get the measure number and potentially width
            String num = "";
            String width = "";
            for (int i = 0; i < xmlStreamReader.getAttributeCount(); i++) {
                if (xmlStreamReader.getAttributeLocalName(i).contentEquals(XMLConsts.NUMBER)) {
                    num = xmlStreamReader.getAttributeValue(i);
                } else if (xmlStreamReader.getAttributeLocalName(i).contentEquals(XMLConsts.WIDTH)) {
                    width = xmlStreamReader.getAttributeValue(i);
                }
            }

            // create measure object
            currentMeasure = new Measure(num, width);
        }

    }




    // -- PARTWISE SPECIFIC --
    // PARTWISE PART
    private boolean partwisePartStart() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MEASURE)) {
            // get the measure number and potentially width
            String num = "";
            String width = "";
            for (int i = 0; i < xmlStreamReader.getAttributeCount(); i++) {
                if (xmlStreamReader.getAttributeLocalName(i).contentEquals(XMLConsts.NUMBER)) {
                    num = xmlStreamReader.getAttributeValue(i);
                } else if (xmlStreamReader.getAttributeLocalName(i).contentEquals(XMLConsts.WIDTH)) {
                    width = xmlStreamReader.getAttributeValue(i);
                }
            }
            System.out.println("MEASURE: " + num + " WIDTH: " + width + " -START-");
            // create measure object
            //currentMeasure = new Measure(num, width);
        }

        XMLParser.getElements(xmlStreamReader, () -> partwiseMeasureStart(), () -> partwiseMeasureEnd());

        return false;
    }

    private boolean partwisePartEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART)) {
            System.out.println("--PART END--");
            return true;
        }
        return false;
    }


    // -- TIMEWISE SPECIFIC --
    // PARTWISE MEASURE
    private boolean partwiseMeasureStart() {
        return false;
    }

    private boolean partwiseMeasureEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MEASURE)) {
            System.out.println("MEASURE - end");
            return true;
        }
        return false;
    }
    */
}
