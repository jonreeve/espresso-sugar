package com.espresso.sugar.sample;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

/**
 * TODO: JAVADOC
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main);
    }
}
