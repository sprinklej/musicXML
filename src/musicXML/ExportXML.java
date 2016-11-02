package musicXML;

import parsed.*;
import parsed.header.credit.Credit;
import parsed.header.identification.TypedText;
import parsed.header.partlist.Group;
import parsed.header.partlist.MidiInstrument;
import parser.PartListWrapper;
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

            // write BODY
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


    // ------------------------- MAIN EXPORTER FOR THE HEADER PART OF THE XML -------------------------
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
/*
        // credit
        if (score.getCredit() != null) {
            writeCredit();
        }

        // part-list - REQUIRED
        writePartList();
        */
    }



    // GOOD HELPER METHODS
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
    // ------------------------------------------------------------------------------------------------------







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

                // TODO link,bookmark,credit-words???

                xmlStreamWriter.writeEndElement();
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }



    // PART-LIST
    // ------------------------- SCORE-HEADER TOP-LEVEL ITEM -------------------------
    private void writePartList() {
        try {
            xmlStreamWriter.writeStartElement(XMLConsts.PART_LIST);
            for (PartListWrapper plw: score.getPartList()) {
                if (plw.getIsPart() == true) {  // is a part
                    writePart(plw.getPart());
                } else {                        // is a group
                    writeGroup(plw.getGroup());
                }
            }
            xmlStreamWriter.writeEndElement();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }


    // PART-GROUP IN PART-LIST
    private void writeGroup(Group group) {
        try {
            xmlStreamWriter.writeStartElement(XMLConsts.PART_GROUP);
            // write attributes
            if (group.getGroupAttributes() != null) {
                writeAttributesArray(group.getGroupAttributes());
            }
            // group-name
            if (group.getGroupName() != null) {
                writeElement(group.getGroupName());
            }
            // group-name-display
            if (group.getGroupNameDisplay() == true) {
                xmlStreamWriter.writeStartElement(XMLConsts.GROUP_NAME_DISPLAY);
                // write attribute
                if (group.getGroupNameDispAttribute() != null) {
                    xmlStreamWriter.writeAttribute(group.getGroupNameDispAttribute().getAttributeName(),
                            group.getGroupNameDispAttribute().getAttributeText());
                }
                // write accidental-text OR display-text elements
                if (group.getAccidentalORDisplayText() != null) {
                    writeElementArray(group.getAccidentalORDisplayText());
                }

                xmlStreamWriter.writeEndElement();
            }
            // group-abbreviation
            if (group.getGroupAbbreviation() != null) {
                writeElement(group.getGroupAbbreviation());
            }
            // group-abbreviation-display
            if (group.getGroupAbbreviationDisplay() == true) {
                xmlStreamWriter.writeStartElement(XMLConsts.GROUP_ABBREVIATION_DISPLAY);
                // write attribute
                if (group.getGroupAbbrevDispAttribute() != null) {
                    xmlStreamWriter.writeAttribute(group.getGroupAbbrevDispAttribute().getAttributeName(),
                            group.getGroupAbbrevDispAttribute().getAttributeText());
                }
                // write elements of group-abbreviation-display
                if (group.getGroupAbbrevDispElements() != null) {
                    writeElementArray(group.getGroupAbbrevDispElements());
                }

                xmlStreamWriter.writeEndElement();
            }
            // group-symbol
            if (group.getGroupSymbol() != null) {
                writeElement(group.getGroupSymbol());
            }
            // group-barline
            if (group.getGroupBarline() != null) {
                writeElement(group.getGroupBarline());
            }
            // group-time
            if (group.getGroupTime() != null) {
                writeElement(group.getGroupTime());
            }
            // footnote
            if (group.getFootnote() != null) {
                writeElement(group.getFootnote());
            }
            // level
            if (group.getLevel() != null) {
                writeElement(group.getLevel());
            }

            xmlStreamWriter.writeEndElement();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    // SCORE-PART IN PART-LIST
    private void writePart(Part part) {
        try {
            xmlStreamWriter.writeStartElement(XMLConsts.SCORE_PART);
            // write attributes
            if (part.getPartIDAttribute() != null) {
                xmlStreamWriter.writeAttribute(part.getPartIDAttribute().getAttributeName(),
                        part.getPartIDAttribute().getAttributeText());
            }
            // identification
            if (part.getIdentification() != null) {
                //writeIdentification(part.getIdentification());
            }
            // part-name
            if (part.getPartName() != null) {
                writeElement(part.getPartName());
            }
            // part-name-display
            if (part.getPartNameDisplay() == true) {
                xmlStreamWriter.writeStartElement(XMLConsts.PART_NAME_DISPLAY);
                // write attribute
                if (part.getPartNameDispAttribute() != null) {
                    xmlStreamWriter.writeAttribute(part.getPartNameDispAttribute().getAttributeName(),
                            part.getPartNameDispAttribute().getAttributeText());
                }
                // write elements
                if (part.getPartNameDispElements() != null) {
                    writeElementsWithAttributes(part.getPartNameDispElements());
                }
                xmlStreamWriter.writeEndElement();
            }
            // part-abbreviation
            if (part.getPartAbbreviation() != null) {
                writeElement(part.getPartAbbreviation());
            }
            // part-abbreviation-display
            if (part.getPartAbbreviationDisplay() == true) {
                xmlStreamWriter.writeStartElement(XMLConsts.PART_ABBREVIATION_DISPLAY);
                // write attribute
                if (part.getPartNameDispAttribute() != null) {
                    xmlStreamWriter.writeAttribute(part.getPartAbbrevDispAttribute().getAttributeName(),
                            part.getPartAbbrevDispAttribute().getAttributeText());
                }
                // write elements
                if (part.getPartAbbrevDispElements() != null) {
                    writeElementsWithAttributes(part.getPartAbbrevDispElements());
                }
                xmlStreamWriter.writeEndElement();
            }
            // group
            if (part.getGroup() != null) {
                writeElementsWithAttributes(part.getGroup());
            }
            // score-instrument
            if (part.getScoreInstrument() == true) {
                xmlStreamWriter.writeStartElement(XMLConsts.SCORE_INSTRUMENT);
                if (part.getScoreInstAttribute() != null) { // write attribute
                    xmlStreamWriter.writeAttribute(part.getScoreInstAttribute().getAttributeName(),
                            part.getScoreInstAttribute().getAttributeText());
                }
                if (part.getScoreInstElements() != null) { // write elements
                    writeElementsWithAttributes(part.getScoreInstElements());
                }
                // virtual-instrument
                if (part.getVirtualInstrument() == true) {
                    xmlStreamWriter.writeStartElement(XMLConsts.VIRTUAL_INSTRUMENT);
                    if (part.getVirtualInstElements() != null) { // write elements
                        writeElementsWithAttributes(part.getVirtualInstElements());
                    }
                    xmlStreamWriter.writeEndElement();
                }

                xmlStreamWriter.writeEndElement();
            }
            // midi-device
            if (part.getMidiDevice() != null) {
                writeElementsWithAttributes(part.getMidiDevice());
            }
            // midi-instrument
            if (part.getMidiInstruments() != null) {

                for(MidiInstrument MI:part.getMidiInstruments()) {
                    xmlStreamWriter.writeStartElement(XMLConsts.MIDI_INSTRUMENT);
                    writeElementsWithAttributes(MI.getMidiIntrumentElements());
                    xmlStreamWriter.writeEndElement();
                }
            }

            xmlStreamWriter.writeEndElement();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    // Takes in an ArrayList of STRING
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


    // write a single element with attributes
    private void writeSingleElementwithAtts(Element element) {
        try {
            xmlStreamWriter.writeStartElement(element.getElementName());
            if (element.getAttributes() != null) {
                ArrayList<Attribute> attributes = element.getAttributes();
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


    // writes and ARRAY of ELEMENTS
    private void writeElementArray(ArrayList<Element> elements) {
        for (Element e:elements) {
            writeElement(e);
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








    // ------------------------------------------------------------------------------------------------------
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
    // write ATTRIBUTES for a complex elements
    private void writeAttributesArray(ArrayList<Attribute> attList) {
        for (Attribute att: attList) {
            try {
                xmlStreamWriter.writeAttribute(att.getAttributeName(), att.getAttributeText());
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        }
    }
*/
/*
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
*/
    //DEAD!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // WORK
    // ------------------------- SCORE-HEADER TOP-LEVEL ITEM -------------------------
   /* private void writeWork() {

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

        // IDENTIFICATION - pass Identification object because its used multiple times
    // ------------------------- SCORE-HEADER TOP-LEVEL ITEM -------------------------
    private void writeIdentification(Identification id) {
        try {
            xmlStreamWriter.writeStartElement(XMLConsts.IDENTIFICATION);
            // creator
            if (id.getCreator() != null) {
                typedTextLoop(id.getCreator());
            }
            // rights
            if (id.getRights() != null) {
                typedTextLoop(id.getRights());
            }
            // encoding
            if (id.getEncoding() != null) {
                xmlStreamWriter.writeStartElement(XMLConsts.ENCODING);
                writeEncoding(id.getEncoding());
                xmlStreamWriter.writeEndElement();
            }
            // source
            if (id.getSource() != null) {
                xmlStreamWriter.writeStartElement(XMLConsts.SOURCE);
                xmlStreamWriter.writeCharacters(id.getSource());
                xmlStreamWriter.writeEndElement();
            }
            // relation
            if (id.getRelation() != null) {
                typedTextLoop(id.getRelation());
            }
            // miscellaneous - special case
            if (id.getMiscField() != null) {
                xmlStreamWriter.writeStartElement(XMLConsts.MISCELLANEOUS);
                for (MiscellaneousField mf:id.getMiscField()) {
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
    private void writeEncoding(Encoding encoding) {
        // encoding-date
        if (encoding.getEncodingDate() != null) {
            stringLoop(encoding.getEncodingDate(), XMLConsts.ENCODING_DATE);
        }
        // encoder
        if (encoding.getEncoder() != null) {
                typedTextLoop(encoding.getEncoder());
        }
        // software
        if (encoding.getSoftware() != null) {
            stringLoop(encoding.getSoftware(), XMLConsts.SOFTWARE);
        }
        // encoding-description
        if (encoding.getEncodDescription() != null) {
            stringLoop(encoding.getEncodDescription(), XMLConsts.ENCODING_DESCRIPTION);
        }
        // supports - self closing tag
        if (encoding.getSupports() != null) {
            try {
                for (Supports s:encoding.getSupports()) {
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

    */

}
