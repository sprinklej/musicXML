package parser;

import org.codehaus.stax2.XMLStreamReader2;
import javax.xml.stream.XMLStreamException;

import musicXML.Score;
import musicXML.Part;
/**
 * Created by sprinklej on 2016-10-02.
 *
 * The shared aspects of partwise and timewise scores
 * XML Structure:
 * <work>{0,1}</work>
 * <movement-number>{0,1}</movement-number>
 * <movement-title>{0,1}</movement-title>
 * <identification>{0,1}</identification>
 * <defaults>{0,1}</defaults>
 * <credit page="">{0,unbounded}</credit>
 * <part-list>{1,1}</part-list>
 */
public class ParseXMLHeader {

    private Score score;
    private Part currentPart = null;

    public ParseXMLHeader(Score aScore) {
        score = aScore;
    }


    // WORK SUBTREE
    public boolean workStart(XMLStreamReader2 xmlStreamReader) {
        try {
            if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.WORK_NUM)) {
                    xmlStreamReader.next();
                System.out.println("work-number: " + xmlStreamReader.getText());
                score.setWorkNumber(xmlStreamReader.getText());

            } else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.WORK_TITLE)) {
                xmlStreamReader.next();
                System.out.println("work-title: " + xmlStreamReader.getText());
                score.setWorkTitle(xmlStreamReader.getText());
            }

        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean workChar(XMLStreamReader2 xmlStreamReader) {
        return false;
    }
    public boolean workEnd(XMLStreamReader2 xmlStreamReader) {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.WORK)) {
            System.out.println("--Work end");
            return true;
        }
        return false;
    }

    // MOVEMENT-NUMBER
    public boolean movementNumStart(XMLStreamReader2 xmlStreamReader) {
        return false;
    }
    public boolean movementNumChar(XMLStreamReader2 xmlStreamReader) {
        System.out.println("movement-number: " + xmlStreamReader.getText());
        score.setMovementNumber(xmlStreamReader.getText());
        return false;
    }
    public boolean movementNumEnd(XMLStreamReader2 xmlStreamReader) {
        System.out.println("--movement-number end");
        return true;
    }


    // MOVEMENT-TITLE
    public boolean movementTitleStart(XMLStreamReader2 xmlStreamReader) {
        return false;
    }
    public boolean movementTitleChar(XMLStreamReader2 xmlStreamReader) {
        System.out.println("movement-title: " + xmlStreamReader.getText());
        score.setMovementTitle(xmlStreamReader.getText());
        return false;
    }
    public boolean movementTitleEnd(XMLStreamReader2 xmlStreamReader) {
        System.out.println("--movement-title end");
        return true;
    }


    // IDENTIFICATION SUBTREE
    public boolean identificationStart(XMLStreamReader2 xmlStreamReader) {
        try {
            if ((xmlStreamReader.getName().toString().contentEquals(XMLConsts.CREATOR))
                    && (xmlStreamReader.getAttributeValue(0).contentEquals(XMLConsts.COMPOSER))) {
                xmlStreamReader.next();
                System.out.println("composer: " + xmlStreamReader.getText());
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
    public boolean identificationChar(XMLStreamReader2 xmlStreamReader) {
        return false;
    }
    public boolean identificationEnd(XMLStreamReader2 xmlStreamReader) {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.IDENTIFICATION)) {
            System.out.println("--identification end");
            return true;
        }
        return false;
    }


    // PART-LIST SUBTREE
    public boolean partListStart(XMLStreamReader2 xmlStreamReader) {

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

            if (type.contentEquals(XMLConsts.START)) {
                System.out.println("*-*part-group start, num = " + num + "*-*");
                XMLParser.getElements(xmlStreamReader, () -> partGroupStart(xmlStreamReader),
                        () -> partGroupChar(xmlStreamReader), () -> partGroupEnd(xmlStreamReader));
            }

            if (type.contentEquals(XMLConsts.STOP)) {
                System.out.println("-*-part-group stop, num = " + num + "-*-");
            }

        }

        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SCORE_PART)) {
           for (int i = 0; i < xmlStreamReader.getAttributeCount(); i++) {
                if (xmlStreamReader.getAttributeLocalName(i).contentEquals(XMLConsts.ID)) {
                    System.out.println("---score-part start, ID = " + xmlStreamReader.getAttributeValue(i));

                    // create new part and wrapper object, add to score arraylist
                    currentPart = new Part(xmlStreamReader.getAttributeValue(i));


                    XMLParser.getElements(xmlStreamReader, () -> scorePartStart(xmlStreamReader),
                            () -> scorePartChar(xmlStreamReader), () -> scorePartEnd(xmlStreamReader));
                    break; // found what we are looking for - also we cant loop anymore because the stream has moved on!
                }
            }
        }
        return false;
    }
    public boolean partListChar(XMLStreamReader2 xmlStreamReader) {
        return false;
    }
    public boolean partListEnd(XMLStreamReader2 xmlStreamReader) {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_LIST)) {
            System.out.println("--part-list end");
            return true;
        }
        return false;
    }


    // SCORE-PART SUBTREE
    private boolean scorePartStart(XMLStreamReader2 xmlStreamReader) {
        try {
            if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_NAME)) {
                xmlStreamReader.next();
                System.out.println("part-name: " + xmlStreamReader.getText());
                currentPart.setPartName(xmlStreamReader.getText());
            } else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_ABBREV)) {
                xmlStreamReader.next();
                System.out.println("part-abbrev: " + xmlStreamReader.getText());
                currentPart.setPartAbbrev(xmlStreamReader.getText());
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean scorePartChar(XMLStreamReader2 xmlStreamReader) {
        //System.out.println("text = " + xmlStreamReader.getText());
        return false;
    }
    private boolean scorePartEnd(XMLStreamReader2 xmlStreamReader) {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SCORE_PART)) {
            System.out.println("---score-part end");
            PartListWrapper partListWrapper = new PartListWrapper(true, currentPart);
            score.addToPartList(partListWrapper);
            currentPart = null;
            return true;
        }
        return false;
    }

    // PART-GROUP SUBTREE - only available when type = "start"
    private boolean partGroupStart(XMLStreamReader2 xmlStreamReader) {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.GROUP_NAME)) {
            try {
                xmlStreamReader.next();
                System.out.println("group-name: " + xmlStreamReader.getText());
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean partGroupChar(XMLStreamReader2 xmlStreamReader) {
        return false;
    }

    private boolean partGroupEnd(XMLStreamReader2 xmlStreamReader) {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_GROUP)) {
            //System.out.println("---score-part end");
            return true;
        }
        return false;
    }
}
