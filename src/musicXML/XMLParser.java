package musicXML;

import java.io.*;

import javax.swing.*;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import javax.xml.validation.SchemaFactory;

import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.XMLStreamReader2;


//http://stackoverflow.com/questions/2186931/java-pass-method-as-parameter
//http://stackoverflow.com/questions/4685563/how-to-pass-a-function-as-a-parameter-in-java
//http://stackoverflow.com/questions/2186931/java-pass-method-as-parameter
//http://stackoverflow.com/questions/4685435/what-is-the-cloest-thing-to-a-function-pointer-in-java
interface I {
    //Can only have 1 abstract method - therefore they must all return true/false
    public boolean interfaceMethod();
}


/**
 * Created by sprinklej on 2016-09-23.
 */
public class XMLParser {
    private MusicXMLFile currentSong = null;
    private Score currentScore = null;
    private XMLStreamReader2 xmlStreamReader = null;

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
    public static void getElements(XMLStreamReader2 xmlStreamReader, I startMethod, I charMethod,I endMethod) {
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
        } else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.TIMEWISE)) {
            // create time-wise score object
        } else {
            // not error - just multiple loop throughs
        }

        parseHeader();

        /*
        if () {
            parsePartwise();
        }
        if () {
            parseTimewise();
        }
        */

        return false;
    }
    private Boolean scoreChar() {
        return false;
    }
    private Boolean scoreEnd() {
        return false;
    }



    private void parseHeader() {
        ParseXMLHeader parseHeaderObj = new ParseXMLHeader();

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
    Note: much more commonly used then timewise
    Structure:
    <score-partwise>
        <work>{0,1}</work>
        <movement-number>{0,1}</movement-number>
        <movement-title>{0,1}</movement-title>
        <identification>{0,1}</identification>
        <defaults>{0,1}</defaults>
        <credit page="">{0,unbounded}</credit>
        <part-list>{1,1}</part-list>
        <part id="">{1,unbounded}</part>
    </score-partwise>
    */
    private void parsePartwise() {

    }



    /* parse score-timewise
    Structure:
    <score-timewise>
        <work>{0,1}</work>
        <movement-number>{0,1}</movement-number>
        <movement-title>{0,1}</movement-title>
        <identification>{0,1}</identification>
        <defaults>{0,1}</defaults>
        <credit page="">{0,unbounded}</credit>
        <part-list>{1,1}</part-list>
        <measure implicit="" non-controlling="" number="" width="">{1,unbounded}</measure>
    </score-timewise>
    */
    private void parseTimewise() {

    }
}