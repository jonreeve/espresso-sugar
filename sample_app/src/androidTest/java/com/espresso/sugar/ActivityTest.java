package com.espresso.sugar;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.util.Log;
import org.junit.After;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Like ActivityInstrumentationTestCase2 but for JUnit 4. Jake Wharton's ActivityRule will launch the activity before we get any chance to
 * run any test setup. If we want pre-launch setup that isn't simply changing the Intent, or might differ for different tests in the same
 * test class, then ActivityRule doesn't work so well.
 */
public class ActivityTest<T extends Activity> {
    private final Class<T> activityClass;

    private T               activity;
    private Instrumentation instrumentation;

    public ActivityTest(Class<T> activityClass) {
        this.activityClass = activityClass;
    }

    protected Intent getLaunchIntent(String targetPackage, Class<T> activityClass) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClassName(targetPackage, activityClass.getName());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    /**
     * Just a syntactic sugar method for launching the activity in a "given" block of a test.
     */
    public final void activityStarted() {
        getActivity();
    }

    /**
     * Get the running instance of the specified activity. This will launch it if it is not already
     * running.
     */
    public final T getActivity() {
        launchActivityIfNotLaunchedAlready();
        return activity;
    }

    /**
     * Get the {@link Instrumentation} instance for this test.
     */
    public final Instrumentation getInstrumentation() {
        Instrumentation result = instrumentation;
        return result != null ? result
                              : (instrumentation = InstrumentationRegistry.getInstrumentation());
    }

    @After
    public void tearDownActivity() throws Exception {
        // Finish the Activity off (unless was never launched anyway)
        if (activity != null && !activity.isFinishing()) {
            activity.finish();
        }
        activity = null;

        // Scrub out members - protects against memory leaks in the case where someone
        // creates a non-static inner class (thus referencing the test case) and gives it to
        // someone else to hold onto
        scrubClass(ActivityTest.class);
    }

    protected void scrubClass(final Class<?> testCaseClass)
            throws IllegalAccessException {
        final Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields) {
            final Class<?> fieldClass = field.getDeclaringClass();
            if (testCaseClass.isAssignableFrom(fieldClass) && !field.getType().isPrimitive()
                && (field.getModifiers() & Modifier.FINAL) == 0) {
                try {
                    field.setAccessible(true);
                    field.set(this, null);
                } catch (Exception e) {
                    Log.d("TestCase", "Error: Could not nullify field!");
                }

                if (field.get(this) != null) {
                    Log.d("TestCase", "Error: Could not nullify field!");
                }
            }
        }
    }

    @SuppressWarnings("unchecked") // Guarded by generics at the constructor.
    private void launchActivityIfNotLaunchedAlready() {
        if (activity != null) { return; }

        Instrumentation instrumentation = getInstrumentation();

        String targetPackage = instrumentation.getTargetContext().getPackageName();
        Intent intent = getLaunchIntent(targetPackage, activityClass);

        activity = (T) instrumentation.startActivitySync(intent);
        instrumentation.waitForIdleSync();
    }
}