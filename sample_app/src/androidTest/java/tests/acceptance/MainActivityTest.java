package tests.acceptance;

import android.support.test.runner.AndroidJUnit4;
import com.espresso.sugar.ActivityTest;
import com.espresso.sugar.sample.MainActivity;
import com.espresso.sugar.sample.R;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.espresso.sugar.EspressoSugar.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest extends ActivityTest<MainActivity> {
    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Test
    public void test() {
        activityStarted();

        // Raw Espresso, yuk.
//        onView(withId(R.id.image)).perform(click());

        clickView(withId(R.id.image));
        type("Hello").intoView(withId(R.id.editText));
        type(R.string.app_name).intoView(withId(R.id.editText));
        scrollToView(withId(R.id.image));

        pressAndHoldView(withId(R.id.image)).andDrop();

//        View v;
//        v.animate().alpha();

        pressAndHoldView(withId(R.id.image)).andDrop();
//        pressAndHoldView(withId(R.id.image)).until(condition).then().drag()...;

        // TODO clever scrolls to find things, that actually do what a user would

        // Later
//        dragView(withId(R.id.image)).overView(withId(R.id.image)).andDrop();
//        pressAndHoldView(withId(R.id.image)).then().drag().overView(withId(R.id.image)).andDrop();
//        pressAndHoldView(withId(R.id.image)).thenAfter(10, SECONDS).drag()...;
//        pressAndHoldView(withId(R.id.image)).until(XXX).then().drag()...;
//        pressAndHoldView(withId(R.id.image)).until(XXX).thenAfter(10, SECONDS).drag()...;
//        pressAndHoldView(withId(R.id.image)).untilIt(turnsGreen()).thenAfter(10, SECONDS).drag()...;
//        pressAndHoldView(withId(R.id.image)).untilView(withText("Test"), turnsGreen()).thenAfter(10, SECONDS).drag()...;
//        pressAndHoldView(withId(R.id.image)).then().drag().overView(withId(R.id.image)).andHold(); // Terminate in the same way the animation framework does? Then drop or error? Configurable?
//        pressAndHoldView(withId(R.id.image)).then().drag().overView(withId(R.id.image)).andHold().untilIt(); // "It" should be the view you're dragging, always - consistent, simple, other one not always present
    }
}