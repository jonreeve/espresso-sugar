package com.espresso.sugar;

import android.support.test.espresso.*;
import android.support.test.espresso.action.*;
import android.support.test.espresso.matcher.ViewMatchers;
import android.view.View;
import org.hamcrest.Matcher;

import javax.annotation.Nonnull;

public class DragOverViewAction {
    @Nonnull private final Matcher<View> viewMatcher;
    @Nonnull private final DragContext dragContext;
    @Nonnull private PrecisionDescriber precisionDescriber = Press.FINGER;
    @Nonnull private CoordinatesProvider coordinatesProvider = GeneralLocation.CENTER;

    public DragOverViewAction(@Nonnull final Matcher<View> viewMatcher, @Nonnull final DragContext dragContext) {
        this.viewMatcher = viewMatcher;
        this.dragContext = dragContext;

        dragContext.perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isDisplayingAtLeast(10);
            }

            @Override
            public String getDescription() {
                return "drag over view " + viewMatcher.toString();
            }

            @Override
            public void perform(final UiController uiController, View draggingView) {
                Espresso.onView(viewMatcher).check(new ViewAssertion() {
                    @Override
                    public void check(View dragTargetView, NoMatchingViewException e) {
                        final float[] coordinates = coordinatesProvider.calculateCoordinates(dragTargetView);
                        MotionEvents2.sendMovement(uiController, dragContext.getDownEvent(), coordinates);
                    }
                });
            }
        });
    }

    public void andDrop() {
        new DropAction(dragContext);
    }
}
