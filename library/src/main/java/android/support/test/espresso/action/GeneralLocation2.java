package android.support.test.espresso.action;

/**
 * Makes package-level things in {@link android.support.test.espresso.action.GeneralLocation} accessible to us, so we can give
 * Espresso the extensible API it should have.
 */
public class GeneralLocation2 {
    /**
     * Makes {@link android.support.test.espresso.action.GeneralLocation#translate(CoordinatesProvider, float, float)} public.
     */
    public static CoordinatesProvider translate(CoordinatesProvider coords, float dx, float dy) {
        return GeneralLocation.translate(coords, dx, dy);
    }
}
