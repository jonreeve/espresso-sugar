package com.espresso.sugar;

import android.support.annotation.StringRes;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.TypeTextAction;
import android.view.View;
import org.hamcrest.Matcher;

import javax.annotation.Nonnull;

public class TypeResourceStringAction {
    private final int textToBeTypedId;

    public TypeResourceStringAction(@StringRes int textToBeTypedId) {

        this.textToBeTypedId = textToBeTypedId;
    }

    public void intoView(Matcher<View> viewMatcher) {
        Espresso.onView(viewMatcher).perform(new TypeResourceTextAction());
    }

    private class TypeResourceTextAction implements ViewAction {
        private TypeTextAction dummyTypeAction = new TypeTextAction("");

        @Override
        public Matcher<View> getConstraints() {
            return dummyTypeAction.getConstraints();
        }

        @Override
        public String getDescription() {
            return String.format("type text from string resource (%d)", textToBeTypedId);
        }

        @Override
        public void perform(UiController uiController, View view) {
            final String textToBeTyped = view.getContext().getString(textToBeTypedId);
            new TypeTextAction(textToBeTyped).perform(uiController, view);
        }
    }
}
