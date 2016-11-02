package parser;

import org.codehaus.stax2.XMLStreamReader2;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import parsed.*;
import parsed.header.credit.Credit;
import parsed.header.defaults.Defaults;
import parsed.header.defaults.PageMargins;
import parsed.header.defaults.StaffLayout;
import parsed.header.identification.*;
import parsed.header.partlist.Group;
import parsed.header.partlist.MidiInstrument;


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

    private XMLStreamReader2 xmlStreamReader;
    private Score score;

    // OLD WAY - KILLING
    //header subtrees
    //private Defaults defaults;
    //private PageMargins pageMargins; // default subtree
    //private StaffLayout staffLayout; // deafult subtree

    private Credit credit;

    private Part currentPart = null;
    private MidiInstrument midiInst;
    private Group currentGroup = null;


    // NEW WAY
    private ParseHelper parseHelper;

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
        // TODO DONE REFACTORING UPTO HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        /*
        // credit
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.CREDIT)) {
            credit = new Credit();
            if (xmlStreamReader.getAttributeCount() == 1) { // page only has 1 attribute
                Attribute a = new Attribute(xmlStreamReader.getAttributeName(0).toString(),
                        xmlStreamReader.getAttributeValue(0).toString());
                credit.setPageAttribute(a);
            }
            XMLParser.getElements(xmlStreamReader, () -> creditStart(), () -> creditEnd());
        }
        // partList subtree
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_LIST)) {
            XMLParser.getElements(xmlStreamReader, () -> partListStart(), () -> partListEnd());
        }
*/
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
            // Extra work to ensure proper xml standards
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


/*
    // CREDIT SUBTREE
    // ------------------------- SCORE-HEADER TOP-LEVEL ITEM -------------------------
    private boolean creditStart() {
        try {
            // credit-type
            if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.CREDIT_TYPE)) {
                xmlStreamReader.next();
                credit.addToCreditType(xmlStreamReader.getText());
            }
            // link
            else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.LINK)) {
                Element e = getElement();
                for (Attribute a:e.getAttributes()) {
                    a.setAttributeName(a.getAttributeName().replace("{" + XMLConsts.XLINKHREF + "}", XMLConsts.XLINK + ":"));
                }
                Attribute att =  new Attribute(XMLConsts.XMLNS + ":" + XMLConsts.XLINK, XMLConsts.XLINKHREF);
                e.addToAttributes(att);

                credit.addToLink(e);
            }
            // bookmark
            else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.BOOKMARK)) {
                credit.addToBookmark(getElement());
            }
            // credit-image
            else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.CREDIT_IMAGE)) {
                credit.setCreditImage(getElement());
            }
            // credit-words
            else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.CREDIT_WORDS)) {
                Element e = getElement();
                credit.setCreditWords(e);
            }
            // TODO link,bookmark,credit-words???
        } catch (XMLStreamException e) {
            e.printStackTrace();
    }
        return false;
    }
    private boolean creditEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.CREDIT)) {
            score.addToCredit(credit);
            return true;
        }
        return false;
    }



    // PART-LIST SUBTREE
    // ------------------------- SCORE-HEADER TOP-LEVEL ITEM -------------------------
    public boolean partListStart() {
        // PART-GROUP
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_GROUP)) {
            currentGroup = new Group();
            String type = "";
            for (int i = 0; i < xmlStreamReader.getAttributeCount(); i++) {
                Attribute a = new Attribute(xmlStreamReader.getAttributeLocalName(i).toString(),
                        xmlStreamReader.getAttributeValue(i));
                currentGroup.addToGroupAttributes(a);

                if (xmlStreamReader.getAttributeLocalName(i).contentEquals(XMLConsts.TYPE)) {
                    type = xmlStreamReader.getAttributeValue(i);
                }
            }
            // determine if it is the "start" or "stop" of a group
            if (type.contentEquals(XMLConsts.START)) {
                XMLParser.getElements(xmlStreamReader, () -> partGroupStart(), () -> partGroupEnd());
            }
            if (type.contentEquals(XMLConsts.STOP)) { // self closing tag - so nothing else to parse
                PartListWrapper partListWrapper = new PartListWrapper(false, currentGroup);
                score.addToPartList(partListWrapper);
                currentGroup = null;
            }
        }
        // SCORE-PART
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SCORE_PART)) {
            currentPart = new Part();
            if (xmlStreamReader.getAttributeCount() == 1) { // only has 1 attribute that is mandatory
                Attribute a = new Attribute(xmlStreamReader.getAttributeLocalName(0).toString(),
                        xmlStreamReader.getAttributeValue(0));
                currentPart.setPartIDAttribute(a);
            }
            XMLParser.getElements(xmlStreamReader, () -> scorePartStart(), () -> scorePartEnd());
        }
        return false;
    }
    public boolean partListEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_LIST)) {
            System.out.println("PARTLIST END");
            return true;
        }
        return false;
    }


    // SCORE-PART SUBTREE
    private boolean scorePartStart() {
        // identification
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.IDENTIFICATION)) {
            identification = new Identification();
            XMLParser.getElements(xmlStreamReader, () -> identificationStart(), () -> identificationScorePartEnd());
        }
        // part-name - must occur
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_NAME)) {
            currentPart.setPartName(getElement());
        }
        // part-name-display
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_NAME_DISPLAY)) {
            currentPart.setPartNameDisplay(true);
            if (xmlStreamReader.getAttributeCount() == 1) {
                Attribute a = new Attribute(xmlStreamReader.getAttributeLocalName(0).toString(),
                        xmlStreamReader.getAttributeValue(0));
                currentPart.setPartNameDispAttribute(a);
            }
            XMLParser.getElements(xmlStreamReader, () -> partNameDisplayStart(), () -> partNameDisplayEnd());
        }
        // part-abbreviation
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_ABBREVIATION)) {
            currentPart.setPartAbbreviation(getElement());
        }
        // part-abbreviation-display
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_ABBREVIATION_DISPLAY)) {
            currentPart.setPartAbbreviationDisplay(true);
            if (xmlStreamReader.getAttributeCount() == 1) { // set attribute
                Attribute a = new Attribute(xmlStreamReader.getAttributeLocalName(0).toString(),
                        xmlStreamReader.getAttributeValue(0));
                currentPart.setPartAbbrevDispAttribute(a);
            }
            XMLParser.getElements(xmlStreamReader, () -> partAbbrevDisplayStart(), () -> partAbbrevDisplayEnd());
        }
        // group
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.GROUP)) {
            currentPart.addToGroup(getElement());
        }
        // score-instrument
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SCORE_INSTRUMENT)) {
            currentPart.setScoreInstrument(true);
            if (xmlStreamReader.getAttributeCount() == 1) { // set attribute
                Attribute a = new Attribute(xmlStreamReader.getAttributeLocalName(0).toString(),
                        xmlStreamReader.getAttributeValue(0));
                currentPart.setScoreInstAttribute(a);
            }
            XMLParser.getElements(xmlStreamReader, () -> scoreInstrumentStart(), () -> scoreInstrumentEnd());
        }
        // midi-device
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MIDI_DEVICE)) {
            currentPart.addToMidiDevice(getElement());
        }
        // midi-instrument
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MIDI_INSTRUMENT)) {
            midiInst = new MidiInstrument();
            if (xmlStreamReader.getAttributeCount() == 1) { // set attribute
                Attribute a = new Attribute(xmlStreamReader.getAttributeLocalName(0).toString(),
                        xmlStreamReader.getAttributeValue(0));
                midiInst.setMidiAttribute(a);
            }
            XMLParser.getElements(xmlStreamReader, () -> midiInstrumentStart(), () -> midiInstrumentEnd());
        }

        return false;
    }
    private boolean scorePartEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SCORE_PART)) {
            PartListWrapper partListWrapper = new PartListWrapper(true, currentPart);
            score.addToPartList(partListWrapper);
            currentPart = null;
            return true;
        }
        return false;
    }

    // PART-NAME-DISPLAY SUBTREE
    private boolean partNameDisplayStart() {
        currentPart.addToPartNameDispElements(getElement());
        return false;
    }
    private boolean partNameDisplayEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_NAME_DISPLAY)) {
            return true;
        }
        return false;
    }

    // PART-ABBREVIATION-DISPLAY SUBTREE
    private boolean partAbbrevDisplayStart() {
        currentPart.addToPartAbbrevDispElements(getElement());
        return false;
    }
    private boolean partAbbrevDisplayEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_ABBREVIATION_DISPLAY)) {
            return true;
        }
        return false;
    }

    // PART SCORE-INSTRUMENT SUBTREE
    private boolean scoreInstrumentStart() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.VIRTUAL_INSTRUMENT)) { // NO attributes
            currentPart.setVirtualInstrument(true);
            XMLParser.getElements(xmlStreamReader, () -> virtualInstrumentStart(), () -> virtualInstrumentEnd());
        } else {
            currentPart.addToScoreInstElements(getElement());
        }
        return false;
    }
    private boolean scoreInstrumentEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SCORE_INSTRUMENT)) {
            return true;
        }
        return false;
    }

    // PART VIRTUAL-INSTRUMENT SUBTREE
    private boolean virtualInstrumentStart() {
        currentPart.addToVirtualInstElements(getElement());
        return false;
    }
    private boolean virtualInstrumentEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.VIRTUAL_INSTRUMENT)) {
            return true;
        }
        return false;
    }

    // PART MIDI-INSTRUMENTS SUBTREE
    private boolean midiInstrumentStart() {
        midiInst.addToMidiInstrumentElements(getElement());
        return false;
    }
    private boolean midiInstrumentEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MIDI_INSTRUMENT)) {
            currentPart.addToMidiInstruments(midiInst);
            midiInst = null;
            return true;
        }
        return false;
    }


    // PART-GROUP SUBTREE - is only available when type = "start"
    private boolean partGroupStart() {
        // group-name
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.GROUP_NAME)) {
            currentGroup.setGroupName(getElement());
        }
        // group-name-display
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.GROUP_NAME_DISPLAY)) {
            currentGroup.setGroupNameDisplay(true);
            if (xmlStreamReader.getAttributeCount() == 1) { // can have at most 1 attribute
                Attribute a = new Attribute(xmlStreamReader.getAttributeLocalName(0).toString(),
                        xmlStreamReader.getAttributeValue(0));
                currentGroup.setGroupNameDispAttribute(a);
            }
            XMLParser.getElements(xmlStreamReader, () -> groupNameDisplayStart(), () -> groupNameDisplayEnd());
        }
        // group-abbreviation
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.GROUP_ABBREVIATION)) {
            currentGroup.setGroupAbbreviation(getElement());
        }
        // group-abbreviation-display
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.GROUP_ABBREVIATION_DISPLAY)) {
            currentGroup.setGroupAbbreviationDisplay(true);
            if (xmlStreamReader.getAttributeCount() == 1) {
                Attribute a = new Attribute(xmlStreamReader.getAttributeLocalName(0).toString(),
                        xmlStreamReader.getAttributeValue(0));
                currentGroup.setGroupAbbrevDispAttribute(a);
            }
            XMLParser.getElements(xmlStreamReader, () -> groupAbbreviationDisplayStart(), () -> groupAbbreviationDisplayEnd());
        }
        // group-symbol
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.GROUP_SYMBOL)) {
            currentGroup.setGroupSymbol(getElement());
        }
        // group-barline
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.GROUP_BARLINE)) {
            currentGroup.setGroupBarline(getElement());
        }
        // group-time
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.GROUP_TIME)) {
            currentGroup.setGroupTime(getElement());
        }
        // footnote
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.FOOTNOTE)) {
            currentGroup.setFootnote(getElement());
        }
        // level
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.LEVEL)) {
            currentGroup.setLevel(getElement());
        }

        return false;
    }
    private boolean partGroupEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_GROUP)) {
            PartListWrapper partListWrapper = new PartListWrapper(false, currentGroup);
            score.addToPartList(partListWrapper);
            currentGroup = null;
            return true;
        }
        return false;
    }


    // GROUP-NAME-DISPLAY SUBTREE
    private boolean groupNameDisplayStart() {
        // accidental-text
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ACCIDENTAL_TEXT)) {
            currentGroup.addToAcdntlORDsplyText(getElement());
        }
        // display-text
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.DISPLAY_TEXT)) {
            currentGroup.addToAcdntlORDsplyText(getElement());
        }
        return  false;
    }
    private boolean groupNameDisplayEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.GROUP_NAME_DISPLAY)) {
            return true;
        }
        return false;
    }


    // GROUP-ABBREVIATION-DISPLAY
    private boolean groupAbbreviationDisplayStart() {
        // accidental-text
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ACCIDENTAL_TEXT)) {
            currentGroup.addToGroupAbbrevElements(getElement());
        }
        // display-text
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.DISPLAY_TEXT)) {
            currentGroup.addToGroupAbbrevElements(getElement());
        }
        return false;
    }
    private boolean groupAbbreviationDisplayEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.GROUP_ABBREVIATION_DISPLAY)) {
            return true;
        }
        return false;
    }
    */
}
