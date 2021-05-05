package com.tugraz.asd.modernnewsgroupapp


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class NewsgroupServerMenuTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashScreen::class.java)

    @Test
    fun newsgroupServerMenuTest() {
        val appCompatEditText = onView(
                allOf(withId(R.id.editText_name),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linearLayout2),
                                        1),
                                1),
                        isDisplayed()))
        appCompatEditText.perform(replaceText("m"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
                allOf(withId(R.id.editText_email),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linearLayout2),
                                        1),
                                2),
                        isDisplayed()))
        appCompatEditText2.perform(replaceText("m@x.m"), closeSoftKeyboard())

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

        val checkBox = onView(
                allOf(withText("tu-graz.algorithmen"),
                        childAtPosition(
                                allOf(withId(R.id.view_subscribe),
                                        childAtPosition(
                                                withId(R.id.scrollView2),
                                                0)),
                                0)))
        checkBox.perform(scrollTo(), click())

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

        val appCompatSpinner = onView(
                allOf(withId(R.id.newsgroups_List),
                        childAtPosition(
                                allOf(withId(R.id.simple_spinner_dropdown_item),
                                        childAtPosition(
                                                withId(R.id.linearLayout2),
                                                0)),
                                1),
                        isDisplayed()))
        appCompatSpinner.perform(click())

        val materialTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(`is`("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(0)
        materialTextView.perform(click())

        val appCompatSpinner2 = onView(
                allOf(withId(R.id.newsgroups_List),
                        childAtPosition(
                                allOf(withId(R.id.simple_spinner_dropdown_item),
                                        childAtPosition(
                                                withId(R.id.linearLayout2),
                                                0)),
                                1),
                        isDisplayed()))
        appCompatSpinner2.perform(click())

        val textView = onView(
                allOf(withId(android.R.id.text1), withText("news.tugraz.at"),
                        withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                        isDisplayed()))
        textView.check(matches(withText("news.tugraz.at")))
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
