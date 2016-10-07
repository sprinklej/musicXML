package parsed;

/**
 * Created by sprinklej on 2016-10-06.
 */
public class Measure {
    private String number;
    private String width;

    public void Measure(String aNumber, String aWidth) {
        number = aNumber;
        width = aWidth;
    }

    // GETTERS
    public String getNumber() {
        return number;
    }

    public String getWidth() {
        return width;
    }

    //SETTERS
    public void setNumber(String aNumber) {
        number = aNumber;
    }

    public void setWidth(String aWidth) {
        width = aWidth;
    }
}
