package parsed;

/**
 * Created by sprinklej on 2016-10-27.
 */
public class Attribute {
    private String attributeName;
    private String attributeText;

    // CONSTRUCTOR
    public Attribute(String aName, String aText) {
        attributeName = aName;
        attributeText = aText;
    }


    // GETTERS
    public String getAttributeName() {
        return attributeName;
    }

    public String getAttributeText() {
        return attributeText;
    }


    // SETTERS
    public void setAttributeName(String aName) {
        attributeName = aName;
    }

    public void setAttributeText(String aText) {
        attributeText = aText;
    }
}
