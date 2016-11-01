package parsed;

import parsed.header.defaults.PageMargins;

import java.util.Objects;

/**
 * Created by sprinklej on 2016-11-01.
 */
public class ElementWrapper {
    private boolean isComplex;
    private ComplexElement complexElement = null;
    private Element element = null;


    // CONSTRUCTOR
    public ElementWrapper(boolean aIsComplex, Object aObject) {
        isComplex = aIsComplex;

        if (isComplex == true) {
            complexElement = (ComplexElement) aObject;
        } else {
            element = (Element) aObject;
        }
    }

    // GETTERS
    public boolean getIsComplex() {
        return isComplex;
    }

    public ComplexElement getComplexElement() {
        return complexElement;
    }

    public Element getElement() {
        return element;
    }
}
