package parsed;

/**
 * Created by sprinklej on 2016-10-26.
 * Referenced By: opus
 * FROM XSD: The link-attributes group includes all the simple XLink attributes supported in the MusicXML format.
 * More info on XLINK here http://www.w3schools.com/xml/xml_xlink.asp
 */
public class LinkAttributes {
    private String href; // if null then error - use is required
    private String type = "simple"; // always fixed to simple
    private String role;
    private String title;
    private String show = "replace"; // default value
    private String actuate = "onRequest"; // default value
    private String xmlns = "xmlns:xlink=\"http://www.w3.org/1999/xlink\""; //required in opus or one of its parents to use xlink

    // CONSTRUCTOR
    public LinkAttributes() {}


    // GETTERS
    public String getHref() {
        return href;
    }

    public String getType() {
        return type;
    }

    public String getRole() {
        return role;
    }

    public String getTitle() {
        return title;
    }

    public String getShow() {
        return show;
    }

    public String getActuate() {
        return actuate;
    }

    public String getXmlns() {
        return xmlns;
    }


    // SETTERS
    public void setHref(String aHref) {
        href = aHref;
    }

    // Setting the type is not valid

    public void setRole(String aRole) {
        role = aRole;
    }

    public void setTitle(String aTitle) {
        title = aTitle;
    }

    public void setShow(String aShow) {
        show = aShow;
    }

    public void setActuate(String aActuate) {
        actuate = aActuate;
    }

    // Setting the xmlns is not valid
}

