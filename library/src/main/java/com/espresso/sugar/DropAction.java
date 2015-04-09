package com.espresso.sugar;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.MotionEvents2;
import android.support.test.espresso.matcher.ViewMatchers;
import android.view.MotionEvent;
import android.view.View;
import org.hamcrest.Matcher;

import javax.annotation.Nonnull;

public class DropAction {
    @Nonnull private final DragContext dragContext;

    public DropAction(@Nonnull final DragContext dragContext) {
        this.dragContext = dragContext;

        dragContext.perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isDisplayingAtLeast(90);
            }

            @Override
            public String getDescription() {
                return "drop";
            }

            @Override
            public void perform(UiController uiController, View view) {
                final MotionEvent downEvent = dragContext.getDownEvent();
                try {
                    if (!MotionEvents2.sendUp(uiController, downEvent)) {
                        MotionEvents2.sendCancel(uiController, downEvent);
                    }
                }
                finally {
                    downEvent.recycle(); // TODO dirty that we're recycling it here...
                }
            }
        });
    }
}
