package parsed;

import java.util.ArrayList;

/**
 * Created by sprinklej on 2016-10-28.
 */
public class Element {
    private String elementName;
    private ArrayList<Attribute> attribute;
    private String data;

    // CONSTRUCTOR
    public Element() {
        attribute = new ArrayList<Attribute>();
    }

    // GETTERS
    public String getElementName() {
        return elementName;
    }

    public ArrayList<Attribute> getAttribute() {
        return attribute;
    }

    public String getData() {
        return data;
    }


    // SETTERS
    public void setElementName(String elName) {
        elementName = elName;
    }

    public void setData(String aData) {
        data = aData;
    }


    // ADD TO
    public void addToAttribute(Attribute atb) {
        attribute.add(atb);
    }
}
