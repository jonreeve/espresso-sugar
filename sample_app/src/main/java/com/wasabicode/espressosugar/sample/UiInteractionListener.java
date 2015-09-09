package com.wasabicode.espressosugar.sample;

import android.view.View;

/**
 * Acts as a delegate for all the UI events, so we can verify them in tests.
 */
public interface UiInteractionListener extends View.OnClickListener, View.OnDragListener, View.OnTouchListener, View.OnKeyListener {
}
