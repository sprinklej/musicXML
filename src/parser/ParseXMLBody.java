package parser;

import org.codehaus.stax2.XMLStreamReader2;


import parsed.*;

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

    //private Part currentPart;
    //private Measure currentMeasure;
    //private boolean isPartwise;


    // CONSTRUCTOR
    public ParseXMLBody(XMLStreamReader2 aXmlStreamReader, Score aScore) {
        xmlStreamReader = aXmlStreamReader;
        score = aScore;

        parseHelper = new ParseHelper();
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
            attributes = new ComplexElement(xmlStreamReader.getName().toString());
            parseHelper.setComplexEAttributes(xmlStreamReader, currentMeasure);
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
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MEASURE)) {
            ElementWrapper ew = new ElementWrapper(true, attributes);
            currentMeasure.addToElements(ew);
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
            //TODO
        }
        // slash
        else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SLASH)) {
            //TODO
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


    // SLASH - Subtree of MEASURE-STYLE







/*
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



    */






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
