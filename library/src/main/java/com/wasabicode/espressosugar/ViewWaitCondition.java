package com.wasabicode.espressosugar;

import android.view.View;
import org.hamcrest.Matcher;

public interface ViewWaitCondition {
    boolean isSatisfied(View view);

    String getDescription(Matcher<View> viewMatcher);
}
