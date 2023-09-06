package myApp.distribute.restapi;

/**
 * Wrapper class that send information about location to SpringBoot API
 * in the form of (x,y) coordinates.
 * X - horizontal distance from leftmost cell
 * Y - vertical distance topmost cell
 * Contains only a static factory method for creation.
 */
public class ApiLocationWrapper {
    public int x;
    public int y;

    public static ApiLocationWrapper makeFromLocation(int x, int y) {
        ApiLocationWrapper locationWrapper = new ApiLocationWrapper();
        locationWrapper.x = x;
        locationWrapper.y = y;
        return locationWrapper;
    }
}
