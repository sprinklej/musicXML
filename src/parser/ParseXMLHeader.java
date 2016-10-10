package parser;

import org.codehaus.stax2.XMLStreamReader2;
import javax.xml.stream.XMLStreamException;

import parsed.Score;
import parsed.Part;
import parsed.Group;
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
    private Part currentPart = null;
    private Group currentGroup = null;

    public ParseXMLHeader(XMLStreamReader2 aXmlStreamReader, Score aScore) {
        xmlStreamReader = aXmlStreamReader;
        score = aScore;
    }


    // MAIN PARSER FOR THE HEADER
    public void parseHeader() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.WORK)) { //work Subtree
            //System.out.println("--Work SUBTREE start");
            XMLParser.getElements(xmlStreamReader, () -> workStart(), () -> workChar(), () -> workEnd());
        }

        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MOVEMENT_NUM)) {
            //System.out.println("--movement-number start");
            XMLParser.getElements(xmlStreamReader, () -> movementNumStart(), () -> movementNumChar(), () -> movementNumEnd());
        }


        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MOVEMENT_TITLE)) {
            //System.out.println("--movement-title start");
            XMLParser.getElements(xmlStreamReader, () -> movementTitleStart(), () -> movementTitleChar(), () -> movementTitleEnd());
        }

        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.IDENTIFICATION)) { // identification subtree
            //System.out.println("--identification SUBTREE start");
            XMLParser.getElements(xmlStreamReader, () -> identificationStart(), () -> identificationChar(),
                    () -> identificationEnd());
        }

        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_LIST)) {
            //System.out.println("--part-list SUBTREE start");
            XMLParser.getElements(xmlStreamReader, () -> partListStart(), () -> partListChar(),
                    () -> partListEnd());
        }
        // if... DEFAULT
        // if... CREDIT
    }





    // WORK SUBTREE
    private boolean workStart() {
        try {
            if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.WORK_NUM)) {
                    xmlStreamReader.next();
                //System.out.println("work-number: " + xmlStreamReader.getText());
                score.setWorkNumber(xmlStreamReader.getText());

            } else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.WORK_TITLE)) {
                xmlStreamReader.next();
                //System.out.println("work-title: " + xmlStreamReader.getText());
                score.setWorkTitle(xmlStreamReader.getText());
            }

        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean workChar() {
        return false;
    }
    private boolean workEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.WORK)) {
            //System.out.println("--Work end");
            return true;
        }
        return false;
    }



    // MOVEMENT-NUMBER  - no subtree just grab the characters
    private boolean movementNumStart() {
        return false;
    }
    private boolean movementNumChar() {
        //System.out.println("movement-number: " + xmlStreamReader.getText());
        score.setMovementNumber(xmlStreamReader.getText());
        return false;
    }
    private boolean movementNumEnd() {
        //System.out.println("--movement-number end");
        return true;
    }



    // MOVEMENT-TITLE  - no subtree just grab the characters
    public boolean movementTitleStart() {
        return false;
    }
    public boolean movementTitleChar() {
        //System.out.println("movement-title: " + xmlStreamReader.getText());
        score.setMovementTitle(xmlStreamReader.getText());
        return false;
    }
    public boolean movementTitleEnd() {
        //System.out.println("--movement-title end");
        return true;
    }


    // IDENTIFICATION SUBTREE
    private boolean identificationStart() {
        try {
            if ((xmlStreamReader.getName().toString().contentEquals(XMLConsts.CREATOR))
                    && (xmlStreamReader.getAttributeValue(0).contentEquals(XMLConsts.COMPOSER))) {
                xmlStreamReader.next();
                //System.out.println("composer: " + xmlStreamReader.getText());
                score.setCreatorComposer(xmlStreamReader.getText());
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return false;

        // OTHER PARTS OF SUBTREE:
        // creator - lyricist
        // rights (copyright info)
        // encoding - SUBTREE: software, encoding date, supports
    }
    private boolean identificationChar() {
        return false;
    }
    private boolean identificationEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.IDENTIFICATION)) {
            //System.out.println("--identification end");
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
                XMLParser.getElements(xmlStreamReader, () -> partGroupStart(), () -> partGroupChar(),
                        () -> partGroupEnd());
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

                    XMLParser.getElements(xmlStreamReader, () -> scorePartStart(), () -> scorePartChar(),
                            () -> scorePartEnd());
                    break; // found what we are looking for - also we cant loop anymore because the stream has moved on!
                }
            }
        }
        return false;
    }
    public boolean partListChar() {
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
    private boolean scorePartChar() {
        //System.out.println("text = " + xmlStreamReader.getText());
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
    private boolean partGroupChar() {
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
