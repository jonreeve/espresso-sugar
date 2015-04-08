package tests.acceptance;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import com.espresso.sugar.ActivityTest;
import com.espresso.sugar.sample.MainActivity;
import com.espresso.sugar.sample.R;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest extends ActivityTest<MainActivity> {
    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Test
    public void test() {
        activityStarted();

        // Raw Espresso, yuk.
        onView(withId(R.id.image)).perform(click());

        dragView(withId(R.id.image)).overView(withId(R.id.image)).andDrop();

        pressAndHoldView(withId(R.id.image)).then().drag().overView(withId(R.id.image)).andDrop();
        pressAndHoldView(withId(R.id.image)).thenAfter(10, SECONDS).drag()...;
        pressAndHoldView(withId(R.id.image)).until(XXX).then().drag()...;
        pressAndHoldView(withId(R.id.image)).until(XXX).thenAfter(10, SECONDS).drag()...;
        pressAndHoldView(withId(R.id.image)).untilIt(turnsGreen()).thenAfter(10, SECONDS).drag()...;
        pressAndHoldView(withId(R.id.image)).untilView(withText("Test"), turnsGreen()).thenAfter(10, SECONDS).drag()...;
        pressAndHoldView(withId(R.id.image)).then().drag().overView(withId(R.id.image)).andHold(); // Terminate in the same way the animation framework does? Then drop or error? Configurable?
        pressAndHoldView(withId(R.id.image)).then().drag().overView(withId(R.id.image)).andHold().untilIt(); // "It" should be the view you're dragging, always - consistent, simple, other one not always present

        clickView(withId(R.id.image));

        type("Hello").onView(withId(R.id.image));

        scrollTo(something).inView(withId());

        // BAD
        click().onView(withId(R.id.image)); // pointless extra method, not same interface because they'd return their own things to chain on
        pressAndHold().onView(withId(R.id.image)).until(viewWithId()).thenAfter(10, SECONDS).drag().andDropOn(view(withId()));
        type("Hello").onView(withId(R.id.image)).until(viewWithId()).thenAfter(10, SECONDS).drag().andDropOn(view(withId()));

        pressAndHoldView(withId(R.id.image)).then().dragOverView(withId(R.id.image)).andDrop(); // too inflexible
        pressAndHoldView(withId(R.id.image), until(10, SECONDS)).thenRelease(); // bad language
        type("Hello", intoView(withId(R.id.image))); // import for no reason
        pressAndHoldView(withId(R.id.image)).until(viewWithId()).thenAfter(10, SECONDS).drag().andDropOn(view(withId())); // drop on, viewWithId
        pressAndHoldView(withId(R.id.image)).then().dragView(withId()).overView(withId(R.id.image)).andDrop(); // not dragging a view, just dragging finger

        pressAndHoldView(withId(R.id.image), until(10, SECONDS)).thenDrag().andDropOn(view(withId())); // until in chain now, can be omitted for a default
        pressAndHoldView(withId(R.id.image)).until(itTurnsGreen()).thenDrag().andDropOn(view(withId())); // untilIt, untilView or until(Waitable) more flexible

        dragView(withId(R.id.image)).andDropOn(view(withId())); // drop now ends infinite chaining, and drop target where we dragged to
        pressAndHoldView(withId(R.id.image)).thenDrag().andDropOn(view(withId())); // drag now separate
        pressAndHoldView(withId(R.id.image)).thenDragAfter(10, sec).andDropOn(view(withId())); // thenDragAfter combines time with next action, reverses things
    }
}