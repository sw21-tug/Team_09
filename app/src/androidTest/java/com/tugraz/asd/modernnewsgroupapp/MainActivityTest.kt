package com.tugraz.asd.modernnewsgroupapp


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun activityAddNewsgroupTest() {
        Thread.sleep(5000)
        val appCompatEditText = onView(
                allOf(
                        withId(R.id.editText_name),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linearLayout2),
                                        1
                                ),
                                1
                        ),
                        isDisplayed()
                )
        )
        appCompatEditText.perform(replaceText("Hallo"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
                allOf(
                        withId(R.id.editText_email),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linearLayout2),
                                        1
                                ),
                                2
                        ),
                        isDisplayed()
                )
        )
        appCompatEditText2.perform(replaceText("test@test.com"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
                allOf(
                        withId(R.id.editText_newsgroupServer),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linearLayout2),
                                        1
                                ),
                                4
                        ),
                        isDisplayed()
                )
        )
        appCompatEditText3.perform(replaceText("news.tugraz.at"), closeSoftKeyboard())

        val appCompatEditText4 = onView(
                allOf(
                        withId(R.id.editText_serverAlias),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linearLayout2),
                                        1
                                ),
                                5
                        ),
                        isDisplayed()
                )
        )
        appCompatEditText4.perform(replaceText("test"), closeSoftKeyboard())

        val appCompatEditText5 = onView(
                allOf(
                        withId(R.id.editText_serverAlias), withText("test"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linearLayout2),
                                        1
                                ),
                                5
                        ),
                        isDisplayed()
                )
        )
        appCompatEditText5.perform(click())

        val appCompatEditText6 = onView(
                allOf(
                        withId(R.id.editText_serverAlias), withText("test"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linearLayout2),
                                        1
                                ),
                                5
                        ),
                        isDisplayed()
                )
        )
        appCompatEditText6.perform(replaceText("test"))

        val appCompatEditText7 = onView(
                allOf(
                        withId(R.id.editText_serverAlias), withText("test"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linearLayout2),
                                        1
                                ),
                                5
                        ),
                        isDisplayed()
                )
        )
        appCompatEditText7.perform(closeSoftKeyboard())

        val materialButton = onView(
                allOf(
                        withId(R.id.button_subscribe), withText("NEXT"),
                        childAtPosition(
                                allOf(
                                        withId(R.id.linearLayout5),
                                        childAtPosition(
                                                withId(R.id.linearLayout2),
                                                2
                                        )
                                ),
                                0
                        ),
                        isDisplayed()
                )
        )
        materialButton.perform(click())

        val button2 = onView(
                allOf(
                        withId(R.id.button_finish), withText("FINISH"),
                        withParent(
                                allOf(
                                        withId(R.id.linearLayout4),
                                        withParent(withId(R.id.linearLayout2))
                                )
                        ),
                        isDisplayed()
                )
        )
        button2.check(ViewAssertions.matches(isDisplayed()))
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

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
