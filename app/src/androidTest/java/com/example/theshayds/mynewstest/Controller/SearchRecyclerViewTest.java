package com.example.theshayds.mynewstest.Controller;


import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.theshayds.mynewstest.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SearchRecyclerViewTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void searchRecyclerViewTest() {
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.search), withContentDescription("Search"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.search_query_text),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main_linear_layout),
                                        1),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.search_query_text),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main_linear_layout),
                                        1),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("Elon Musk"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.search_query_text), withText("Elon Musk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main_linear_layout),
                                        1),
                                0),
                        isDisplayed()));
        appCompatEditText5.perform(closeSoftKeyboard());

//        ViewInteraction appCompatButton = onView(
//                allOf(withId(R.id.button_being_date),
//                        childAtPosition(
//                                allOf(withId(R.id.fields_begin_date),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.LinearLayout")),
//                                                0)),
//                                1),
//                        isDisplayed()));
//        appCompatButton.perform(click());
//
//        ViewInteraction appCompatButton2 = onView(
//                allOf(withId(android.R.id.button1), withText("OK"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.ScrollView")),
//                                        0),
//                                3)));
//        appCompatButton2.perform(scrollTo(), click());
//
//        ViewInteraction appCompatButton3 = onView(
//                allOf(withId(R.id.button_end_date),
//                        childAtPosition(
//                                allOf(withId(R.id.fields_end_date),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.LinearLayout")),
//                                                0)),
//                                1),
//                        isDisplayed()));
//        appCompatButton3.perform(click());
//
//        ViewInteraction appCompatButton4 = onView(
//                allOf(withId(android.R.id.button1), withText("OK"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.ScrollView")),
//                                        0),
//                                3)));
//        appCompatButton4.perform(scrollTo(), click());

        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(R.id.checkbox_business), withText("Business"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatCheckBox.perform(click());

        ViewInteraction appCompatCheckBox2 = onView(
                allOf(withId(R.id.checkbox_entrepreneurs), withText("Entrepreneurs"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                3),
                        isDisplayed()));
        appCompatCheckBox2.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.button_search), withText("Search"),
                        childAtPosition(
                                allOf(withId(R.id.search_linear_layout),
                                        childAtPosition(
                                                withId(R.id.main_linear_layout),
                                                2)),
                                0),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.search_recycler_view),
                        withParent(withId(R.id.my_relative_layout)),
                        isDisplayed()));

        recyclerView.check(matches(hasMinimumChildCount(1)));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
