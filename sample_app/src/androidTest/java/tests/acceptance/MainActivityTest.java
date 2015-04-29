package tests.acceptance;

import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;
import com.espresso.sugar.ActivityTest;
import com.espresso.sugar.sample.MainActivity;
import com.espresso.sugar.sample.R;
import com.espresso.sugar.sample.UiInteractionListener;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.espresso.sugar.EspressoSugar.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest extends ActivityTest<MainActivity> {
    private Mockery mockery = new Mockery();

    private UiInteractionListener uiInteractionListener = mockery.mock(UiInteractionListener.class);

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        final MainActivity activity = getActivity();
        activity.setInteractionListener(uiInteractionListener);
    }

    @Test
    public void canClickOnAView() {
        mockery.checking(new Expectations() {{
            oneOf(uiInteractionListener).onClick(with(withId(R.id.button1)));
        }});

        // Raw Espresso looks like this, yuk.
        // onView(withText(R.string.button1)).perform(click());

        // Much better :)
        clickView(withText(R.string.button1_text));

        mockery.assertIsSatisfied();
    }

    @Test
    public void canTypeIntoAView() {
        mockery.checking(new Expectations() {
            {
                keyPress(KeyEvent.KEYCODE_T);
                keyPress(KeyEvent.KEYCODE_E);
                keyPress(KeyEvent.KEYCODE_S);
                keyPress(KeyEvent.KEYCODE_T);
            }

            private void keyPress(int keycode) {
                oneOf(uiInteractionListener).onKey(with(withId(R.id.editText1)), with(keycode), with(any(KeyEvent.class)));
                oneOf(uiInteractionListener).onKey(with(withId(R.id.editText1)), with(keycode), with(any(KeyEvent.class)));
            }
        });

        // Raw Espresso version
        // onView(withId(R.id.editText1)).perform(type("test"));

        // Our version
        type("test").intoView(withId(R.id.editText1));

        mockery.assertIsSatisfied();
    }

//        type("Hello").intoView(withId(R.id.editText1));
//        type(R.string.app_name).intoView(withId(R.id.editText1));
//        scrollToView(withId(R.id.image));
//
//        pressAndHoldView(withId(R.id.image)).andDrop();
//        WaitCondition condition = new WaitCondition() {
//            @Override
//            public boolean isSatisfied() {
//                return true;
//            }
//
//            @Override
//            public String getDescription() {
//                return "(true)";
//            }
//        };
//        ViewWaitCondition viewCondition = new ViewWaitCondition() {
//            @Override
//            public boolean isSatisfied(View view) {
//                return view.getVisibility() == View.VISIBLE;
//            }
//
//            @Override
//            public String getDescription(Matcher<View> viewMatcher) {
//                return "is visible";
//            }
//        };
//        pressAndHoldView(withId(R.id.image)).until(condition).then().drag();
//        pressAndHoldView(withId(R.id.image)).untilIt(viewCondition).then().drag();
//        pressAndHoldView(withId(R.id.image)).untilView(withId(R.id.image), viewCondition).then().drag();
//        pressAndHoldView(withId(R.id.image)).then().drag().overView(withId(R.id.image)).andDrop();

//        View v;
//        v.animate().alpha();


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