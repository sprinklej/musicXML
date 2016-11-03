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
    private ComplexElement beatRepeat;
    private ComplexElement slash;

    private ComplexElement backup;

    private ComplexElement barline;



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
