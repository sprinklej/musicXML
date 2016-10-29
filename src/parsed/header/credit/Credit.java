package parsed.header.credit;

import parsed.Attribute;
import parsed.Element;

import java.util.ArrayList;

/**
 * Created by sprinklej on 2016-10-19.
 */
public class Credit {
    //private String pageAttribute;
    private Attribute pageAttribute;
    private ArrayList<String> creditType;   // minOccurs=0 maxOccurs="unbounded"
    private ArrayList<Element> link;        // minOccurs=0 maxOccurs="unbounded"
    private ArrayList<Element> bookmark;    // minOccurs=0 maxOccurs="unbounded"
    private Element creditImage;
    private Element creditWords;

    // CONSTRUCTORS
    public Credit() {
        creditType = new ArrayList<String>();
        link = new ArrayList<Element>();
        bookmark = new ArrayList<Element>();
    }


    // GETTERS
    public Attribute getPageAttribute() {
        return pageAttribute;
    }

    public ArrayList<String> getCreditType() {
        return creditType;
    }

    public ArrayList<Element> getLink() {
        return link;
    }

    public ArrayList<Element> getBookmark() {
        return bookmark;
    }

    public Element getCreditImage() {
        return creditImage;
    }

    public Element getCreditWords() {
        return creditWords;
    }


    // SETTERS
    public void setPageAttribute(Attribute pageAtt) {
        pageAttribute = pageAtt;
    }

    public void setCreditImage(Element CI) {
        creditImage = CI;
    }

    public void setCreditWords(Element CW) {
        creditWords = CW;
    }


    // ADD TO
    public void addToCreditType(String ct) {
        creditType.add(ct);
    }

    public void addToLink(Element element) {
        link.add(element);
    }

    public void addToBookmark(Element element) {
        bookmark.add(element);
    }
}
