package musicXML;

import parsed.*;
import parsed.header.credit.Credit;
import parsed.header.identification.MiscellaneousField;
import parsed.header.TypedText;
import parsed.header.defaults.PageMargins;
import parsed.header.defaults.StaffLayout;
import parsed.header.identification.Supports;
import parser.XMLConsts;

import java.io.*;
import java.util.ArrayList;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.codehaus.stax2.XMLStreamWriter2;


/*
writeStartDocument()
writeStartElement()
writeEndElement()
writeEndDocument()
writeAttribute()
writeCData()
writeCharacters()
writeComment()
writeEmptyElement()
writeDTD()
writeNamespace()
writeEntityRef()
writeProcessingInstruction()
 */


/**
 * Created by sprinklej on 2016-10-25.
 */
public class ExportXML {
    private Score score;
    private String scoreType;
    private XMLStreamWriter2 xmlStreamWriter;


    private String filename = "testFile.xml";

    public ExportXML(Score aScore) {
        score = aScore;

        if(score.getScoreType().contentEquals(XMLConsts.PARTWISE)) {
            scoreType = XMLConsts.PARTWISE;
        } else {
            scoreType = XMLConsts.TIMEWISE;
        }

        writeFile();
    }


    private void writeFile(){
        try {
            // get writer started
            XMLOutputFactory xof = XMLOutputFactory.newInstance();
            xmlStreamWriter = (XMLStreamWriter2) xof.createXMLStreamWriter(new FileWriter(filename));

            // start writing the document
            xmlStreamWriter.writeStartDocument();
            xmlStreamWriter.writeDTD("score-" + scoreType, "http://www.musicxml.org/dtds/" + scoreType + ".dtd",
                    "-//Recordare//DTD MusicXML 3.0 " + scoreType.substring(0, 1).toUpperCase() + scoreType.substring(1) + "//EN", "");
            //<!DOCTYPE score-partwise PUBLIC "-//Recordare//DTD MusicXML 3.0 Partwise//EN" "http://www.musicxml.org/dtds/partwise.dtd">
            //<!DOCTYPE score-timewise PUBLIC "-//Recordare//DTD MusicXML 3.0 Timewise//EN" "http://www.musicxml.org/dtds/timewise.dtd">
            xmlStreamWriter.writeStartElement(score.getScoreType());
            xmlStreamWriter.writeAttribute(XMLConsts.VERSION, score.getScoreVersion());

            // write Header info
            writeHeader();

            // write body info
            writeBody();

            // finish up writing
            xmlStreamWriter.writeEndDocument();
            xmlStreamWriter.flush();
            xmlStreamWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // ------------------------- MAIN EXPORTER FOR THE HEADER PART OF THE XML -------------------------
    private void writeHeader() {
        // work
        if (score.getWork() != null) {
            writeWork();
        }

        try {
            // movement-number
            if (score.getMovementNumber() != null) {
                xmlStreamWriter.writeStartElement(XMLConsts.MOVEMENT_NUM);
                xmlStreamWriter.writeCharacters(score.getMovementNumber());
                xmlStreamWriter.writeEndElement();
            }

            // movement-title
            if (score.getMovementTitle() != null) {
                xmlStreamWriter.writeStartElement(XMLConsts.MOVEMENT_TITLE);
                xmlStreamWriter.writeCharacters(score.getMovementTitle());
                xmlStreamWriter.writeEndElement();
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

        // identification
        if (score.getIdentification() != null) {
            writeIdentification();
        }

        // defaults
        if (score.getDefaults() != null) {
            writeDefaults();
        }

        // credit
        if (score.getCredit() != null) {
            writeCredit();
        }

        // part-list - REQUIRED
        // TODO

    }


    // WORK
    // ------------------------- SCORE-HEADER TOP-LEVEL ITEM -------------------------
    private void writeWork() {
        try {
            xmlStreamWriter.writeStartElement(XMLConsts.WORK);
            // work-number
            if (score.getWork().getWorkNumber() != null) {
                xmlStreamWriter.writeStartElement(XMLConsts.WORK_NUM);
                xmlStreamWriter.writeCharacters(score.getWork().getWorkNumber());
                xmlStreamWriter.writeEndElement();
            }
            // work-title
            if (score.getWork().getWorkTitle() != null) {
                xmlStreamWriter.writeStartElement(XMLConsts.WORK_TITLE);
                xmlStreamWriter.writeCharacters(score.getWork().getWorkTitle());
                xmlStreamWriter.writeEndElement();
            }
            // opus
            if (score.getWork().getOpus() == true) {
                xmlStreamWriter.writeStartElement(XMLConsts.OPUS);
                xmlStreamWriter.writeAttribute(XMLConsts.XMLNS + ":" + XMLConsts.XLINK, score.getWork().getOpusAttributes().getXmlnsLink());
                xmlStreamWriter.writeAttribute(XMLConsts.XLINK + ":" + XMLConsts.XLINK_HREF, score.getWork().getOpusAttributes().getHref());
                xmlStreamWriter.writeAttribute(XMLConsts.XLINK + ":" + XMLConsts.XLINK_TYPE, score.getWork().getOpusAttributes().getType());
                xmlStreamWriter.writeAttribute(XMLConsts.XLINK + ":" + XMLConsts.XLINK_SHOW, score.getWork().getOpusAttributes().getShow());
                xmlStreamWriter.writeAttribute(XMLConsts.XLINK + ":" + XMLConsts.XLINK_ACTUATE, score.getWork().getOpusAttributes().getActuate());
                if (score.getWork().getOpusAttributes().getRole() != null) {
                    xmlStreamWriter.writeAttribute(XMLConsts.XLINK + ":" + XMLConsts.XLINK_ROLE, score.getWork().getOpusAttributes().getRole());
                }
                if (score.getWork().getOpusAttributes().getTitle() != null) {
                    xmlStreamWriter.writeAttribute(XMLConsts.XLINK + ":" + XMLConsts.XLINK_TITLE, score.getWork().getOpusAttributes().getTitle());
                }
                xmlStreamWriter.writeEndElement();
            }
            xmlStreamWriter.writeEndElement();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }


    // IDENTIFICATION
    // ------------------------- SCORE-HEADER TOP-LEVEL ITEM -------------------------
    private void writeIdentification() {
        try {
            xmlStreamWriter.writeStartElement(XMLConsts.IDENTIFICATION);
            // creator
            if (score.getIdentification().getCreator() != null) {
                typedTextLoop(score.getIdentification().getCreator());
            }
            // rights
            if (score.getIdentification().getRights() != null) {
                typedTextLoop(score.getIdentification().getRights());
            }
            // encoding
            if (score.getIdentification().getEncoding() != null) {
                xmlStreamWriter.writeStartElement(XMLConsts.ENCODING);
                writeEncoding();
                xmlStreamWriter.writeEndElement();
            }
            // source
            if (score.getIdentification().getSource() != null) {
                xmlStreamWriter.writeStartElement(XMLConsts.SOURCE);
                xmlStreamWriter.writeCharacters(score.getIdentification().getSource());
                xmlStreamWriter.writeEndElement();
            }
            // relation
            if (score.getIdentification().getRelation() != null) {
                typedTextLoop(score.getIdentification().getRelation());
            }
            // miscellaneous - special case
            if (score.getIdentification().getMiscField() != null) {
                xmlStreamWriter.writeStartElement(XMLConsts.MISCELLANEOUS);
                for (MiscellaneousField mf:score.getIdentification().getMiscField()) {
                    xmlStreamWriter.writeStartElement(XMLConsts.MISCELLANEOUS_FIELD);
                    xmlStreamWriter.writeAttribute(XMLConsts.NAME, mf.getNameAttribue());
                    xmlStreamWriter.writeCharacters(mf.getText());
                    xmlStreamWriter.writeEndElement();
                }
                xmlStreamWriter.writeEndElement();
            }

            xmlStreamWriter.writeEndElement();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }


    // ENCODING - SUBTREE OF IDENTIFICATION
    private void writeEncoding() {
        // encoding-date
        if (score.getIdentification().getEncoding().getEncodingDate() != null) {
            stringLoop(score.getIdentification().getEncoding().getEncodingDate(), XMLConsts.ENCODING_DATE);
        }
        // encoder
        if (score.getIdentification().getEncoding().getEncoder() != null) {
                typedTextLoop(score.getIdentification().getEncoding().getEncoder());
        }
        // software
        if (score.getIdentification().getEncoding().getSoftware() != null) {
            stringLoop(score.getIdentification().getEncoding().getSoftware(), XMLConsts.SOFTWARE);
        }
        // encoding-description
        if (score.getIdentification().getEncoding().getEncodDescription() != null) {
            stringLoop(score.getIdentification().getEncoding().getEncodDescription(), XMLConsts.ENCODING_DESCRIPTION);
        }
        // supports - self closing tag
        if (score.getIdentification().getEncoding().getSupports() != null) {
            try {
                for (Supports s:score.getIdentification().getEncoding().getSupports()) {
                    xmlStreamWriter.writeStartElement(XMLConsts.SUPPORTS);
                    // type - REQUIRED
                    xmlStreamWriter.writeAttribute(XMLConsts.TYPE, s.getTypeAttribute());
                    // element - REQUIRED
                    xmlStreamWriter.writeAttribute(XMLConsts.ELEMENT, s.getElementAttribute());
                    // attribute
                    if (s.getAttributeAttribute() != null) {
                        xmlStreamWriter.writeAttribute(XMLConsts.ATTRIBUTE, s.getAttributeAttribute());
                    }
                    // value
                    if (s.getValueAttribute() != null) {
                        xmlStreamWriter.writeAttribute(XMLConsts.VALUE, s.getValueAttribute());
                    }
                    xmlStreamWriter.writeEndElement();
                }
            } catch (XMLStreamException e) {
                 e.printStackTrace();
            }
        }
    }


    // DEFAULTS
    // ------------------------- SCORE-HEADER TOP-LEVEL ITEM -------------------------
    private void writeDefaults() {
        try {
            xmlStreamWriter.writeStartElement(XMLConsts.DEFAULTS);
            // scaling
            if (score.getDefaults().getScaling() == true) {
                xmlStreamWriter.writeStartElement(XMLConsts.SCALING);

                // millimeters - must occur
                xmlStreamWriter.writeStartElement(XMLConsts.MILLIMETERS);
                xmlStreamWriter.writeCharacters(score.getDefaults().getScalingMillimeters());
                xmlStreamWriter.writeEndElement();
                // tenths - must occur
                xmlStreamWriter.writeStartElement(XMLConsts.TENTHS);
                xmlStreamWriter.writeCharacters(score.getDefaults().getScalingTenths());
                xmlStreamWriter.writeEndElement();

                xmlStreamWriter.writeEndElement();
            }
            // page-layout - minOccurs=0
            if (score.getDefaults().getPageLayout() == true) {
                xmlStreamWriter.writeStartElement(XMLConsts.PAGE_LAYOUT);
                // page-height
                if (score.getDefaults().getPageHeight() != null) {
                    xmlStreamWriter.writeStartElement(XMLConsts.PAGE_HEIGHT);
                    xmlStreamWriter.writeCharacters(score.getDefaults().getPageHeight());
                    xmlStreamWriter.writeEndElement();
                }
                // page-width
                if (score.getDefaults().getPageWidth() != null) {
                    xmlStreamWriter.writeStartElement(XMLConsts.PAGE_WIDTH);
                    xmlStreamWriter.writeCharacters(score.getDefaults().getPageWidth());
                    xmlStreamWriter.writeEndElement();
                }
                // page-margins
                if (score.getDefaults().getPageMargins() != null) {
                    for (PageMargins pm:score.getDefaults().getPageMargins()) {
                        xmlStreamWriter.writeStartElement(XMLConsts.PAGE_MARGINS);
                        if(pm.getTypeAttribute() != null) {
                            xmlStreamWriter.writeAttribute(pm.getTypeAttribute().getAttributeName(),
                                    pm.getTypeAttribute().getAttributeText());
                        }
                        // left
                        if (pm.getLeft() != null) {
                            xmlStreamWriter.writeStartElement(XMLConsts.LEFT_MARGIN);
                            xmlStreamWriter.writeCharacters(pm.getLeft());
                            xmlStreamWriter.writeEndElement();
                        }
                        // right
                        if (pm.getRight() != null) {
                            xmlStreamWriter.writeStartElement(XMLConsts.RIGHT_MARGIN);
                            xmlStreamWriter.writeCharacters(pm.getRight());
                            xmlStreamWriter.writeEndElement();
                        }
                        // top
                        if (pm.getTop() != null) {
                            xmlStreamWriter.writeStartElement(XMLConsts.TOP_MARGIN);
                            xmlStreamWriter.writeCharacters(pm.getTop());
                            xmlStreamWriter.writeEndElement();
                        }
                        // bottom
                        if (pm.getBottom() != null) {
                            xmlStreamWriter.writeStartElement(XMLConsts.BOTTOM_MARGIN);
                            xmlStreamWriter.writeCharacters(pm.getBottom());
                            xmlStreamWriter.writeEndElement();
                        }

                        xmlStreamWriter.writeEndElement();
                    }
                }

                xmlStreamWriter.writeEndElement();
            }
            // system-layout
            if (score.getDefaults().getSystemLayout() == true) {
                xmlStreamWriter.writeStartElement(XMLConsts.SYSTEM_LAYOUT);
                // system-margins
                if (score.getDefaults().getSystemMargins() == true) {
                    xmlStreamWriter.writeStartElement(XMLConsts.SYSTEM_MARGINS);
                    // left-margin - required
                    xmlStreamWriter.writeStartElement(XMLConsts.LEFT_MARGIN);
                    xmlStreamWriter.writeCharacters(score.getDefaults().getLeftSysMargin());
                    xmlStreamWriter.writeEndElement();
                    // right-margin - required
                    xmlStreamWriter.writeStartElement(XMLConsts.RIGHT_MARGIN);
                    xmlStreamWriter.writeCharacters(score.getDefaults().getRightSysMargin());
                    xmlStreamWriter.writeEndElement();

                    xmlStreamWriter.writeEndElement();
                }
                // system-distance
                if (score.getDefaults().getSystemDistance() != null) {
                    xmlStreamWriter.writeStartElement(XMLConsts.SYSTEM_DISTANCE);
                    xmlStreamWriter.writeCharacters(score.getDefaults().getSystemDistance());
                    xmlStreamWriter.writeEndElement();
                }
                // top-system-distance
                if (score.getDefaults().getTopSysDistance() != null) {
                    xmlStreamWriter.writeStartElement(XMLConsts.TOP_SYSTEM_DISTANCE);
                    xmlStreamWriter.writeCharacters(score.getDefaults().getTopSysDistance());
                    xmlStreamWriter.writeEndElement();
                }
                // system-dividers
                if (score.getDefaults().getSystemDividers() == true) {
                    xmlStreamWriter.writeStartElement(XMLConsts.SYSTEM_DIVIDERS);
                    // left-divider - required
                    xmlStreamWriter.writeStartElement(XMLConsts.LEFT_DIVIDER);
                    for (Attribute a:score.getDefaults().getLeftDivider()) {
                        xmlStreamWriter.writeAttribute(a.getAttributeName(), a.getAttributeText());
                    }
                    xmlStreamWriter.writeEndElement();
                    // right-divider -required
                    xmlStreamWriter.writeStartElement(XMLConsts.RIGHT_DIVIDER);
                    for (Attribute a:score.getDefaults().getRightDivider()) {
                        xmlStreamWriter.writeAttribute(a.getAttributeName(), a.getAttributeText());
                    }
                    xmlStreamWriter.writeEndElement();

                    xmlStreamWriter.writeEndElement();
                }


                xmlStreamWriter.writeEndElement();
            }
            // staff-layout
            if (score.getDefaults().getStaffLayout() != null) {
                for(StaffLayout sl:score.getDefaults().getStaffLayout()) {
                    xmlStreamWriter.writeStartElement(XMLConsts.STAFF_LAYOUT);
                    if(sl.getAttribute() != null) {
                        xmlStreamWriter.writeAttribute(sl.getAttribute().getAttributeName(),
                                sl.getAttribute().getAttributeText());
                    }
                    xmlStreamWriter.writeStartElement(XMLConsts.STAFF_DISTANCE);
                    xmlStreamWriter.writeCharacters(sl.getStaffDistance());
                    xmlStreamWriter.writeEndElement();

                    xmlStreamWriter.writeEndElement();
                }

            }
            // appearance
            if (score.getDefaults().getAppearance() == true) {
                xmlStreamWriter.writeStartElement(XMLConsts.APPEARANCE);
                // line-width
                if (score.getDefaults().getLineWidth() != null) {
                    writeElementsWithAttributes(score.getDefaults().getLineWidth());
                }
                // note-size
                if (score.getDefaults().getNoteSize() != null) {
                    writeElementsWithAttributes(score.getDefaults().getNoteSize());
                }
                // distance
                if (score.getDefaults().getDistance() != null) {
                    writeElementsWithAttributes(score.getDefaults().getDistance());
                }
                // other-appearance
                if (score.getDefaults().getOtherAppearance() != null) {
                    writeElementsWithAttributes(score.getDefaults().getOtherAppearance());
                }

                xmlStreamWriter.writeEndElement();
            }
            // music-font
            if (score.getDefaults().getMusicFont() != null) {
                writeSingleElementwithAtts(score.getDefaults().getMusicFont());
            }
            // word-font
            if (score.getDefaults().getWordFont() != null) {
                writeSingleElementwithAtts(score.getDefaults().getWordFont());
            }
            // lyric-font
            if (score.getDefaults().getLyricFont() != null) {
                writeElementsWithAttributes(score.getDefaults().getLyricFont());
            }
            // lyric-language
            if (score.getDefaults().getLyricLanguage() != null) {
                writeElementsWithAttributes(score.getDefaults().getLyricLanguage());
            }

            xmlStreamWriter.writeEndElement();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

    }


    // write a single element with attributes
    private void writeSingleElementwithAtts(Element element) {
        try {
            xmlStreamWriter.writeStartElement(element.getElementName());
            if (element.getAttribute() != null) {
                ArrayList<Attribute> attributes = element.getAttribute();
                for (Attribute a : attributes) {
                    xmlStreamWriter.writeAttribute(a.getAttributeName(), a.getAttributeText());
                }
            }
            if (element.getData() != null) {
                xmlStreamWriter.writeCharacters(element.getData());
            }
            xmlStreamWriter.writeEndElement();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    // write and Array of element with attributes
    private void writeElementsWithAttributes(ArrayList<Element> elements) {
        for (Element el:elements) {
            writeSingleElementwithAtts(el);
        }
    }




    // CREDIT
    // ------------------------- SCORE-HEADER TOP-LEVEL ITEM -------------------------
    private void writeCredit() {
        try {
            for (Credit credit:score.getCredit()) {
                xmlStreamWriter.writeStartElement(XMLConsts.CREDIT);
                // page attribute
                if (credit.getPageAttribute() != null) {
                    xmlStreamWriter.writeAttribute(credit.getPageAttribute().getAttributeName(),
                            credit.getPageAttribute().getAttributeText());
                }

                // credit-type
                if (credit.getCreditType() != null) {
                    for (String s:credit.getCreditType()) {
                        xmlStreamWriter.writeStartElement(XMLConsts.CREDIT_TYPE);
                        xmlStreamWriter.writeCharacters(s);
                        xmlStreamWriter.writeEndElement();
                    }
                }
                // link
                if (credit.getLink() != null) {
                    writeElementsWithAttributes(credit.getLink());
                }
                // bookmark
                if (credit.getLink() != null) {
                    writeElementsWithAttributes(credit.getBookmark());
                }
                // credit-image
                if (credit.getCreditImage() != null) {
                    writeSingleElementwithAtts(credit.getCreditImage());
                }
                // credit-words
                if (credit.getCreditWords() != null) {
                    writeSingleElementwithAtts(credit.getCreditWords());
                }


                // link
                // TODO
                // bookmark
                // TODO
                // credit-words





                xmlStreamWriter.writeEndElement();
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    // PART-LIST
    // ------------------------- SCORE-HEADER TOP-LEVEL ITEM -------------------------




    // BODY
    // ------------------------- MAIN EXPORTER FOR THE BODY PART OF THE XML -------------------------
    private void writeBody() {
        // TODO
    }


    // Takes in an ArrayList of String
    // writes out the elements to xml
    private void stringLoop(ArrayList<String> sArray, String elementType) {
        try {
            for (String s:sArray) {
                xmlStreamWriter.writeStartElement(elementType);
                xmlStreamWriter.writeCharacters(s);
                xmlStreamWriter.writeEndElement();
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }


    // Takes in an ArrayList of TypedText
    // writes out the elements to xml
    private void typedTextLoop(ArrayList<TypedText> ttArray) {
        try {
            for (TypedText tt:ttArray) {
                xmlStreamWriter.writeStartElement(tt.getElementName());
                if (tt.getTypeText() != null) {
                    xmlStreamWriter.writeAttribute(XMLConsts.TYPE, tt.getTypeText());
                }
                xmlStreamWriter.writeCharacters(tt.getText());
                xmlStreamWriter.writeEndElement();
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    // ??
    public String transform(String xml) throws XMLStreamException, TransformerException
    {
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        Writer out = new StringWriter();
        t.transform(new StreamSource(new StringReader(xml)), new StreamResult(out));
        return out.toString();
    }

    /*
    private Score score;
    private File file;
    private PrintWriter writer;

    public ExportXML(Score aScore) {
        score = aScore;

        // create the new file
        createFile();
        // fill the header part of the file
        printHeader();
        // fill the body of the file
        printBody();
        // close the file
        writer.println("</" + score.getScoreType() + ">");
        writer.close();
    }

    //http://stackoverflow.com/questions/6142901/how-to-create-a-file-in-a-directory-in-java
    private void createFile() {
        file = new File("testFile.xml");

        try {
            //f.getParentFile().mkdirs();
            file.createNewFile();
            writer = new PrintWriter(file, "UTF-8");
        } catch (IOException e) {
            System.out.println("ERROR: Could Not create new XML file");
            e.printStackTrace();
        }
    }


    // ------------------------- MAIN EXPORTER FOR THE HEADER PART OF THE XML -------------------------
    private void printHeader() {
        // XML VERSION and DOCTYPE
        writer.println(score.getxmlVersDocType());

        // SCORE-TYPE
        writer.print("<" + score.getScoreType());
        if (score.getScoreVersion() != null) {
            writer.println(" " + XMLConsts.VERSION + "=\"" + score.getScoreVersion() + "\">");
        } else {
            writer.println(">");
        }

        // WORK - minOccurs=0
        if (score.getWork() != null) {
            printWork();
        }

        // MOVEMENT-NUMBER - minOccurs=0
        if (score.getMovementNumber() != null) {
            writer.println("  <movement-number>" + score.getMovementNumber() + "</movement-number>");
        }

        // MOVEMENT-TITLE - minOccurs=0
        if (score.getMovementTitle() != null) {
            writer.println("  <movement-title>" + score.getMovementTitle() + "</movement-title>");
        }

        // IDENTIFICATION - minOccurs=0
        if (score.getIdentification() != null) {
            printIdentification();
        }

        // DEFAULTS - minOccurs=0
        if (score.getDefaults() != null) {
            // TODO
        }

        // CREDIT - minOccurs=0 maxOccurs="unbounded"
        if (score.getCredit() != null) {
            // TODO
        }

        // PART-LIST - MUST OCCUR
        // TODO
    }

    // WORK
    // ------------------------- SCORE-HEADER TOP-LEVEL ITEM -------------------------
    private void printWork() {
        writer.println("  <work>");
        // work-number - minOccurs=0
        if (score.getWork().getWorkNumber() != null) {
            writer.println("    <work-number>"+ score.getWork().getWorkNumber() + "</work-number>");
        }
        // work-title - minOccurs=0
        if (score.getWork().getWorkTitle() != null) {
            writer.println("    <work-title>" + score.getWork().getWorkTitle() + "</work-title>");
        }
        // opus - minOccurs=0
        if (score.getWork().getOpus() == true) {
            writer.print("    <opus " + score.getWork().getOpusAttributes().getXmlns() + " "
                    + XMLConsts.XLINK + ":" + XMLConsts.XLINK_HREF + "=\"" + score.getWork().getOpusAttributes().getHref() + "\" "
                    + XMLConsts.XLINK + ":" + XMLConsts.XLINK_TYPE + "=\"" + score.getWork().getOpusAttributes().getType() + "\" "
                    + XMLConsts.XLINK + ":" + XMLConsts.XLINK_SHOW + "=\"" + score.getWork().getOpusAttributes().getShow() + "\" "
                    + XMLConsts.XLINK + ":" + XMLConsts.XLINK_ACTUATE + "=\"" + score.getWork().getOpusAttributes().getActuate() + "\"");

            if (score.getWork().getOpusAttributes().getRole() != null) {
                writer.print(" " + XMLConsts.XLINK + ":" + XMLConsts.XLINK_ROLE + "=\"" + score.getWork().getOpusAttributes().getRole() + "\"");
            }

            if (score.getWork().getOpusAttributes().getTitle() != null) {
                writer.print(" " + XMLConsts.XLINK + ":" + XMLConsts.XLINK_TITLE + "=\"" + score.getWork().getOpusAttributes().getTitle() + "\"");
            }
            writer.println("/>");
        }
        writer.println("  </work>");
    }


    // IDENTIFICATION
    // ------------------------- SCORE-HEADER TOP-LEVEL ITEM -------------------------
    private void printIdentification() {
        writer.println("    <identification>");
        // creator
        if (score.getIdentification().getCreator() != null) {
            for (TypedText tt:score.getIdentification().getCreator()) {
                writer.println("      " + tt.toString());
            }
        }
        // rights
        if (score.getIdentification().getRights() != null) {
            for (TypedText tt:score.getIdentification().getRights()) {
                writer.println("      " + tt.toString());
            }
        }
        // encoding
        if (score.getIdentification().getEncoding() != null) {
            //TODO
        }
        // source
        if (score.getIdentification().getSource() != null) {
            writer.println("      <source>" + score.getIdentification().getSource() + "</source>");
        }
        // relation
        if (score.getIdentification().getRelation() != null) {
            for (TypedText tt:score.getIdentification().getRelation()) {
                writer.println("      " + tt.toString());
            }
        }
        // miscellaneous
        if (score.getIdentification().getMiscField() != null) {
            //TODO
        }



        // encoding-date
        //if (score.getIdentification().geteDate() != null) {
        //    writer.println("      <encoding-date>" + score.getIdentification().geteDate() + "</encoding-date>");
        //}
        // encoding-

        writer.println("    </identification>");
    }


    // DEFAULTS
    // ------------------------- SCORE-HEADER TOP-LEVEL ITEM -------------------------

    // CREDIT
    // ------------------------- SCORE-HEADER TOP-LEVEL ITEM -------------------------

    // PART-LIST
    // ------------------------- SCORE-HEADER TOP-LEVEL ITEM -------------------------




    // BODY
    // ------------------------- MAIN EXPORTER FOR THE BODY PART OF THE XML -------------------------
    private void printBody() {
        // TODO
    }
    */
}
