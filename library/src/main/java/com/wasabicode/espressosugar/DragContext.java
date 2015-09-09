package com.wasabicode.espressosugar;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.view.MotionEvent;
import android.view.View;
import org.hamcrest.Matcher;

import javax.annotation.Nonnull;

public class DragContext {
    @Nonnull private final Matcher<View> viewMatcher;
    private MotionEvent downEvent;

    public DragContext(Matcher<View> viewMatcher) {
        this.viewMatcher = viewMatcher;
    }

    public void addAction(ViewAction viewAction) {
        // TODO implement com.wasabicode.test.espresso.sugar.DragContext#addAction

    }

    public void setDownEvent(MotionEvent downEvent) {
        this.downEvent = downEvent;
    }

    public MotionEvent getDownEvent() {
        return downEvent;
    }

    @Nonnull
    public Matcher<View> getViewMatcher() {
        return viewMatcher;
    }

    public void perform(ViewAction viewAction) {
        perform(viewMatcher, viewAction);
    }

    public void perform(Matcher<View> viewMatcher, ViewAction viewAction) {
        Espresso.onView(viewMatcher).perform(viewAction);
    }
}
