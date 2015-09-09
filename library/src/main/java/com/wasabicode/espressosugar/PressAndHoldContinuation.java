package com.wasabicode.espressosugar;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.*;
import android.support.test.espresso.matcher.ViewMatchers;
import android.view.MotionEvent;
import android.view.View;
import org.hamcrest.Matcher;

import javax.annotation.Nonnull;

import static org.hamcrest.Matchers.anything;

public class PressAndHoldContinuation {
    @Nonnull private final DragContext dragContext;
    @Nonnull private PrecisionDescriber precisionDescriber = Press.FINGER;
    @Nonnull private CoordinatesProvider coordinatesProvider = GeneralLocation.CENTER;

    public static PressAndHoldContinuation pressAndHoldView(@Nonnull Matcher<View> viewMatcher) {
        return new PressAndHoldContinuation(viewMatcher).perform();
    }

    public static PressAndHoldContinuation pressAndHoldView(@Nonnull final DragContext dragContext) {
        return new PressAndHoldContinuation(dragContext).perform();
    }

    private PressAndHoldContinuation(@Nonnull Matcher<View> viewMatcher) {
        this(new DragContext(viewMatcher));
    }

    private PressAndHoldContinuation(@Nonnull final DragContext dragContext) {
        this.dragContext = dragContext;
    }

    public PressAndHoldContinuation until(@Nonnull final WaitCondition condition) {
        dragContext.perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return anything();
            }

            @Override
            public String getDescription() {
                return "until " + condition.getDescription();
            }

            @Override
            public void perform(UiController uiController, View view) {
                while(!condition.isSatisfied()) {
                    uiController.loopMainThreadForAtLeast(50);
                }
            }
        });
        return this;
    }

    public PressAndHoldContinuation untilView(@Nonnull final Matcher<View> viewMatcher, @Nonnull final ViewWaitCondition condition) {
        dragContext.perform(viewMatcher, new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return anything();
            }

            @Override
            public String getDescription() {
                return "until " + condition.getDescription(dragContext.getViewMatcher());
            }

            @Override
            public void perform(UiController uiController, View view) {
                while (!condition.isSatisfied(view)) {
                    uiController.loopMainThreadForAtLeast(50);
                }
            }
        });
        return this;
    }

    public PressAndHoldContinuation untilIt(@Nonnull final ViewWaitCondition condition) {
        return untilView(dragContext.getViewMatcher(), condition);
    }


    public PressAndHoldContinuation then() {
        return this;
    }

    public DragContinuation drag() {
        return new DragContinuation(dragContext);
    }

    public void andDrop() {
        new DropContinuation(dragContext);
    }

    private PressAndHoldContinuation perform() {
        dragContext.perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isDisplayingAtLeast(90);
            }

            @Override
            public String getDescription() {
                return "drag";
            }

            @Override
            public void perform(UiController uiController, View view) {
                float[] coordinates = coordinatesProvider.calculateCoordinates(view);
                float[] precision = precisionDescriber.describePrecision();

                MotionEvent downEvent = MotionEvents2.sendDown(uiController, coordinates, precision).down;
                dragContext.setDownEvent(downEvent);
            }
        });
        return this;
    }
}
