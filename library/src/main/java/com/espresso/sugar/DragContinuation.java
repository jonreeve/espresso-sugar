package com.espresso.sugar;

import android.view.View;
import org.hamcrest.Matcher;

import javax.annotation.Nonnull;

public class DragContinuation {
    @Nonnull private final DragContext dragContext;

    public DragContinuation(@Nonnull final DragContext dragContext) {
        this.dragContext = dragContext;
    }

    public DragOverViewAction overView(@Nonnull final Matcher<View> viewMatcher) {
        return new DragOverViewAction(viewMatcher, dragContext);
    }
}
