package parser;

import org.codehaus.stax2.XMLStreamReader2;

import javax.swing.plaf.synth.SynthTabbedPaneUI;
import javax.xml.stream.XMLStreamException;

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

    private XMLStreamReader2 xmlStreamReader;
    private Score score;
    //header subtrees
    private Work work;
    private Identification identification;
    private Defaults defaults;
    private Credit credit;


    private Part currentPart = null;
    private Group currentGroup = null;



    // CONSTRUCTOR
    public ParseXMLHeader(XMLStreamReader2 aXmlStreamReader, Score aScore) {
        xmlStreamReader = aXmlStreamReader;
        score = aScore;
    }


    // MAIN PARSER FOR THE HEADER PART OF THE XML
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
            String page = xmlStreamReader.getAttributeValue(0).toString(); //page is only attribute
            credit = new Credit(page);
            XMLParser.getElements(xmlStreamReader, () -> creditStart(), () -> creditEnd());
        }
        // partList subtree
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_LIST)) {
            XMLParser.getElements(xmlStreamReader, () -> partListStart(), () -> partListEnd());
        }
    }



    // WORK SUBTREE
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
                work.setWorkOpus(xmlStreamReader.getText());
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
    private boolean identificationStart() {
        try {
            // creator
            if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.CREATOR)) {
                String type = xmlStreamReader.getAttributeValue(0).toString(); // only attribute is "Type"
                xmlStreamReader.next();
                String creator = xmlStreamReader.getText();

                Creator creatorObj = new Creator(type, creator);
                identification.addToCreator(creatorObj);
            }
            // rights
            else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.RIGHTS)) {
                xmlStreamReader.next();
                identification.addToRights(xmlStreamReader.getText());
            }
            // encoding
            else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ENCODING)) {
                XMLParser.getElements(xmlStreamReader, () -> encodingStart(), () -> encodingEnd());
            }
            //source
            else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SOURCE)) {
                xmlStreamReader.next();
                identification.setSource(xmlStreamReader.getText());
            }
            // relation
            else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.RELATION)) {
                xmlStreamReader.next();
                identification.addToRelation(xmlStreamReader.getText());
            }
            //TODO MISCELLANEOUS **************************************************************


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
            // encoder
            if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ENCODER)) {
                xmlStreamReader.next();
                //identification.set(xmlStreamReader.getText());
            }
            // encoding-date
            else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ENCODING_DATE)) {
                //TODO
            }
            // encoding-description
            if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ENCODING_DESCRIPTION)) {
                //TODO
            }
            // software
            if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SOFTWARE)) {
                //TODO
            }
            // supports - self closing tag
            if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SUPPORTS)) {
                //TODO
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean encodingEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ENCODING)) {
            return true;
        }
        return false;
    }


    // DEFAULTS SUBTREE
    private boolean defaultsStart() {
        // TODO
        // scaling
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SCALING)) {
            // TODO
        }
        // page-layout
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PAGE_LAYOUT)) {
            // TODO
        }
        // system-layout
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SYSTEM_LAYOUT)) {
            // TODO
        }
        // staff-layout 0..*
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.STAFF_LAYOUT)) {
            // TODO
        }
        // appearance
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.APPEARANCE)) {
            // TODO
        }
        // music-font
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MUSIC_FONT)) {
            // TODO
        }
        // word-font
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.WORD_FONT)) {
            // TODO
        }
        // lyric-font 0..*
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.LYRIC_FONT)) {
            // TODO
        }
        // lyric-language 0..*
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.LYRIC_LANGUAGE)) {
            // TODO
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


    // CREDIT SUBTREE
    private boolean creditStart() {
        //TODO
        // credit-type
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.CREDIT_TYPE)) {
            // TODO
        }
        // link
        // bookmark

        //????
        // credit-image
        // credit-words
        // link
        // bookmark
        // credit-words
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
}
