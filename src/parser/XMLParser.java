package parser;

import java.io.*;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import musicXML.MusicXMLFile;
import musicXML.Score;
import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.XMLStreamReader2;


//http://stackoverflow.com/questions/2186931/java-pass-method-as-parameter
//http://stackoverflow.com/questions/4685563/how-to-pass-a-function-as-a-parameter-in-java
//http://stackoverflow.com/questions/2186931/java-pass-method-as-parameter
//http://stackoverflow.com/questions/4685435/what-is-the-cloest-thing-to-a-function-pointer-in-java

/*
@FunctionalInterface
interface Interface {
    //Can only have 1 abstract method - therefore they must all return true/false
    //http://stackoverflow.com/questions/23682243/lambda-can-only-be-used-with-functional-interface
    public boolean interfaceMethod();
}
*/

/**
 * Created by sprinklej on 2016-09-23.
 */
public class XMLParser {
    private MusicXMLFile currentSong = null;
    private Score currentScore = null;
    private XMLStreamReader2 xmlStreamReader = null;
    private Score score = null;

    // constructor
    public XMLParser(MusicXMLFile aCurrentSong) {
        currentSong = aCurrentSong;
    }

    // Start Parsing

    public void startParsing() throws XMLStreamException {
        String xmlFileName = currentSong.getFilePath();
        InputStream xmlInputStream = getClass().getResourceAsStream(xmlFileName);
        XMLInputFactory2 xmlInputFactory = (XMLInputFactory2)XMLInputFactory.newInstance();
        xmlInputFactory.setProperty(XMLInputFactory2.SUPPORT_DTD, false);   //do not read DTD - TODO read local copy
        //xmlInputFactory.setProperty(XMLInputFactory2.P_DTD_OVERRIDE,
        //SchemaFactory.newInstance("/Users/sprinklej/Downloads/MusicXML/musicxml30/musicxml.xsd"));

        //XMLStreamReader2 xmlStreamReader = null;
        try {
            xmlStreamReader = (XMLStreamReader2) xmlInputFactory.createXMLStreamReader(xmlFileName, new FileInputStream(xmlFileName));
            //xmlStreamReader.validateAgainst(schemaFromDTD);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("ERROR: File not found");
            return;
        }

        getElements(xmlStreamReader, () -> scoreStart(), () -> scoreChar(), () -> scoreEnd());

        //temp
        System.out.println(score.toString());
        java.util.ArrayList<PartListWrapper> list = score.getPartList();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getPart().toString());
        }
        /*
        while(xmlStreamReader.hasNext()){
            int eventType = xmlStreamReader.next();
            switch (eventType) {
                case XMLEvent.START_ELEMENT:
                    System.out.print("<" + xmlStreamReader.getName().toString() + ">");
                    if ((xmlStreamReader.getName().toString() == XMLConsts.PARTWISE) ||
                            (xmlStreamReader.getName().toString() == XMLConsts.TIMEWISE)) {
                        parseScoreHeader(xmlStreamReader);
                    }

                    if (xmlStreamReader.getName().toString() == XMLConsts.PARTWISE) {
                        System.out.println("PARTWISE!!!!!");
                    } else if (xmlStreamReader.getName().toString() == XMLConsts.TIMEWISE) {
                        System.out.println("TIMEWISE!!!!!");
                    }
                    break;
                case XMLEvent.CHARACTERS:
                    System.out.print(xmlStreamReader.getText());
                    break;
                case XMLEvent.END_ELEMENT:
                    System.out.println("</"+xmlStreamReader.getName().toString()+">");
                    break;
                default:
                    //do nothing
                    break;
            }
        } */
    }


    //http://stackoverflow.com/questions/2671496/java-when-to-use-static-methods
    public static void getElements(XMLStreamReader2 xmlStreamReader, Interface startMethod, Interface charMethod, Interface endMethod) {
        try {
           wLoop: while(xmlStreamReader.hasNext()){
                int eventType = xmlStreamReader.next();
                switch (eventType) {
                    case XMLEvent.START_ELEMENT:
                        //System.out.print("<" + xmlStreamReader.getName().toString() + ">");
                        startMethod.interfaceMethod();
                        break;

                    case XMLEvent.CHARACTERS:
                        //System.out.print(xmlStreamReader.getText());
                        charMethod.interfaceMethod();
                        break;

                    case XMLEvent.END_ELEMENT:
                        //System.out.println("</"+xmlStreamReader.getName().toString()+">");
                        if (endMethod.interfaceMethod()) {
                            break wLoop;
                        }
                        break;

                    default:
                        //do nothing
                        break;
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
            System.out.println("ERROR: Streaming file error");
        //} catch (Exception e) {
          //  e.printStackTrace();
        }
    }


    private Boolean scoreStart() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PARTWISE)) {
            // create part-wise score object
            score = new Score(XMLConsts.PARTWISE);
        } else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.TIMEWISE)) {
            // create time-wise score object
            score = new Score(XMLConsts.TIMEWISE);
        } else {
            // not error - just multiple loop throughs
        }

        parseHeader();

        if (score.getScoreType().contentEquals(XMLConsts.PARTWISE)) {
            parsePartwise();
        }
        if (score.getScoreType().contentEquals(XMLConsts.TIMEWISE)) {
            parseTimewise();
        }
        return false;
    }
    private Boolean scoreChar() {
        return false;
    }
    private Boolean scoreEnd() {
        return false;
    }




    /* Parse the header of the score - all elements are common between partwise and timewise
     * 5 top-level elements:
     * work
     * movement-number
     * movement-title
     * identification
     * part-list
     */
    private void parseHeader() {
        ParseXMLHeader parseHeaderObj = new ParseXMLHeader(score);

        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.WORK)) { //work Subtree
            System.out.println("--Work SUBTREE start");
            getElements(xmlStreamReader, () -> parseHeaderObj.workStart(xmlStreamReader),
                    () -> parseHeaderObj.workChar(xmlStreamReader),
                    () -> parseHeaderObj.workEnd(xmlStreamReader));
        }

        /* Could be completed using:
         * xmlStreamReader.next();
         * xmlStreamReader.getText();
         * But done this way for consistency of keeping all header info in the ParseXMLHeader Class
         */
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MOVEMENT_NUM)) {
            System.out.println("--movement-number start");
            getElements(xmlStreamReader, () -> parseHeaderObj.movementNumStart(xmlStreamReader),
                    () -> parseHeaderObj.movementNumChar(xmlStreamReader),
                    () -> parseHeaderObj.movementNumEnd(xmlStreamReader));
        }

        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MOVEMENT_TITLE)) {
            System.out.println("--movement-title start");
            getElements(xmlStreamReader, () -> parseHeaderObj.movementTitleStart(xmlStreamReader),
                    () -> parseHeaderObj.movementTitleChar(xmlStreamReader),
                    () -> parseHeaderObj.movementTitleEnd(xmlStreamReader));
        }

        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.IDENTIFICATION)) { // identification subtree
            System.out.println("--identification SUBTREE start");
            getElements(xmlStreamReader, () -> parseHeaderObj.identificationStart(xmlStreamReader),
                    () -> parseHeaderObj.identificationChar(xmlStreamReader),
                    () -> parseHeaderObj.identificationEnd(xmlStreamReader));
        }

        // if... DEFAULT
        // if... CREDIT

        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART_LIST)) {
            System.out.println("--part-list SUBTREE start");
            getElements(xmlStreamReader, () -> parseHeaderObj.partListStart(xmlStreamReader),
                    () -> parseHeaderObj.partListChar(xmlStreamReader),
                    () -> parseHeaderObj.partListEnd(xmlStreamReader));
        }
    }









    /* parse score-partwise
     * Note: much more common then timewise
     * Part->Measure
     */
    private void parsePartwise() {
        //System.out.println("finally made it");
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PART)) {
            // get part number
            // get measures and sub-tree data from measures
        }
    }



    /* parse score-timewise
     * Measure->Part
     */
    private void parseTimewise() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.MEASURE)) {
            // get measure number
            // get parts and sub-tree data from parts
        }
    }
}