package parsed.header.defaults;

import parsed.Attribute;

/**
 * Created by sprinklej on 2016-10-28.
 */
public class StaffLayout {
    private Attribute attribute;  // not required
    private String staffDistance; // minOccurs=0

    // CONSTRUCTOR
    public StaffLayout() {}


    // GETTERS
    public Attribute getAttribute() {
        return attribute;
    }

    public String getStaffDistance() {
        return staffDistance;
    }


    //SETTERS
    public void setAttribute(Attribute att) {
        attribute = att;
    }

    public void setStaffDistance(String staffDist) {
        staffDistance = staffDist;
    }
}
