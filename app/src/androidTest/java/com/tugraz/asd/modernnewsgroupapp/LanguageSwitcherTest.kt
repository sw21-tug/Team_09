package com.tugraz.asd.modernnewsgroupapp


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LanguageSwitcherTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun clearDb(){
        InstrumentationRegistry.getInstrumentation().getTargetContext().deleteDatabase("newsgroup.db")
    }

    @Test
    fun languageSwitcherTest() {
        Thread.sleep(3000)
        val appCompatEditText = onView(
                allOf(withId(R.id.editText_name),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linearLayout2),
                                        1),
                                1),
                        isDisplayed()))
        appCompatEditText.perform(replaceText("test"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
                allOf(withId(R.id.editText_email),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linearLayout2),
                                        1),
                                2),
                        isDisplayed()))
        appCompatEditText2.perform(replaceText("test@test.com"), closeSoftKeyboard())

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

        val appCompatImageButton = onView(
                allOf(withId(R.id.button_show_profile),
                        childAtPosition(
                                allOf(withId(R.id.simple_spinner_dropdown_item),
                                        childAtPosition(
                                                withId(R.id.linearLayout2),
                                                0)),
                                0),
                        isDisplayed()))
        appCompatImageButton.perform(click())

        val appCompatSpinner = onView(
                allOf(withId(R.id.spinner_language),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout6),
                                        childAtPosition(
                                                withId(R.id.linearLayout2),
                                                1)),
                                1),
                        isDisplayed()))
        appCompatSpinner.perform(click())

        val appCompatCheckedTextView = onView(allOf(withText("Russian")))
        appCompatCheckedTextView.perform(click())

        val materialButton3 = onView(
                allOf(withId(R.id.button_save_profile), withText("SAVE"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                withId(R.id.nav_host_fragment),
                                                0)),
                                2),
                        isDisplayed()))
        materialButton3.perform(click())

        val button = onView(
                allOf(withId(R.id.button_subscribe), withText(" ДАЛЕЕ СЛЕДУЮЩИЙ "),
                        withParent(allOf(withId(R.id.linearLayout5),
                                withParent(withId(R.id.linearLayout2)))),
                        isDisplayed()))
        button.check(matches(isDisplayed()))
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
