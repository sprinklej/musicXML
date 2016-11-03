package parser;

import org.codehaus.stax2.XMLStreamReader2;
import parsed.Attribute;
import parsed.ComplexElement;
import parsed.Element;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

/**
 * Created by sprinklej on 2016-11-01.
 *
 * A helper class for the ParseXMLBody and ParseXMLHeader classes
 *
 */
public class ParseHelper {
    // CONSTRUCTOR
    public ParseHelper() {}

    // ------------------------- Attribute Helpers -------------------------
    // sets the attributes for a complex element
    public void setComplexEAttributes(XMLStreamReader2 xmlStreamReader, ComplexElement ce) {
        for (int i = 0; i < xmlStreamReader.getAttributeCount(); i++) {
            ce.addToAttributes(getAttribute(xmlStreamReader, i));
        }
    }

    // sets the attributes for an element
    public void setElementAttributes(XMLStreamReader2 xmlStreamReader, Element e) {
        for (int i = 0; i < xmlStreamReader.getAttributeCount(); i++) {
            e.addToAttributes(getAttribute(xmlStreamReader, i));
        }
    }

    // creates and attribute object and returns it
    private Attribute getAttribute(XMLStreamReader2 xmlStreamReader, int i) {
        Attribute a = new Attribute(xmlStreamReader.getAttributeName(i).toString(),
                xmlStreamReader.getAttributeValue(i).toString());
        a = checkAttribute(a);
        return a;
    }


    // checks an attribute to see if "xml:" or "xmlns:"
    // has been replaced by a url by the parser and set it back.
    private Attribute checkAttribute(Attribute a) {
        a.setAttributeName(a.getAttributeName().replace("{" + XMLConsts.NAMESPACEURL + "}", XMLConsts.XML + ":"));
        a.setAttributeName(a.getAttributeName().replace("{" + XMLConsts.XLINKURL + "}", XMLConsts.XLINK + ":"));
        return a;
    }



    // ------------------------- Element Helper -------------------------
    // gets the attributes and the text-data for an element
    // then returns the element
    public Element getElement(XMLStreamReader2 xmlStreamReader){
        Element element = new Element();
        try {
            element.setElementName(xmlStreamReader.getName().toString());
            // attributes
            setElementAttributes(xmlStreamReader, element);
            // data
            int eventType = xmlStreamReader.next();
            if (eventType != XMLEvent.END_ELEMENT) {
                element.setData(xmlStreamReader.getText());
                xmlStreamReader.next();
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return element;
    }
}
