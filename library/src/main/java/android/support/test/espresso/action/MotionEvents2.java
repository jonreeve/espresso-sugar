package android.support.test.espresso.action;

import android.support.test.espresso.UiController;
import android.view.MotionEvent;

/**
 * Makes package-level things in {@link android.support.test.espresso.action.MotionEvents} accessible to us, so we can give
 * Espresso the extensible API it should have.
 */
public class MotionEvents2 {
    public static MotionEvents2.DownResultHolder sendDown(UiController uiController, float[] coordinates, float[] precision) {
        return new DownResultHolder(MotionEvents.sendDown(uiController, coordinates, precision));
    }

    public static boolean sendUp(UiController uiController, MotionEvent downEvent) {
        return MotionEvents.sendUp(uiController, downEvent);
    }

    public static boolean sendUp(UiController uiController, MotionEvent downEvent, float[] coordinates) {
        return MotionEvents.sendUp(uiController, downEvent, coordinates);
    }

    public static void sendCancel(UiController uiController, MotionEvent downEvent) {
        MotionEvents.sendCancel(uiController, downEvent);
    }

    public static boolean sendMovement(UiController uiController, MotionEvent downEvent, float[] coordinates) {
        return MotionEvents.sendMovement(uiController, downEvent, coordinates);
    }

    public static class DownResultHolder {
        public final MotionEvent down;
        public final boolean longPress;

        DownResultHolder(MotionEvents.DownResultHolder original) {
            this.down = original.down;
            this.longPress = original.longPress;
        }
    }
}