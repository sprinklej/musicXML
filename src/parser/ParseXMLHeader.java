package parser;

import org.codehaus.stax2.XMLStreamReader2;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import parsed.*;
import parsed.header.*;
import parsed.header.credit.Credit;
import parsed.header.defaults.Defaults;
import parsed.header.defaults.PageMargins;
import parsed.header.defaults.StaffLayout;
import parsed.header.identification.Encoding;
import parsed.header.identification.Identification;
import parsed.header.identification.MiscellaneousField;
import parsed.header.identification.Supports;
import parsed.header.work.LinkAttributes;
import parsed.header.work.Work;

import java.util.ArrayList;
//import parsed.header.identification.Identification;
//import parsed.header.work.Work;

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
    //header subtrees
    private Work work;
    private Identification identification;
    private Encoding encoding; // identification subtree
    private Supports supports; // identification subtree
    private Defaults defaults;
    private PageMargins pageMargins; // default subtree
    private StaffLayout staffLayout; // deafult subtree

    private Credit credit;

    private Part currentPart = null;
    private Group currentGroup = null;



    // CONSTRUCTOR
    public ParseXMLHeader(XMLStreamReader2 aXmlStreamReader, Score aScore) {
        xmlStreamReader = aXmlStreamReader;
        score = aScore;
    }


    // ------------------------- MAIN PARSER FOR THE HEADER PART OF THE XML -------------------------
    public void parseHeader() {
        // work subtree
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.WORK)) { //work Subtree
            work = new Work();
            XMLParser.getElements(xmlStreamReader, () -> workStart(), () -> workEnd());
        }
        // movement number
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MOVEMENT_NUM)) {
            try {
                xmlStreamReader.next();
                score.setMovementNumber(xmlStreamReader.getText());
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        }
        // movement title
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MOVEMENT_TITLE)) {
            try {
                xmlStreamReader.next();
                score.setMovementTitle(xmlStreamReader.getText());
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        }
        // identification subtree
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.IDENTIFICATION)) {
            identification = new Identification();
            XMLParser.getElements(xmlStreamReader, () -> identificationStart(), () -> identificationEnd());
        }
        // defaults
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.DEFAULTS)) {
            defaults = new Defaults();
            XMLParser.getElements(xmlStreamReader, () -> defaultsStart(), () -> defaultsEnd());
        }
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
    }



    // WORK SUBTREE
    // ------------------------- SCORE-HEADER TOP-LEVEL ITEM -------------------------
    private boolean workStart() {
        try {
            if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.WORK_NUM)) {
                    xmlStreamReader.next();
                //System.out.println("work-number: " + xmlStreamReader.getText());
                work.setWorkNumber(xmlStreamReader.getText());

            } else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.WORK_TITLE)) {
                xmlStreamReader.next();
                //System.out.println("work-title: " + xmlStreamReader.getText());
                work.setWorkTitle(xmlStreamReader.getText());

            } else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.OPUS)) {
                work.setOpus(true);
                LinkAttributes la = new LinkAttributes();
                for (int i = 0; i < xmlStreamReader.getAttributeCount(); i++) {
                    if (xmlStreamReader.getAttributeLocalName(i).contentEquals(XMLConsts.XLINK_HREF)) {
                        la.setHref(xmlStreamReader.getAttributeValue(i));
                    }
                    if (xmlStreamReader.getAttributeLocalName(i).contentEquals(XMLConsts.XLINK_ROLE)) {
                        la.setRole(xmlStreamReader.getAttributeValue(i));
                    }
                    if (xmlStreamReader.getAttributeLocalName(i).contentEquals(XMLConsts.XLINK_TITLE)) {
                        la.setTitle(xmlStreamReader.getAttributeValue(i));
                    }
                    if (xmlStreamReader.getAttributeLocalName(i).contentEquals(XMLConsts.XLINK_SHOW)) {
                        la.setShow(xmlStreamReader.getAttributeValue(i));
                    }
                    if (xmlStreamReader.getAttributeLocalName(i).contentEquals(XMLConsts.XLINK_ACTUATE)) {
                        la.setActuate(xmlStreamReader.getAttributeValue(i));
                    }
                }
                work.setOpusAttributes(la);
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
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
        try {
            // creator
            if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.CREATOR)) {
                String typeText = null;
                if (xmlStreamReader.getAttributeCount() == 1) { // can have at most 1 attribute "type"
                    typeText = xmlStreamReader.getAttributeValue(0).toString();
                }

                xmlStreamReader.next();
                TypedText creatorObj = new TypedText(XMLConsts.CREATOR, typeText, xmlStreamReader.getText());
                identification.addToCreator(creatorObj);
            }
            // rights
            else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.RIGHTS)) {
                String typeText = null;
                if (xmlStreamReader.getAttributeCount() == 1) { // can have at most 1 attribute "type"
                    typeText = xmlStreamReader.getAttributeValue(0).toString();
                }

                xmlStreamReader.next();
                TypedText rightsObj = new TypedText(XMLConsts.RIGHTS, typeText, xmlStreamReader.getText());
                identification.addToRights(rightsObj);
            }
            // encoding
            else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ENCODING)) {
                encoding = new Encoding();
                XMLParser.getElements(xmlStreamReader, () -> encodingStart(), () -> encodingEnd());
            }
            //source
            else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SOURCE)) {
                xmlStreamReader.next();
                identification.setSource(xmlStreamReader.getText());
            }
            // relation
            else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.RELATION)) {
                String typeText = null;
                if (xmlStreamReader.getAttributeCount() == 1) { // can have at most 1 attribute "type"
                    typeText = xmlStreamReader.getAttributeValue(0).toString();
                }

                xmlStreamReader.next();
                TypedText RelationObj = new TypedText(XMLConsts.RELATION, typeText, xmlStreamReader.getText());
                identification.addToRelation(RelationObj);
            }
            // miscellaneous
            else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MISCELLANEOUS)) {
                XMLParser.getElements(xmlStreamReader, () -> miscellaneousStart(), () -> miscellaneousEnd());
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
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


    // ENCODING SUBTREE
    private boolean encodingStart() {
        try {
            // encoding-date
            if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ENCODING_DATE)) {
                xmlStreamReader.next();
                encoding.addToEncodingDate(xmlStreamReader.getText());
            }
            // encoder
            else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ENCODER)) {
                String typeText = null;
                if (xmlStreamReader.getAttributeCount() == 1) { // can have at most 1 attribute "type"
                    typeText = xmlStreamReader.getAttributeValue(0).toString();
                }

                xmlStreamReader.next();
                TypedText encoderObj = new TypedText(XMLConsts.CREATOR, typeText, xmlStreamReader.getText());
                encoding.addToEncoder(encoderObj);
            }
            // software
            else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SOFTWARE)) {
                xmlStreamReader.next();
                encoding.addToSoftware(xmlStreamReader.getText());
            }
            // encoding-description
            else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ENCODING_DESCRIPTION)) {
                xmlStreamReader.next();
                encoding.addToEncodDescription(xmlStreamReader.getText());
            }
            // supports - self closing tag - <supports attribute="new-system" element="print" type="yes" value="yes"/>
            else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SUPPORTS)) {
                supports = new Supports();
                for (int i = 0; i < xmlStreamReader.getAttributeCount(); i++) { // get attributes
                    // type
                    if (xmlStreamReader.getAttributeLocalName(i).contentEquals(XMLConsts.TYPE)) { // required
                        supports.setTypeAttribute(xmlStreamReader.getAttributeValue(i));
                    }
                    // element
                    if (xmlStreamReader.getAttributeLocalName(i).contentEquals(XMLConsts.ELEMENT)) { // required
                        supports.setElementAttribute(xmlStreamReader.getAttributeValue(i));
                    }
                    // attribute
                    if (xmlStreamReader.getAttributeLocalName(i).contentEquals(XMLConsts.ATTRIBUTE)) {
                        supports.setAttributeAttribute(xmlStreamReader.getAttributeValue(i));
                    }
                    // value
                    if (xmlStreamReader.getAttributeLocalName(i).contentEquals(XMLConsts.VALUE)) {
                        supports.setValueAttribute(xmlStreamReader.getAttributeValue(i));
                    }
                }
                encoding.addToSupports(supports);
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean encodingEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ENCODING)) {
            identification.setEncoding(encoding);
            return true;
        }
        return false;
    }


    // MISCELLANEOUS SUBTREE
    private boolean miscellaneousStart() {
        // miscellaneous-field
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MISCELLANEOUS_FIELD)) {
            MiscellaneousField miscField = new MiscellaneousField(xmlStreamReader.getAttributeValue(0)); // only has 1 attribute "name" which is required
            try {
                xmlStreamReader.next();
                miscField.setText(xmlStreamReader.getText());
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
            identification.addToMiscField(miscField);
        }
        return false;
    }

    private boolean miscellaneousEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MISCELLANEOUS)) {
            return true;
        }
        return false;
    }


    // DEFAULTS SUBTREE
    // ------------------------- SCORE-HEADER TOP-LEVEL ITEM -------------------------
    private boolean defaultsStart() {
        // scaling
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SCALING)) {
            defaults.setScaling(true);
            XMLParser.getElements(xmlStreamReader, () -> scalingStart(), () -> scalingEnd());
        }
        // page-layout
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PAGE_LAYOUT)) {
            defaults.setPageLayout(true);
            XMLParser.getElements(xmlStreamReader, () -> pageLayoutStart(), () -> pageLayoutEnd());
        }
        // system-layout
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SYSTEM_LAYOUT)) {
            defaults.setSystemLayout(true);
            XMLParser.getElements(xmlStreamReader, () -> systemLayoutStart(), () -> systemLayoutEnd());
        }
        // staff-layout 0..*
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.STAFF_LAYOUT)) {
            staffLayout = new StaffLayout();
            if (xmlStreamReader.getAttributeCount() == 1) { // can have at most 1 attribute "type"
                Attribute a = new Attribute(xmlStreamReader.getAttributeName(0).toString(),
                        xmlStreamReader.getAttributeValue(0).toString());
                staffLayout.setAttribute(a);
            }
            XMLParser.getElements(xmlStreamReader, () -> staffLayoutStart(), () -> staffLayoutEnd());
        }
        // appearance
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.APPEARANCE)) {
            defaults.setAppearance(true);
            XMLParser.getElements(xmlStreamReader, () -> appearanceStart(), () -> appearanceEnd());
        }
        // music-font
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MUSIC_FONT)) {
            defaults.setMusicFont(getElement());
        }
        // word-font
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.WORD_FONT)) {
            defaults.setWordFont(getElement());
        }
        // lyric-font 0..*
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.LYRIC_FONT)) {
            defaults.addTolyricFont(getElement());
        }
        // lyric-language 0..*
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.LYRIC_LANGUAGE)) {
            Element e = getElement();
            for(Attribute a:e.getAttribute()) {
                if (a.getAttributeName().contains(XMLConsts.NAMESPACE)) {
                    a.setAttributeName(XMLConsts.XMLLANG);
                }
            }
            defaults.addToLyricLanguage(e);
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
        try {
            if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MILLIMETERS)) {
                xmlStreamReader.next();
                defaults.setScalingMillimeters(xmlStreamReader.getText());
            } else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.TENTHS)) {
                xmlStreamReader.next();
                defaults.setScalingTenths(xmlStreamReader.getText());
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean scalingEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SCALING)) {
            return true;
        }
        return false;
    }

    // PAGE-LAYOUT SUBTREE
    private boolean pageLayoutStart() {
        try {
            if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PAGE_HEIGHT)) {
                xmlStreamReader.next();
                defaults.setPageHeight(xmlStreamReader.getText());
            } else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PAGE_WIDTH)) {
                xmlStreamReader.next();
                defaults.setPageWidth(xmlStreamReader.getText());
            } else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PAGE_MARGINS)) {
                pageMargins = new PageMargins();
                if (xmlStreamReader.getAttributeCount() == 1) { // can have at most 1 attribute
                    Attribute a = new Attribute(xmlStreamReader.getAttributeName(0).toString(),
                            xmlStreamReader.getAttributeValue(0).toString());
                    pageMargins.setAttribute(a);
                }
                XMLParser.getElements(xmlStreamReader, () -> pageMarginsStart(), () -> pageMarginsEnd());
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean pageLayoutEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PAGE_LAYOUT)) {
            return true;
        }
        return false;
    }

    // PAGE-MARGINS SUBTREE
    private boolean pageMarginsStart() {
        try {
            if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.LEFT_MARGIN)) {
                xmlStreamReader.next();
                pageMargins.setLeft(xmlStreamReader.getText());
            } else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.RIGHT_MARGIN)) {
                xmlStreamReader.next();
                pageMargins.setRight(xmlStreamReader.getText());
            } else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.TOP_MARGIN)) {
                xmlStreamReader.next();
                pageMargins.setTop(xmlStreamReader.getText());
            } else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.BOTTOM_MARGIN)) {
                xmlStreamReader.next();
                pageMargins.setBottom(xmlStreamReader.getText());
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean pageMarginsEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PAGE_MARGINS)) {
            defaults.addToPageMargins(pageMargins);
            return true;
        }
        return false;
    }

    // SYSTEM-LAYOUT SUBTREE
    private boolean systemLayoutStart() {
        try {
            if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SYSTEM_MARGINS)) {
                defaults.setSystemMargins(true);
                XMLParser.getElements(xmlStreamReader, () -> systemMarginsStart(), () -> systemMarginsEnd());
            } else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SYSTEM_DISTANCE)) {
                xmlStreamReader.next();
                defaults.setSystemDistance(xmlStreamReader.getText());
            } else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.TOP_SYSTEM_DISTANCE)) {
                xmlStreamReader.next();
                defaults.setTopSysDistance(xmlStreamReader.getText());
            } else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SYSTEM_DIVIDERS)) {
                defaults.setSystemDividers(true);
                XMLParser.getElements(xmlStreamReader, () -> systemDividersStart(), () -> systemDividersEnd());
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean systemLayoutEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SYSTEM_LAYOUT)) {
            return true;
        }
        return false;
    }

    // SYSTEM-MARGINS SUBTREE
    private boolean systemMarginsStart() {
        try {
            if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.LEFT_MARGIN)) {
                xmlStreamReader.next();
                defaults.setLeftSysMargin(xmlStreamReader.getText());
            } else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.RIGHT_MARGIN)) {
                xmlStreamReader.next();
                defaults.setRightSysMargin(xmlStreamReader.getText());
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean systemMarginsEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SYSTEM_MARGINS)) {
            return true;
        }
        return false;
    }

    // SYSTEM-DIVIDERS SUBTREE
    private boolean systemDividersStart() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.LEFT_DIVIDER)) { // self closing element
            for (int i = 0; i < xmlStreamReader.getAttributeCount(); i++) {
                Attribute a = new Attribute(xmlStreamReader.getAttributeName(i).toString(),
                        xmlStreamReader.getAttributeValue(i).toString());
                defaults.addToLeftDivider(a);
            }
        } else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.RIGHT_DIVIDER)) { // self closing element
            for (int i = 0; i < xmlStreamReader.getAttributeCount(); i++) {
                Attribute a = new Attribute(xmlStreamReader.getAttributeName(i).toString(),
                        xmlStreamReader.getAttributeValue(i).toString());
                defaults.addToRightDivider(a);
            }
        }
        return false;
    }
    private boolean systemDividersEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SYSTEM_DIVIDERS)) {
            return true;
        }
        return false;
    }


    // STAFF-LAYOUT SUBTREE
    private boolean staffLayoutStart() {
        try {
            if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.STAFF_DISTANCE)) {
                xmlStreamReader.next();
                staffLayout.setStaffDistance(xmlStreamReader.getText());
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean staffLayoutEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.STAFF_LAYOUT)) {
            defaults.addToStaffLayout(staffLayout);
            return true;
        }
        return false;
    }


    // APPEARANCE SUBTREE
    private boolean appearanceStart() {
        // line-width
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.LINE_WIDTH)) {
            defaults.addToLineWidth(getElement());
        }
        // note-size
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.NOTE_SIZE)) {
            defaults.addToNoteSize(getElement());
        }
        // distance
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.DISTANCE)) {
            defaults.addToDistance(getElement());
        }
        // other-appearance
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.OTHER_APPEARANCE)) {
            defaults.addToOtherAppearance(getElement());
        }
        return false;
    }
    private boolean appearanceEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.APPEARANCE)) {
            return true;
        }
        return false;
    }











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
                for (Attribute a:e.getAttribute()) {
                    a.setAttributeName(a.getAttributeName().replace("{" + XMLConsts.XLINKHREF + "}", XMLConsts.XLINK + ":"));
                }
                Attribute att =  new Attribute(XMLConsts.XMLNS + ":" + XMLConsts.XLINK, XMLConsts.XLINKHREF);
                e.addToAttribute(att);

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
                for (Attribute a:e.getAttribute()) {
                    a.setAttributeName(a.getAttributeName().replace("{" + XMLConsts.NAMESPACEURL + "}", XMLConsts.XML + ":"));
                }
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
            String num = "", type = "";
            for (int i = 0; i < xmlStreamReader.getAttributeCount(); i++) {
                if (xmlStreamReader.getAttributeLocalName(i).contentEquals(XMLConsts.NUMBER)) {
                    num = xmlStreamReader.getAttributeValue(i);
                }

                if (xmlStreamReader.getAttributeLocalName(i).contentEquals(XMLConsts.TYPE)) {
                    type = xmlStreamReader.getAttributeValue(i);
                }
            }

            currentGroup = new Group(num, type);

            if (type.contentEquals(XMLConsts.START)) {
                //System.out.println("*-*part-group start, num = " + num + "*-*");
                XMLParser.getElements(xmlStreamReader, () -> partGroupStart(), () -> partGroupEnd());
            }

            if (type.contentEquals(XMLConsts.STOP)) { // self closing tag - so nothing else to parse
                //System.out.println("-*-part-group stop, num = " + num + "-*-");

                PartListWrapper partListWrapper = new PartListWrapper(false, currentGroup);
                score.addToPartList(partListWrapper);
                currentGroup = null;
            }

        }

        // SCORE-PART
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SCORE_PART)) {
           for (int i = 0; i < xmlStreamReader.getAttributeCount(); i++) {
                if (xmlStreamReader.getAttributeLocalName(i).contentEquals(XMLConsts.ID)) {
                    //System.out.println("---score-part start, ID = " + xmlStreamReader.getAttributeValue(i));

                    // create a new part object
                    currentPart = new Part(xmlStreamReader.getAttributeValue(i));

                    XMLParser.getElements(xmlStreamReader, () -> scorePartStart(), () -> scorePartEnd());
                    break; // found what we are looking for - also we cant loop anymore because the stream has moved on!
                }
            }
        }
        return false;
    }
    public boolean partListEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_LIST)) {
            //System.out.println("--part-list end");
            return true;
        }
        return false;
    }


    // SCORE-PART SUBTREE
    private boolean scorePartStart() {
        try {
            if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_NAME)) {
                xmlStreamReader.next();
                //System.out.println("part-name: " + xmlStreamReader.getText());
                currentPart.setPartName(xmlStreamReader.getText());
            } else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_ABBREV)) {
                xmlStreamReader.next();
                //System.out.println("part-abbrev: " + xmlStreamReader.getText());
                currentPart.setPartAbbrev(xmlStreamReader.getText());
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean scorePartEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SCORE_PART)) {
            //System.out.println("---score-part end");
            PartListWrapper partListWrapper = new PartListWrapper(true, currentPart);
            score.addToPartList(partListWrapper);
            currentPart = null;
            return true;
        }
        return false;
    }

    // PART-GROUP SUBTREE - is only available when type = "start"
    private boolean partGroupStart() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.GROUP_NAME)) {
            try {
                xmlStreamReader.next();
                //System.out.println("group-name: " + xmlStreamReader.getText());
                currentGroup.setGroupName(xmlStreamReader.getText());
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
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










    // gets the attributes and the text-data for an element
    // then returns the element
    private Element getElement(){
        Element element = new Element();
        try {
            element.setElementName(xmlStreamReader.getName().toString());
            for (int i = 0; i < xmlStreamReader.getAttributeCount(); i++) {
                Attribute a = new Attribute(xmlStreamReader.getAttributeName(i).toString(),
                        xmlStreamReader.getAttributeValue(i));
                element.addToAttribute(a);
            }
            int eventType = xmlStreamReader.next();
            if (eventType != XMLEvent.END_ELEMENT) {
                element.setData(xmlStreamReader.getText());
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return element;
    }
}
