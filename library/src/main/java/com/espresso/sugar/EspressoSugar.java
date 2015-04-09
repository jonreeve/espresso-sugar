/*
 * Copyright (C) 2015 Wasabi Code Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.espresso.sugar;

import android.support.annotation.StringRes;
import android.support.test.espresso.Espresso;
import android.view.View;
import org.hamcrest.Matcher;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static org.hamcrest.Matchers.allOf;

/**
 * Espresso tests still don't read nicely enough (i.e. as proper sentences). These functions add syntactic sugar.
 */
public class EspressoSugar {
    public static void clickView(Matcher<View> viewMatcher) {
        Espresso.onView(viewMatcher).perform(click());
    }

    public static void clickView(Matcher<View>... viewMatchers) {
        clickView(allOf(viewMatchers));
    }

    public static TypeAction type(String textToBeTyped) {
        return new TypeAction(textToBeTyped);
    }

    public static TypeResourceStringAction type(@StringRes int textToBeTypedId) {
        return new TypeResourceStringAction(textToBeTypedId);
    }

    public static void scrollToView(Matcher<View> viewMatcher) {
        Espresso.onView(viewMatcher).perform(scrollTo());
    }

    public static PressAndHoldAction pressAndHoldView(Matcher<View> viewMatcher) {
        return PressAndHoldAction.pressAndHoldView(viewMatcher);
    }

//    public static ViewInteraction onView(Matcher<View>... viewMatchers) {
//        return Espresso.onView(allOf(viewMatchers));
//    }
//
//    public static ViewInteraction onView(Matcher<View> viewMatcher) {
//        return Espresso.onView(viewMatcher);
//    }
//
//    public static void checkThatView(Matcher<View> viewMatcher, Matcher<View> condition) {
//        Espresso.onView(viewMatcher).check(matches(condition));
//    }
//
//    public static void checkThatView(Matcher<View> viewMatcher, Matcher<View>... conditions) {
//        Espresso.onView(viewMatcher).check(matches(allOf(conditions)));
//    }
//
//    public static void checkThatView(Matcher<View> viewMatcher, ViewAssertion viewAssertion) {
//        Espresso.onView(viewMatcher).check(viewAssertion);
//    }
//
//    public static void checkThatView(ViewInteraction viewInteraction, ViewAssertion viewAssertion) {
//        viewInteraction.check(viewAssertion);
//    }
//
//    public static void checkThatView(ViewInteraction viewInteraction, Matcher<View> condition) {
//        viewInteraction.check(matches(condition));
//    }
//
//    public static void checkThatView(ViewInteraction viewInteraction, Matcher<View>... conditions) {
//        viewInteraction.check(matches(allOf(conditions)));
//    }
}
