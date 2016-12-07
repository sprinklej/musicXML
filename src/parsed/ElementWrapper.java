/*
 * A wrapper so that a list of elements and/or complex elements can be built
 * Can contain either an 'element' or a 'complex element'
 */

package parsed;

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
