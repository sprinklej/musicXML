/*
 * A MusicXML Complex element (an element that contains other elements)
 * i.e. <'elementName' 'attributes'>'elements'</'elementName'>
 */

package parsed;

import java.util.ArrayList;

public class ComplexElement {
    private String elementName;
    private ArrayList<Attribute> attributes;
    private ArrayList<ElementWrapper> elements;

    // CONSTRUCTOR
    public ComplexElement(String aElementName) {
        elementName = aElementName;
        attributes = new ArrayList<Attribute>();
        elements = new ArrayList<ElementWrapper>();
    }


    // GETTERS
    public String getElementName() {
        return elementName;
    }

    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    public ArrayList<ElementWrapper> getElements() {
        return elements;
    }


    // Setters
    public void setElementName(String elmName) {
        elementName = elmName;
    }


    // ADD TO
    public void addToAttributes(Attribute a) {
        attributes.add(a);
    }

    public void addToElements(ElementWrapper EW) {
        elements.add(EW);
    }
}
