package parser;

import org.codehaus.stax2.XMLStreamReader2;


import parsed.*;

import javax.xml.stream.XMLStreamException;

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
    private XMLStreamReader2 xmlStreamReader;

    private ComplexElement currentPart;
    private ComplexElement currentMeasure;

    private ComplexElement currentAttributes;

    //private Part currentPart;
    //private Measure currentMeasure;
    //private boolean isPartwise;

    public ParseXMLBody(XMLStreamReader2 aXmlStreamReader, Score aScore) {
        xmlStreamReader = aXmlStreamReader;
        score = aScore;
/*
        if (score.getScoreType().contentEquals(XMLConsts.PARTWISE)){
            isPartwise = true;
        } else {
            isPartwise = false;

        }*/
    }


    // ------------------------- MAIN PARSER FOR THE BODY PART OF THE XML -------------------------
    public void parseBody() {
        // get top body element either PART or MEASURE
        // part - partwise
        if ((score.getScoreType() == XMLConsts.PARTWISE)
                && (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART))) {
            currentPart = new ComplexElement(xmlStreamReader.getName().toString());
            setComplexEAttributes(currentPart);
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
            setComplexEAttributes(currentMeasure);
            XMLParser.getElements(xmlStreamReader, () -> partMeasureStart(), () -> partMeasureEnd());
        }
        return false;
    }
    private boolean partwiseEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART)) {
            ElementWrapper ew = new ElementWrapper(true, currentPart);
            score.addToBody(ew);
            currentPart = null;
            return true;
        }
        return false;
    }


    // MEASURE - SUBTREE OF PART
    private boolean partMeasureStart() {
        // attributes
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.ATTRIBUTES)) {
            currentAttributes = new ComplexElement(xmlStreamReader.getName().toString());
            setComplexEAttributes(currentMeasure);
            XMLParser.getElements(xmlStreamReader, () -> attributesStart(), () -> partAttributesEnd());
        }
        // backup
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.BACKUP)) {
            // TODO complex
        }
        // barline
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.BARLINE)) {
            // TODO complex
        }
        // bookmark
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.BOOKMARK)) {
            // TODO
        }
        // direction
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.DIRECTION)) {
            // TODO COMPLEX
        }
        // figured-base
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.FIGURED_BASE)) {
            // TODO COMPLEX
        }
        // forward
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.FORWARD)) {
            // TODO COMPLEX
        }
        // grouping
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.GROUPING)) {
            // TODO COMPLEX
        }
        // harmony
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.HARMONY)) {
            // TODO COMPLEX
        }
        // link
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.LINK)) {
            // TODO
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
        return false;
    }
    private boolean partMeasureEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MEASURE)) {
            ElementWrapper ew = new ElementWrapper(true, currentMeasure);
            currentPart.addToElements(ew);
            currentMeasure = null;
            return true;
        }
        return false;
    }



    // ATTRIBUTES
    private boolean attributesStart() {
        // footnote
        // TODO
        // level
        // TODO
        // divisions
        // TODO
        // key
        // TODO
        // time
        // TODO
        // staves
        // TODO
        // part-symbol
        // TODO
        // instruments
        // TODO
        // clef
        // TODO
        // staff-details
        // TODO
        // TRANSPOSE
        // TODO
        // directive
        // TODO
        // measure-style
        // TODO

        return false;
    }
    private boolean partAttributesEnd() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MEASURE)) {
            ElementWrapper ew = new ElementWrapper(true, currentAttributes);
            currentMeasure.addToElements(ew);
            currentAttributes = null;
            return true;
        }
        return false;
    }




    // ------------------------- HELPER METHODS -------------------------
    // sets the attributes for a complex element
    private void setComplexEAttributes(ComplexElement ce) {
        for (int i = 0; i < xmlStreamReader.getAttributeCount(); i++) {
            ce.addToAttributes(getAttribute(i));
        }
    }
    // sets the attributes for an element
    private void setElementAttributes(Element e) {
        for (int i = 0; i < xmlStreamReader.getAttributeCount(); i++) {
            e.addToAttributes(getAttribute(i));
        }
    }
    // creates an attribute object and returns it
    private Attribute getAttribute(int i) {
        return new Attribute(xmlStreamReader.getAttributeName(i).toString(),
                xmlStreamReader.getAttributeValue(i).toString());
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
