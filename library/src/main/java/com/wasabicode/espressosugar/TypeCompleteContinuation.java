package com.wasabicode.espressosugar;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.view.View;
import org.hamcrest.Matcher;

import javax.annotation.Nonnull;

public class TypeCompleteContinuation {
    @Nonnull private final Matcher<View> viewMatcher;

    public TypeCompleteContinuation(@Nonnull final Matcher<View> viewMatcher) {
        this.viewMatcher = viewMatcher;
    }

    public void then(final ViewAction... actions) {
        Espresso.onView(viewMatcher).perform(actions);
    }
}
