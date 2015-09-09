package com.wasabicode.espressosugar;

import android.support.test.espresso.Espresso;
import android.view.View;
import org.hamcrest.Matcher;

import javax.annotation.Nonnull;

import static android.support.test.espresso.action.ViewActions.typeText;

public class TypeContinuation {
    @Nonnull private final String textToBeTyped;

    public TypeContinuation(@Nonnull String textToBeTyped) {
        this.textToBeTyped = textToBeTyped;
    }

    public TypeCompleteContinuation into(@Nonnull final Matcher<View> viewMatcher) {
        Espresso.onView(viewMatcher).perform(typeText(textToBeTyped));
        return new TypeCompleteContinuation(viewMatcher);
    }
}
