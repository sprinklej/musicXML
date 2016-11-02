package parser;

import org.codehaus.stax2.XMLStreamReader2;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import parsed.*;


/**
 * Created by sprinklej on 2016-10-02.
 * The shared aspects of partwise and timewise scores
 * -- XML Header Structure --
 * work - {0,1}
 *  work-number
 *  work-title
 *  opus
 * movement-number - {0,1}
 * movement-title - {0,1}
 * identification - {0,1}
 *  creator
 *  rights
 *  encoding
 *  source
 *  relation
 *  miscellaneous
 * defaults - {0,1}
 *  scaling
 *  page-layout
 *  system-layout
 *  staff-layout
 *  appearance
 *  music-font
 *  word-font
 *  lyric-font
 *  lyric-language
 * credit - {0,*}
 *  credit-type
 *  credit-words
 * part-list - {1,1}
 *  part-group
 *  score-part
 */
public class ParseXMLHeader {
    private ParseHelper parseHelper;

    private XMLStreamReader2 xmlStreamReader;
    private Score score;

    //header subtrees
    private ComplexElement work;

    private ComplexElement identification;
    private ComplexElement encoding;
    private ComplexElement miscellaneous;

    private ComplexElement defaults;
    private ComplexElement scaling;
    private ComplexElement pageLayout;
    private ComplexElement pageMargins;
    private ComplexElement systemLayout;
    private ComplexElement systemMargins;
    private ComplexElement systemDividers;
    private ComplexElement staffLayout;
    private ComplexElement appearance;

    private ComplexElement credit;

    private ComplexElement partList;
    private ComplexElement currentGroup;
    private ComplexElement groupNameDisplay;
    private ComplexElement groupAbbreviationDisplay;
    private ComplexElement currentPart;
    private ComplexElement partNameDisplay;
    private ComplexElement partAbbreviationDisplay;
    private ComplexElement scoreInstrument;
    private ComplexElement virtualInstrument;
    private ComplexElement midiInstrument;


    // CONSTRUCTOR
    public ParseXMLHeader(XMLStreamReader2 aXmlStreamReader, Score aScore) {
        xmlStreamReader = aXmlStreamReader;
        score = aScore;
        parseHelper = new ParseHelper();
    }



    // ------------------------- MAIN PARSER FOR THE HEADER PART OF THE XML -------------------------
    public void parseHeader() {
        // work subtree
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.WORK)) { //work Subtree
            work = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, work);
            XMLParser.getElements(xmlStreamReader, () -> workStart(), () -> workEnd());
        }
        // movement number
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MOVEMENT_NUM)) {
            score.setMovementNumberElement(parseHelper.getElement(xmlStreamReader));
        }
        // movement title
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MOVEMENT_TITLE)) {
            score.setMovementTitleElement(parseHelper.getElement(xmlStreamReader));
        }
        // identification subtree
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.IDENTIFICATION)) {
            identification = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, identification);
            XMLParser.getElements(xmlStreamReader, () -> identificationStart(), () -> identificationEnd());
        }
        // defaults
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.DEFAULTS)) {
            defaults = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, defaults);
            XMLParser.getElements(xmlStreamReader, () -> defaultsStart(), () -> defaultsEnd());
        }
        // credit
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.CREDIT)) {
            credit = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, credit);
            XMLParser.getElements(xmlStreamReader, () -> creditStart(), () -> creditEnd());
        }
        // partList subtree
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_LIST)) {
            partList = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, partList);
            XMLParser.getElements(xmlStreamReader, () -> partListStart(), () -> partListEnd());
        }

        // PARSE THE BODY
        // partwise
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART)) {
            ParseXMLBody parseBodyObj = new ParseXMLBody(xmlStreamReader, score);
            parseBodyObj.parseBody();
        }
        // TODO TIMEWISE PARSRING
    }



    // WORK SUBTREE
    // ------------------------- SCORE-HEADER TOP-LEVEL ITEM -------------------------
    private boolean workStart() {
        // opus
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.OPUS)) {
            // Extra work to ensure proper xml link standards
            Element e = parseHelper.getElement(xmlStreamReader);
            e.addToAttributes(new Attribute(XMLConsts.XMLNS + ":" + XMLConsts.XLINK, XMLConsts.XLINKURL));
            work.addToElements(new ElementWrapper(false, e));
        }
        // work-number, work-title
        else {
            work.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        }
        return false;
    }
    private boolean workEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.WORK)) {
            score.setWork(work);
            return true;
        }
        return false;
    }



    // IDENTIFICATION SUBTREE
    // ------------------------- SCORE-HEADER TOP-LEVEL ITEM -------------------------
    private boolean identificationStart() {
        // encoding
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ENCODING)) {
            encoding = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, encoding);
            XMLParser.getElements(xmlStreamReader, () -> encodingStart(), () -> encodingEnd());
        }
        // miscellaneous
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MISCELLANEOUS)) {
            miscellaneous = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, miscellaneous);
            XMLParser.getElements(xmlStreamReader, () -> miscellaneousStart(), () -> miscellaneousEnd());
        }
        // creator, rights, source, relation,
        else {
            identification.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        }
        return false;
    }
    private boolean identificationEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.IDENTIFICATION)) {
            score.setIdentification(identification);
            return true;
        }
        return false;
    }
    private boolean identificationScorePartEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.IDENTIFICATION)) {
            currentPart.addToElements(new ElementWrapper(true, identification));
            return true;
        }
        return false;
    }


    // ENCODING SUBTREE
    private boolean encodingStart() {
        // encoding-date, encoder, software, encoding-description, encoding-description, supports
        encoding.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean encodingEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ENCODING)) {
            identification.addToElements(new ElementWrapper(true, encoding));
            return true;
        }
        return false;
    }


    // MISCELLANEOUS SUBTREE
    private boolean miscellaneousStart() {
        // miscellaneous-field
        miscellaneous.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean miscellaneousEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MISCELLANEOUS)) {
            identification.addToElements(new ElementWrapper(true, miscellaneous));
            return true;
        }
        return false;
    }


    // DEFAULTS SUBTREE
    // ------------------------- SCORE-HEADER TOP-LEVEL ITEM -------------------------
    private boolean defaultsStart() {
        // scaling
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SCALING)) {
            scaling = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, scaling);
            XMLParser.getElements(xmlStreamReader, () -> scalingStart(), () -> scalingEnd());
        }
        // page-layout
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PAGE_LAYOUT)) {
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
        // appearance
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.APPEARANCE)) {
            appearance = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, appearance);
            XMLParser.getElements(xmlStreamReader, () -> appearanceStart(), () -> appearanceEnd());
        }
        // music-font, word-font, lyric-font, lyric-language
        else {
            defaults.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        }
        return false;
    }
    private boolean defaultsEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.DEFAULTS)) {
            score.setDefaults(defaults);
            return true;
        }
        return false;
    }

    // SCALING SUBTREE
    private boolean scalingStart() {
        // millimeters, tenths
        scaling.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean scalingEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SCALING)) {
            defaults.addToElements(new ElementWrapper(true, scaling));
            return true;
        }
        return false;
    }

    // PAGE-LAYOUT SUBTREE
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
            defaults.addToElements(new ElementWrapper(true, pageLayout));
            return true;
        }
        return false;
    }

    // PAGE-MARGINS SUBTREE
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

    // SYSTEM-LAYOUT SUBTREE
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
        // system-distance, top-system-distance
        else {
            systemLayout.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        }
        return false;
    }
    private boolean systemLayoutEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SYSTEM_LAYOUT)) {
            defaults.addToElements(new ElementWrapper(true, systemLayout));
            return true;
        }
        return false;
    }

    // SYSTEM-MARGINS SUBTREE
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

    // SYSTEM-DIVIDERS SUBTREE
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

    // STAFF-LAYOUT SUBTREE
    private boolean staffLayoutStart() {
        // staff-distance
        staffLayout.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean staffLayoutEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.STAFF_LAYOUT)) {
            defaults.addToElements(new ElementWrapper(true, staffLayout));
            return true;
        }
        return false;

    }

    // APPEARANCE SUBTREE
    private boolean appearanceStart() {
        // line-width, note-size, distance, other-appearance
        appearance.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean appearanceEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.APPEARANCE)) {
            defaults.addToElements(new ElementWrapper(true, appearance));
            return true;
        }
        return false;
    }



    // CREDIT SUBTREE
    // ------------------------- SCORE-HEADER TOP-LEVEL ITEM -------------------------
    private boolean creditStart() {
        //link
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.LINK)) {
            // Extra work to ensure proper xml link standards
            Element e = parseHelper.getElement(xmlStreamReader);
            e.addToAttributes(new Attribute(XMLConsts.XMLNS + ":" + XMLConsts.XLINK, XMLConsts.XLINKURL));
            credit.addToElements(new ElementWrapper(false, e));
        }
        // credit-type, bookmark, credit-image, credit-words
        else {
            credit.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        }
        return false;
    }
    private boolean creditEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.CREDIT)) {
            score.addToCredit(new ElementWrapper(true, credit));
            return true;
        }
        return false;
    }



    // PART-LIST SUBTREE
    // ------------------------- SCORE-HEADER TOP-LEVEL ITEM -------------------------
    public boolean partListStart() {
        // part-group
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_GROUP)) {

            for (int i = 0; i < xmlStreamReader.getAttributeCount(); i++) {
                // GROUP START
                if ((xmlStreamReader.getAttributeLocalName(i).contentEquals(XMLConsts.TYPE))
                        && (xmlStreamReader.getAttributeValue(i).contentEquals(XMLConsts.START))) {
                    currentGroup = new ComplexElement(xmlStreamReader.getName().toString());
                    parseHelper.setComplexEAttributes(xmlStreamReader, currentGroup);
                    XMLParser.getElements(xmlStreamReader, () -> partGroupStart(), () -> partGroupEnd());
                    break;
                }
                // GROUP STOP
                else if ((xmlStreamReader.getAttributeLocalName(i).contentEquals(XMLConsts.TYPE))
                        && (xmlStreamReader.getAttributeValue(i).contentEquals(XMLConsts.STOP))) {
                    partList.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
                    break;
                }
            }
        }
        // score-part
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SCORE_PART)) {
            currentPart = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, currentPart);
            XMLParser.getElements(xmlStreamReader, () -> scorePartStart(), () -> scorePartEnd());
        }
        return false;
    }
    public boolean partListEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_LIST)) {
            score.setPartList(partList);
            return true;
        }
        return false;
    }


    // PART-GROUP SUBTREE - is only available when type = "start"
    private boolean partGroupStart() {
        // group-name-display
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.GROUP_NAME_DISPLAY)) {
            groupNameDisplay = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, groupNameDisplay);
            XMLParser.getElements(xmlStreamReader, () -> groupNameDisplayStart(), () -> groupNameDisplayEnd());
        }
        // group-abbreviation-display
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.GROUP_ABBREVIATION_DISPLAY)) {
            groupAbbreviationDisplay = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, groupAbbreviationDisplay);
            XMLParser.getElements(xmlStreamReader, () -> groupAbbreviationDisplayStart(), () -> groupAbbreviationDisplayEnd());
        }
        // group-name, group-abbreviation, group-symbol, group-barline, group-time, footnote, level
        else {
            currentGroup.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        }
        return false;
    }
    private boolean partGroupEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_GROUP)) {
            partList.addToElements(new ElementWrapper(true, currentGroup));
            currentGroup = null;
            return true;
        }
        return false;
    }


    // GROUP-NAME-DISPLAY SUBTREE
    private boolean groupNameDisplayStart() {
        // accidental-text, display-text
        groupNameDisplay.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return  false;
    }
    private boolean groupNameDisplayEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.GROUP_NAME_DISPLAY)) {
            currentGroup.addToElements(new ElementWrapper(true, groupNameDisplay));
            return true;
        }
        return false;
    }


    // GROUP-ABBREVIATION-DISPLAY
    private boolean groupAbbreviationDisplayStart() {
        // accidental-text, display-text
        groupAbbreviationDisplay.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean groupAbbreviationDisplayEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.GROUP_ABBREVIATION_DISPLAY)) {
            currentGroup.addToElements(new ElementWrapper(true, groupAbbreviationDisplay));
            return true;
        }
        return false;
    }


    // SCORE-PART SUBTREE
    private boolean scorePartStart() {
        // identification
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.IDENTIFICATION)) {
            identification = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, identification);
            XMLParser.getElements(xmlStreamReader, () -> identificationStart(), () -> identificationScorePartEnd());
        }
        // part-name-display
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_NAME_DISPLAY)) {
            partNameDisplay = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, partNameDisplay);
            XMLParser.getElements(xmlStreamReader, () -> partNameDisplayStart(), () -> partNameDisplayEnd());
        }
        // part-abbreviation-display
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_ABBREVIATION_DISPLAY)) {
            partAbbreviationDisplay = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, partAbbreviationDisplay);
            XMLParser.getElements(xmlStreamReader, () -> partAbbrevDisplayStart(), () -> partAbbrevDisplayEnd());
        }
        // score-instrument
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SCORE_INSTRUMENT)) {
            scoreInstrument = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, scoreInstrument);
            XMLParser.getElements(xmlStreamReader, () -> scoreInstrumentStart(), () -> scoreInstrumentEnd());
        }
        // midi-instrument
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MIDI_INSTRUMENT)) {
            midiInstrument = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, midiInstrument);
            XMLParser.getElements(xmlStreamReader, () -> midiInstrumentStart(), () -> midiInstrumentEnd());
        }
        // part-name, part-abbreviation, group, midi-device
        else {
            currentPart.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        }
        return false;
    }
    private boolean scorePartEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SCORE_PART)) {
            partList.addToElements(new ElementWrapper(true, currentPart));
            currentPart = null;
            return true;
        }
        return false;
    }

    // PART-NAME-DISPLAY SUBTREE
    private boolean partNameDisplayStart() {
        // accidental-text, display-text
        partNameDisplay.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean partNameDisplayEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_NAME_DISPLAY)) {
            currentPart.addToElements(new ElementWrapper(true, partNameDisplay));
            return true;
        }
        return false;
    }

    // PART-ABBREVIATION-DISPLAY SUBTREE
    private boolean partAbbrevDisplayStart() {
        // accidental-text, display-text
        partAbbreviationDisplay.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean partAbbrevDisplayEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_ABBREVIATION_DISPLAY)) {
            currentPart.addToElements(new ElementWrapper(true, partAbbreviationDisplay));
            return true;
        }
        return false;
    }

    // PART SCORE-INSTRUMENT SUBTREE
    private boolean scoreInstrumentStart() {
        // virtual-instrument
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.VIRTUAL_INSTRUMENT)) {
            virtualInstrument = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, virtualInstrument);
            XMLParser.getElements(xmlStreamReader, () -> virtualInstrumentStart(), () -> virtualInstrumentEnd());
        }
        // instrument-name, instrument-abbreviation, instrument-sound, ensemble, solo,
        else {
            scoreInstrument.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        }
        return false;
    }
    private boolean scoreInstrumentEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SCORE_INSTRUMENT)) {
            currentPart.addToElements(new ElementWrapper(true, scoreInstrument));
            return true;
        }
        return false;
    }

    // PART VIRTUAL-INSTRUMENT SUBTREE
    private boolean virtualInstrumentStart() {
        // virtual-library, virtual-name
        virtualInstrument.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean virtualInstrumentEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.VIRTUAL_INSTRUMENT)) {
            scoreInstrument.addToElements(new ElementWrapper(true, virtualInstrument));
            return true;
        }
        return false;
    }

    // PART MIDI-INSTRUMENTS SUBTREE
    private boolean midiInstrumentStart() {
        // midi-channel, midi-name, midi-bank, midi-program, midi-unpitched, volume, pan, elevation
        midiInstrument.addToElements(new ElementWrapper(false, parseHelper.getElement(xmlStreamReader)));
        return false;
    }
    private boolean midiInstrumentEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MIDI_INSTRUMENT)) {
            currentPart.addToElements(new ElementWrapper(true, midiInstrument));
            return true;
        }
        return false;
    }
}