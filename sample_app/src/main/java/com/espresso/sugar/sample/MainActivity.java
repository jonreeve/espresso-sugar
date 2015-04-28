package com.espresso.sugar.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

/**
 * Sample activity that passes all interaction onto a listener so we can spy and verify on that in tests.
 */
public class MainActivity extends Activity implements View.OnClickListener, View.OnDragListener, View.OnTouchListener {
    private UiInteractionListener uiInteractionListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        uiInteractionListener.onClick(v);
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        return uiInteractionListener.onDrag(v, event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return uiInteractionListener.onTouch(v, event);
    }

    public void setInteractionListener(UiInteractionListener interactionListener) {
        this.uiInteractionListener = interactionListener;
    }
}
