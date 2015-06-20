package tests.acceptance;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import com.espresso.sugar.ActivityTest;
import com.espresso.sugar.WaitCondition;
import com.espresso.sugar.sample.MainActivity;
import com.espresso.sugar.sample.R;
import com.espresso.sugar.sample.UiInteractionListener;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.States;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.Executors;

import static android.support.test.espresso.matcher.ViewMatchers.*;
import static com.espresso.sugar.EspressoSugar.*;
import static java.util.concurrent.TimeUnit.SECONDS;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest extends ActivityTest<MainActivity> {
    private Mockery mockery = new Mockery();

    private UiInteractionListener uiInteractionListener = mockery.mock(UiInteractionListener.class);
    private FakeWaitCondition fakeCondition = mockery.mock(FakeWaitCondition.class);

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        final MainActivity activity = getActivity();
//        activity.setInteractionListener(uiInteractionListener);
        activity.setInteractionListener(new LoggingUiInteractionListener(uiInteractionListener));
    }

    @After
    public void tearDown() throws Exception {
        mockery.assertIsSatisfied();
    }

    @Test
    public void canClickOnAView() {
        mockery.checking(new Expectations() {{
            oneOf(uiInteractionListener).onClick(with(withId(R.id.button1)));
        }});

        // Espresso
        // onView(withText(R.string.button1)).perform(click());

        // Sugared
        clickView(withText(R.string.button1_text));
    }

    @Test
    public void canTypeIntoAView() {
        expectTypingTestIntoView(withId(R.id.editText1));

        // Espresso
        // onView(withId(R.id.editText1)).perform(type("test"));

        // Sugared
        type("test").intoView(withId(R.id.editText1));
    }

    @Test
    public void canTypeResourceStringIntoAView() {
        expectTypingTestIntoView(withId(R.id.editText1));

        type(R.string.test).intoView(withId(R.id.editText1));
    }

    @Test
    public void canScrollToView() {
        final int halfArea = 50;
        final View viewWeScrollTo = getActivity().findViewById(R.id.needs_scrolling_to);

        scrollToView(withId(R.id.needs_scrolling_to));

        assertThat(viewWeScrollTo, isDisplayingAtLeast(halfArea));
    }

    @Test
    public void canPressAndHoldThenDrop() {
        mockery.checking(new Expectations(){{
            final Sequence drag = mockery.sequence("drag");

            oneOf(uiInteractionListener).onTouch(with(withId(R.id.draggable)), with(aMotionEventWith(MotionEvent.ACTION_DOWN)));
            will(returnValue(Boolean.TRUE));
            inSequence(drag);

            oneOf(uiInteractionListener).onTouch(with(withId(R.id.draggable)), with(aMotionEventWith(MotionEvent.ACTION_UP)));
            will(returnValue(Boolean.TRUE));
            inSequence(drag);
        }});

        pressAndHoldView(withId(R.id.draggable)).andDrop();
    }

    @Test
    public void canHoldViewUntilConditionIsSatisfied() throws InterruptedException {
        mockery.checking(new Expectations() {{
            allowing(uiInteractionListener).onTouch(with(withId(any(Integer.class))), with(aMotionEventWith(MotionEvent.ACTION_CANCEL)));
            will(returnValue(true));

            final States conditionState = mockery.states("conditionState").startsAs("waiting");

            atLeast(1).of(uiInteractionListener).onTouch(with(withId(R.id.draggable)), with(aMotionEventWith(MotionEvent.ACTION_DOWN)));
            will(returnValue(Boolean.TRUE));
            when(conditionState.is("waiting"));

            oneOf(uiInteractionListener).onTouch(with(withId(R.id.draggable)), with(aMotionEventWith(MotionEvent.ACTION_UP)));
            will(returnValue(Boolean.TRUE));
            when(conditionState.is("satisfied"));

            allowing(fakeCondition).getDescription();
            will(returnValue("Fake wait condition"));

            allowing(fakeCondition).isSatisfied();
            will(returnValue(Boolean.FALSE));
            when(conditionState.isNot("satisfied"));

            allowing(fakeCondition).isSatisfied();
            will(returnValue(Boolean.TRUE));
            when(conditionState.is("satisfied"));

            oneOf(fakeCondition).satisfy();
            then(conditionState.is("satisfied"));
        }});

        Executors.newSingleThreadScheduledExecutor().schedule(new Runnable() {
            @Override
            public void run() {
                fakeCondition.satisfy();
            }
        }, 2, SECONDS);

        pressAndHoldView(withId(R.id.draggable)).until(fakeCondition).andDrop();
    }

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

    private void expectTypingTestIntoView(final Matcher<View> viewMatcher) {
        mockery.checking(new Expectations() {
            {
                keyPress(KeyEvent.KEYCODE_T);
                keyPress(KeyEvent.KEYCODE_E);
                keyPress(KeyEvent.KEYCODE_S);
                keyPress(KeyEvent.KEYCODE_T);
            }

            private void keyPress(int keycode) {
                oneOf(uiInteractionListener).onKey(with(viewMatcher), with(keycode), with(aKeyEventWith(KeyEvent.ACTION_DOWN)));
                will(returnValue(true));

                oneOf(uiInteractionListener).onKey(with(viewMatcher), with(keycode), with(aKeyEventWith(KeyEvent.ACTION_UP)));
                will(returnValue(true));
            }
        });
    }

    private Matcher<MotionEvent> aMotionEventWith(final int action) {
        return new TypeSafeMatcher<MotionEvent>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("a MotionEvent with action " + MotionEvent.actionToString(action));
            }

            @Override
            public boolean matchesSafely(MotionEvent motionEvent) {
                return motionEvent.getAction() == action;
            }
        };
    }

    private Matcher<KeyEvent> aKeyEventWith(final int action) {
        return new TypeSafeMatcher<KeyEvent>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("a KeyEvent with action " + MotionEvent.actionToString(action));
            }

            @Override
            public boolean matchesSafely(KeyEvent keyEvent) {
                return keyEvent.getAction() == action;
            }
        };
    }

    private static class LoggingUiInteractionListener implements UiInteractionListener {
        public static final String LOG_TAG = "UI-Interaction";
        private final UiInteractionListener delegate;

        public LoggingUiInteractionListener(UiInteractionListener delegate) {
            this.delegate = delegate;
        }

        @Override
        public void onClick(View v) {
            Log.d(LOG_TAG, "onClick " + v);
            delegate.onClick(v);
        }

        @Override
        public boolean onDrag(View v, DragEvent event) {
            Log.d(LOG_TAG, "onDrag " + v + ", " + event);
            return delegate.onDrag(v, event);
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            Log.d(LOG_TAG, "onKey " + v + ", " + keyCode + ", " + event);
            return delegate.onKey(v, keyCode, event);
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Log.d(LOG_TAG, "onTouch " + v + ", " + event);
            return delegate.onTouch(v, event);
        }
    }

    private static class StubWaitCondition implements WaitCondition {
        volatile boolean satisfied = false;

        @Override
        public boolean isSatisfied() {
            return satisfied;
        }

        @Override
        public String getDescription() {
            return "Stub Condition";
        }

        public void satisfy() {
            this.satisfied = true;
        }
    }

    private static interface FakeWaitCondition extends WaitCondition {
        public void satisfy();
    }
}
