package tests.acceptance;

import android.support.test.runner.AndroidJUnit4;
import com.espresso.sugar.ActivityTest;
import com.espresso.sugar.sample.MainActivity;
import com.espresso.sugar.sample.R;
import com.espresso.sugar.sample.UiInteractionListener;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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
        //onView(withText(R.string.button1)).perform(click());

        // Much better :)
        clickView(withText(R.string.button1_text));

        mockery.assertIsSatisfied();
    }
}