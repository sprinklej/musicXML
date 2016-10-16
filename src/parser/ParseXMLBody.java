package parser;

import org.codehaus.stax2.XMLStreamReader2;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;


import parsed.Score;
import parsed.Part;
import parsed.Measure;
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
    private Score score;
    private Part currentPart;
    private Measure currentMeasure;

    XMLStreamReader2 xmlStreamReader;
    private boolean isPartwise;

    public ParseXMLBody(XMLStreamReader2 aXmlStreamReader, Score aScore) {
        xmlStreamReader = aXmlStreamReader;
        score = aScore;

        if (score.getScoreType().contentEquals(XMLConsts.PARTWISE)){
            isPartwise = true;
        } else {
            isPartwise = false;
        }
    }

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
            for (PartListWrapper item : score.getPartList()) {
                if ((item.getIsPart() == true)
                        && (item.getPart().getPartID().contentEquals(num))) { // relying on java to short-circuit
                    currentPart = item.getPart();

                }
            }

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




    // -- APPLIES TO BOTH TIMEWISE AND PARTWISE --



}
