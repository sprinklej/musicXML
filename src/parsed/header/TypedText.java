package parsed.header;

/**
 * Created by sprinklej on 2016-10-26.
 * FROM XSD: The typed-text type represents a text element with a type attributes.
 * i.e. <creator type="composer">Lee Actor</creator> OR <rights>All Rights reserved</rights>
 * FORMAT: <elementName type="typeText">text</elementName>
 */

/*
  Referenced By
    creator
    rights
    encoder
    relation
 */
public class TypedText {
    private String elementName;
    private String typeText;
    private String text;

    // CONSTRUCTOR
    public TypedText(String aElementName,String aTypeText, String aText) {
        elementName = aElementName;
        typeText = aTypeText;
        text = aText;
    }

    // GETTTERS
    public String getElementName() {
        return elementName;
    }

    public String getTypeText() {
        return typeText;
    }

    public String getText() {
        return text;
    }

    // SETTERS
    public void setElementName(String aElementName) {
        elementName = aElementName;
    }

    public void setTypeText(String aType) {
        typeText = aType;
    }

    public void setText(String aText) {
        text = aText;
    }


    // TOSTRING
    public String toString() {
        String string;
        string = "<" + elementName;
        if (typeText != null) {
            string += " type=\"" + typeText + "\"";
        }
        string += ">" + text + "</" + elementName + ">";

        return string;
    }

}
