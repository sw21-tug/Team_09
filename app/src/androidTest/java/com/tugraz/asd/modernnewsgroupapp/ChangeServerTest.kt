package com.tugraz.asd.modernnewsgroupapp


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ChangeServerTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun clearDb(){
        InstrumentationRegistry.getInstrumentation().getTargetContext().deleteDatabase("newsgroup.db")
    }

    @After
    fun after(){
        InstrumentationRegistry.getInstrumentation().getTargetContext().deleteDatabase("newsgroup.db")
    }

    @Test
    fun changeServerTest() {
        val appCompatEditText = onView(
            allOf(withId(R.id.editText_name),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.linearLayout2),
                        1),
                    1),
                isDisplayed()))
        appCompatEditText.perform(replaceText("pe"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(withId(R.id.editText_email),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.linearLayout2),
                        1),
                    2),
                isDisplayed()))
        appCompatEditText2.perform(replaceText("pe@v.m"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            allOf(withId(R.id.editText_serverAlias),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.linearLayout2),
                        1),
                    5),
                isDisplayed()))
        appCompatEditText3.perform(replaceText("pe"), closeSoftKeyboard())

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

        val materialCheckBox = onView(
            allOf(withId(R.id.checkBox), withText("tu-graz.algorithmen"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.linear_scroll),
                        0),
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

        val appCompatImageButton = onView(
            allOf(withId(R.id.button_add_server),
                childAtPosition(
                    allOf(withId(R.id.simple_spinner_dropdown_item),
                        childAtPosition(
                            withId(R.id.linearLayout2),
                            0)),
                    3),
                isDisplayed()))
        appCompatImageButton.perform(click())

        val appCompatEditText4 = onView(
            allOf(withId(R.id.editText_name),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.linearLayout2),
                        1),
                    1),
                isDisplayed()))
        appCompatEditText4.perform(replaceText("nis"), closeSoftKeyboard())

        val appCompatEditText5 = onView(
            allOf(withId(R.id.editText_email),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.linearLayout2),
                        1),
                    2),
                isDisplayed()))
        appCompatEditText5.perform(replaceText("nis@gmail.com"), closeSoftKeyboard())

        val appCompatEditText6 = onView(
            allOf(withId(R.id.editText_serverAlias),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.linearLayout2),
                        1),
                    5),
                isDisplayed()))
        appCompatEditText6.perform(replaceText("nis"), closeSoftKeyboard())

        val materialButton3 = onView(
            allOf(withId(R.id.button_subscribe), withText("NEXT"),
                childAtPosition(
                    allOf(withId(R.id.linearLayout5),
                        childAtPosition(
                            withId(R.id.linearLayout2),
                            2)),
                    0),
                isDisplayed()))
        materialButton3.perform(click())

        val appCompatEditText7 = onView(
            allOf(withId(R.id.editTextGroupFilter),
                childAtPosition(
                    allOf(withId(R.id.linearLayout3),
                        childAtPosition(
                            withId(R.id.linearLayout2),
                            0)),
                    2),
                isDisplayed()))
        appCompatEditText7.perform(replaceText("ci"), closeSoftKeyboard())

        val materialCheckBox2 = onView(
            allOf(withId(R.id.checkBox), withText("tu-graz.lv.ci"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.linear_scroll),
                        11),
                    0),
                isDisplayed()))
        materialCheckBox2.perform(click())

        val materialButton4 = onView(
            allOf(withId(R.id.button_finish), withText("FINISH"),
                childAtPosition(
                    allOf(withId(R.id.linearLayout4),
                        childAtPosition(
                            withId(R.id.linearLayout2),
                            2)),
                    1),
                isDisplayed()))
        materialButton4.perform(click())

        val textView = onView(
            allOf(withId(R.id.tv_subgroup_name), withText("tu-graz.lv.ci"),
                withParent(allOf(withId(R.id.recyclerView),
                    withParent(withId(R.id.linearLayout2)))),
                isDisplayed()))
        textView.check(matches(withText("tu-graz.lv.ci")))
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
