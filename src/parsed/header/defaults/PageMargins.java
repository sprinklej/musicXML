package parsed.header.defaults;

import com.sun.tools.doclint.HtmlTag;
import parsed.Attribute;
import parsed.Margins;

/**
 * Created by sprinklej on 2016-10-27.
 */
public class PageMargins {
    //private String typeAttribute;
    private Attribute attribute;
    private String left;
    private String right;
    private String top;
    private String bottom;


    // CONSTRUCTOR
    public PageMargins() {}


    // GETTERS
    public Attribute getTypeAttribute() {
        return attribute;
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }

    public String getTop() {
        return top;
    }

    public String getBottom() {
        return bottom;
    }


    // SETTERS
    public void setAttribute(Attribute aAttribute) {
        attribute = aAttribute;
    }

    public void setLeft(String aLeft) {
        left = aLeft;
    }

    public void setRight(String aRight) {
        right = aRight;
    }

    public void setTop(String aTop) {
        top = aTop;
    }

    public void setBottom(String aBottom) {
        bottom = aBottom;
    }
}
