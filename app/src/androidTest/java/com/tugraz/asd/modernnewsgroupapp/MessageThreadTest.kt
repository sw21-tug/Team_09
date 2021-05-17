package com.tugraz.asd.modernnewsgroupapp


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MessageThreadTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashScreen::class.java)

    @Test
    fun messageThreadTest() {
        Thread.sleep(5000)
        val materialButton = onView(
                allOf(withId(R.id.button_subscribe), withText("NEXT"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout5),
                                        childAtPosition(
                                                withId(R.id.linearLayout2),
                                                2)),
                                0),
                        isDisplayed()))
        materialButton.perform(click())

        val appCompatEditText = onView(
                allOf(withId(R.id.editTextGroupFilter),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout3),
                                        childAtPosition(
                                                withId(R.id.linearLayout2),
                                                0)),
                                2),
                        isDisplayed()))
        appCompatEditText.perform(replaceText("o"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
                allOf(withId(R.id.editTextGroupFilter), withText("o"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout3),
                                        childAtPosition(
                                                withId(R.id.linearLayout2),
                                                0)),
                                2),
                        isDisplayed()))
        appCompatEditText2.perform(click())

        val appCompatEditText3 = onView(
                allOf(withId(R.id.editTextGroupFilter), withText("o"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout3),
                                        childAtPosition(
                                                withId(R.id.linearLayout2),
                                                0)),
                                2),
                        isDisplayed()))
        appCompatEditText3.perform(replaceText("oad"))

        val appCompatEditText4 = onView(
                allOf(withId(R.id.editTextGroupFilter), withText("oad"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout3),
                                        childAtPosition(
                                                withId(R.id.linearLayout2),
                                                0)),
                                2),
                        isDisplayed()))
        appCompatEditText4.perform(closeSoftKeyboard())

        val materialCheckBox = onView(
                allOf(withId(R.id.checkBox), withText("tu-graz.lv.oad"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linear_scroll),
                                        41),
                                0),
                        isDisplayed()))
        materialCheckBox.perform(click())

        val materialButton2 = onView(
                allOf(withId(R.id.button_finish), withText("FINISH"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout4),
                                        childAtPosition(
                                                withId(R.id.linearLayout2),
                                                2)),
                                1),
                        isDisplayed()))
        materialButton2.perform(click())

        val textView = onView(
                allOf(withText("tu-graz.lv.oad"),
                        childAtPosition(
                                allOf(withId(R.id.view_show_subgroups),
                                        childAtPosition(
                                                withId(R.id.scrollView_show_subgroups),
                                                0)),
                                0)))
        textView.perform(scrollTo(), click())

        val appCompatImageButton = onView(
                allOf(withId(R.id.button_back),
                        childAtPosition(
                                allOf(withId(R.id.header_messages),
                                        childAtPosition(
                                                withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                0),
                        isDisplayed()))
        appCompatImageButton.perform(click())

        val textView2 = onView(
                allOf(withText("tu-graz.lv.oad"),
                        childAtPosition(
                                allOf(withId(R.id.view_show_subgroups),
                                        childAtPosition(
                                                withId(R.id.scrollView_show_subgroups),
                                                0)),
                                0)))
        textView2.perform(scrollTo(), click())

        val textView3 = onView(
                allOf(withText("15.05.2021 11:16\nAssignment 2 Interviews [18.05.2021]"),
                        withParent(allOf(withId(R.id.view_show_messages),
                                withParent(withId(R.id.scrollView_show_messages)))),
                        isDisplayed()))
        textView3.check(matches(withText("15.05.2021 11:16 Assignment 2 Interviews [18.05.2021]")))
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
