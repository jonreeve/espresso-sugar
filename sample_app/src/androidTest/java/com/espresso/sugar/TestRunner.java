package com.espresso.sugar;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnitRunner;

public class TestRunner extends AndroidJUnitRunner {
    @Override
    public void onCreate(final Bundle arguments) {
        arguments.putString("disableAnalytics", "true"); // Don't need espresso calling home for this
        super.onCreate(arguments);
    }
}
