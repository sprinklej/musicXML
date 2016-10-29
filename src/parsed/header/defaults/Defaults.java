package parsed.header.defaults;

import parsed.Attribute;
import parsed.Element;
import sun.jvm.hotspot.debugger.posix.elf.ELFException;

import java.security.interfaces.ECKey;
import java.util.ArrayList;

/**
 * Created by sprinklej on 2016-10-19.
 * FROM XSD: The defaults type specifies score-wide defaults for scaling, layout, and appearance.
 */
public class Defaults {
    // SCALING SUBTREE - minOccurs=0
    private boolean scaling = false;
    private String scalingMillimeters;
    private String scalingTenths;

    // PAGE-LAYOUT SUBTREE
    private boolean pageLayout = false;
    private String pageHeight; // minOccurs=0
    private String pageWidth;  // minOccurs=0
    private ArrayList<PageMargins> pageMargins; // minOccurs=0 maxOccurs=2 - attribute: type

    // SYSTEM-LAYOUT SUBTREE - minOccurs=0
    private boolean systemLayout;  // SUBTREE
    private boolean systemMargins; // SYS-LAYOUT SUBTREE - minOccurs=0
    private String leftSysMargin;
    private String rightSysMargin;

    private String systemDistance; // minOccurs=0
    private String topSysDistance; // minOccurs=0

    private boolean systemDividers = false; // SYS-LAYOUT SUBTREE - minOccurs=0
    private ArrayList<Attribute> leftDivider;
    private ArrayList<Attribute> rightDivider;

    // STAFF-LAYOUT SUBTREE - minOccurs=0 maxOccurs="unbounded"
    private ArrayList<StaffLayout> staffLayout;

    // APPEARANCE - minOccurs=0
    private boolean appearance;
    private ArrayList<Element> lineWidth;       // minOccurs=0 -attribute required
    private ArrayList<Element> noteSize;        // minOccurs=0 -attribute required
    private ArrayList<Element> distance;        // minOccurs=0 -attribute required
    private ArrayList<Element> otherAppearance; // minOccurs=0-attribute required

    // MUSIC-FONT - minOccurs=0
    Element musicFont; // empty element - all attributes

    // WORD-FONT - minOccurs=0
    Element wordFont;  // empty element - all attributes

    // LYRIC-FONT - minOccurs=0 maxOccurs="unbounded"
    private ArrayList<Element> lyricFont;

    // LYRIC-LANGUAGE - minOccurs=0 maxOccurs="unbounded"
    private ArrayList<Element> lyricLanguage;


    // CONSTRUCTOR
    public Defaults() {
        pageMargins = new ArrayList<PageMargins>();
        leftDivider = new ArrayList<Attribute>();
        rightDivider = new ArrayList<Attribute>();
        staffLayout = new ArrayList<StaffLayout>();

        lineWidth = new ArrayList<Element>();
        noteSize = new ArrayList<Element>();
        distance = new ArrayList<Element>();
        otherAppearance = new ArrayList<Element>();

        lyricFont = new ArrayList<Element>();
        lyricLanguage = new ArrayList<Element>();
    }

    // GETTERS
    public boolean getScaling() {
        return scaling;
    }

    public String getScalingMillimeters() {
        return scalingMillimeters;
    }

    public String getScalingTenths() {
        return scalingTenths;
    }

    public boolean getPageLayout() {
        return pageLayout;
    }

    public String getPageHeight() {
        return pageHeight;
    }

    public String getPageWidth() {
        return pageWidth;
    }

    public ArrayList<PageMargins> getPageMargins() {
        return pageMargins;
    }

    public boolean getSystemLayout() {
        return systemLayout;
    }

    public boolean getSystemMargins() {
        return systemMargins;
    }

    public String getLeftSysMargin() {
        return leftSysMargin;
    }

    public String getRightSysMargin() {
        return rightSysMargin;
    }

    public String getSystemDistance() {
        return systemDistance;
    }

    public String getTopSysDistance() {
        return  topSysDistance;
    }

    public boolean getSystemDividers() {
        return systemDividers;
    }

    public ArrayList<Attribute> getLeftDivider() {
        return leftDivider;
    }

    public ArrayList<Attribute> getRightDivider() {
        return rightDivider;
    }

    public ArrayList<StaffLayout> getStaffLayout() {
        return staffLayout;
    }

    public boolean getAppearance() {
        return appearance;
    }

    public ArrayList<Element> getLineWidth() {
        return lineWidth;
    }

    public ArrayList<Element> getNoteSize() {
        return noteSize;
    }

    public ArrayList<Element> getDistance() {
        return distance;
    }

    public ArrayList<Element> getOtherAppearance() {
        return otherAppearance;
    }

    public Element getMusicFont() {
        return musicFont;
    }

    public Element getWordFont() {
        return wordFont;
    }

    public ArrayList<Element> getLyricFont() {
        return lyricFont;
    }

    public ArrayList<Element> getLyricLanguage() {
        return lyricLanguage;
    }

    // SETTERS
    public void setScaling(boolean aScaling) {
        scaling = aScaling;
    }

    public void setScalingMillimeters(String aMillimeters) {
        scalingMillimeters = aMillimeters;
    }

    public void setScalingTenths(String aTenths) {
        scalingTenths = aTenths;
    }

    public void setPageLayout(boolean pLayout) {
        pageLayout = pLayout;
    }

    public void setPageHeight(String pHeight) {
        pageHeight = pHeight;
    }

    public void setPageWidth(String pWidth) {
        pageWidth = pWidth;
    }

    public void setSystemLayout(boolean sysLayout) {
        systemLayout = sysLayout;
    }

    public void setSystemMargins(boolean sysMargin) {
        systemMargins = sysMargin;
    }

    public void setLeftSysMargin(String lSysM) {
        leftSysMargin = lSysM;
    }

    public void setRightSysMargin(String rSysM) {
        rightSysMargin = rSysM;
    }

    public void setSystemDistance(String sysDist) {
        systemDistance = sysDist;
    }

    public void setTopSysDistance(String topSysDis) {
        topSysDistance = topSysDis;
    }

    public void setSystemDividers(boolean sysDividers) {
        systemDividers = sysDividers;
    }

    public void setAppearance(boolean app) {
        appearance = app;
    }

    public void setMusicFont(Element e) {
        musicFont = e;
    }

    public void setWordFont(Element e) {
        wordFont = e;
    }



    // ADD TO
    public void addToPageMargins(PageMargins pMargin) {
        pageMargins.add(pMargin);
    }

    public void addToLeftDivider(Attribute attribute) {
        leftDivider.add(attribute);
    }

    public void addToRightDivider(Attribute attribute) {
        rightDivider.add(attribute);
    }

    public void addToStaffLayout(StaffLayout sLayout) {
        staffLayout.add(sLayout);
    }

    public void addToLineWidth(Element e) {
        lineWidth.add(e);
    }

    public void addToNoteSize(Element e) {
        noteSize.add(e);
    }

    public void addToDistance(Element e) {
        distance.add(e);
    }

    public void addToOtherAppearance(Element e) {
        otherAppearance.add(e);
    }

    public void addTolyricFont(Element e) {
        lyricFont.add(e);
    }

    public void addToLyricLanguage(Element e) {
        lyricLanguage.add(e);
    }
}