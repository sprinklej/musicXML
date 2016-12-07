/*
 * Exports parsed MusicMXL data back into a file
 */

package musicXML;

import parsed.*;
import parser.XMLConsts;

import java.io.*;
import java.util.ArrayList;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;


import org.codehaus.stax2.XMLStreamWriter2;

public class ExportXML {
    private Score score;
    private String scoreType;
    private XMLStreamWriter2 xmlStreamWriter;
    private File file;

    private String filename = "testFile.xml";

    public ExportXML(File afile, Score aScore) {
        file = afile;
        score = aScore;

        if(score.getScoreType().contentEquals(XMLConsts.PARTWISE)) {
            scoreType = XMLConsts.PARTWISE;
        } else {
            scoreType = XMLConsts.TIMEWISE;
        }
    }


    // Write parsed XML data to a file
    public void writeFile(){
        try {
            // get writer started
            XMLOutputFactory xof = XMLOutputFactory.newInstance();
            xmlStreamWriter = (XMLStreamWriter2) xof.createXMLStreamWriter(new FileWriter(file));

            // start writing the document
            xmlStreamWriter.writeStartDocument();
            xmlStreamWriter.writeDTD("score-" + scoreType, "http://www.musicxml.org/dtds/" + scoreType + ".dtd",
                    "-//Recordare//DTD MusicXML 3.0 " + scoreType.substring(0, 1).toUpperCase() + scoreType.substring(1) + "//EN", "");
            //<!DOCTYPE score-partwise PUBLIC "-//Recordare//DTD MusicXML 3.0 Partwise//EN" "http://www.musicxml.org/dtds/partwise.dtd">
            //<!DOCTYPE score-timewise PUBLIC "-//Recordare//DTD MusicXML 3.0 Timewise//EN" "http://www.musicxml.org/dtds/timewise.dtd">
            xmlStreamWriter.writeStartElement(score.getScoreType());
            xmlStreamWriter.writeAttribute(XMLConsts.VERSION, score.getScoreVersion());

            // write the Header
            writeHeader();

            // write the Body
            if (score.getBody() != null) {
                writeElementWrapperList(score.getBody());
            }

            // finish up writing
            xmlStreamWriter.writeEndDocument();
            xmlStreamWriter.flush();
            xmlStreamWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // ------------------------- MAIN WRITER FOR THE HEADER PART OF THE XML -------------------------
    private void writeHeader() {
        // work
        if (score.getWork() != null) {
            writeComplexElement(score.getWork());
        }
        // movment-number
        if (score.getMovementNumberElement() != null) {
            writeElement(score.getMovementNumberElement());
        }
        // movement-title
        if (score.getMovementTitleElement() != null) {
            writeElement(score.getMovementTitleElement());
        }
        // identification
        if (score.getIdentification() != null) {
            writeComplexElement(score.getIdentification());
        }
        // defaults
        if (score.getDefaults() != null) {
            writeComplexElement(score.getDefaults());
        }

        // credit
        if (score.getCredit() != null) {
            writeElementWrapperList(score.getCredit());
            //writeCredit();
        }
        // part-list - REQUIRED
        if (score.getPartList() != null) {
            writeComplexElement(score.getPartList());
        }
    }



    // HELPER METHODS
    // ------------------------------------------------------------------------------------------------------
    // Writes an arraylist of of ElementWrappers
    private void writeElementWrapperList(ArrayList<ElementWrapper> elementWrappers) {
        for (ElementWrapper ew:elementWrappers) {
            if (ew.getIsComplex() == true) { // write complex element
                writeComplexElement(ew.getComplexElement());
            } else { // write element
                writeElement(ew.getElement());
            }
        }
    }

    // Writes a COMPLEX ELEMENT
    private void writeComplexElement(ComplexElement complexElement) {
        try {
            xmlStreamWriter.writeStartElement(complexElement.getElementName()); // open tag
            // attributes
            if (complexElement.getAttributes() != null) {
                writeAttributesArray(complexElement.getAttributes());
            }
            // elements
            if (complexElement.getElements() != null) {
                writeElementWrapperList(complexElement.getElements());
            }

            xmlStreamWriter.writeEndElement();                          // close tag
        } catch (XMLStreamException e) {
            e.printStackTrace();

        }
    }

    // writes an ELEMENT
    private void writeElement(Element element) {
        try {
            xmlStreamWriter.writeStartElement(element.getElementName()); // open tag
            // attributes
            if (element.getAttributes() != null) {
                writeAttributesArray(element.getAttributes());
            }
            // text data
            if (element.getData() != null) {
                xmlStreamWriter.writeCharacters(element.getData());
            }
            xmlStreamWriter.writeEndElement();                          // close tag
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    // writes an ATTRIBUTES ARRAY
    private void writeAttributesArray(ArrayList<Attribute> attList) {
        for (Attribute att: attList) {
            try {
                xmlStreamWriter.writeAttribute(att.getAttributeName(), att.getAttributeText());
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        }
    }
}
