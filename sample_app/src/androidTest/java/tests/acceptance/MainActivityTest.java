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
//        onView(withId(R.id.image)).perform(click());

        activityStarted();

        click().onView(withId(R.id.image));

        click().on(view(withId()));


        onView(withId(R.id.image)).perform(click());
        click(onView(withId(R.id.image)));



        click().onView().withId(R.id.image);

//        click().on(button(withId()));
//
//        click().on(button("OK"));
//
//        click().on(button(R.string.abc_action_bar_home_description));
//        click().on(button(R.id.view_attributes));
//
    }
}