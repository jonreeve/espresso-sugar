package com.espresso.sugar;

import android.support.test.espresso.Espresso;
import android.view.View;
import org.hamcrest.Matcher;

import javax.annotation.Nonnull;

import static android.support.test.espresso.action.ViewActions.typeText;

public class TypeAction {
    @Nonnull private final String textToBeTyped;

    public TypeAction(@Nonnull String textToBeTyped) {
        this.textToBeTyped = textToBeTyped;
    }

    public void intoView(Matcher<View> viewMatcher) {
        Espresso.onView(viewMatcher).perform(typeText(textToBeTyped));
    }
}
