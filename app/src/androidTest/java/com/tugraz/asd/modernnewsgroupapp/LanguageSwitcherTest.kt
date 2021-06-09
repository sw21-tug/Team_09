package com.tugraz.asd.modernnewsgroupapp


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onData
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

    private fun clearDb(){
        InstrumentationRegistry.getInstrumentation().getTargetContext().deleteDatabase("newsgroup.db")
    }

    @Before
    fun before() {
        clearDb()
    }

    @Test
    fun languageSwitcherTest___() {
        clearDb()
        Thread.sleep(3000)
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
        appCompatEditText.perform(replaceText("test"), closeSoftKeyboard())

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
        appCompatEditText2.perform(replaceText("test"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.editText_email), withText("test"),
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
        appCompatEditText3.perform(click())

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.editText_email), withText("test"),
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
        appCompatEditText4.perform(replaceText("tes@tets.com"))

        val appCompatEditText5 = onView(
            allOf(
                withId(R.id.editText_email), withText("tes@tets.com"),
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
        appCompatEditText5.perform(closeSoftKeyboard())

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

        val materialCheckBox = onView(
            allOf(
                withId(R.id.checkBox), withText("vc-graz"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.view_subscribe),
                        0
                    ),
                    0
                )
            )
        )
        materialCheckBox.perform(scrollTo(), click())

        val materialButton2 = onView(
            allOf(
                withId(R.id.button_finish), withText("FINISH"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout4),
                        childAtPosition(
                            withId(R.id.linearLayout2),
                            2
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        val appCompatImageButton = onView(
            allOf(
                withId(R.id.button_edit_newsgroup),
                childAtPosition(
                    allOf(
                        withId(R.id.simple_spinner_dropdown_item),
                        childAtPosition(
                            withId(R.id.linearLayout2),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val materialButton3 = onView(
            allOf(
                withId(R.id.button_delete_newsgroup), withText("Delete newsgroup"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout2),
                        childAtPosition(
                            withId(R.id.nav_host_fragment),
                            0
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())

        val appCompatImageButton2 = onView(
            allOf(
                withId(R.id.button_show_profile),
                childAtPosition(
                    allOf(
                        withId(R.id.simple_spinner_dropdown_item),
                        childAtPosition(
                            withId(R.id.linearLayout2),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageButton2.perform(click())

        val appCompatSpinner = onView(
            allOf(
                withId(R.id.spinner_language),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout6),
                        childAtPosition(
                            withId(R.id.linearLayout2),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatSpinner.perform(click())

        val appCompatCheckedTextView0 = onView(allOf(withText("Russian")))
        appCompatCheckedTextView0.perform(click())


        Thread.sleep(5000)


        val materialButton4 = onView(
            allOf(
                withId(R.id.button_save_profile), withText("SAVE"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout2),
                        childAtPosition(
                            withId(R.id.nav_host_fragment),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton4.perform(click())

        Thread.sleep(3000)

        val appCompatEditText6 = onView(
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
        appCompatEditText6.perform(replaceText("test"), closeSoftKeyboard())

        val appCompatEditText7 = onView(
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
        appCompatEditText7.perform(replaceText("test@test.com"), closeSoftKeyboard())

        val materialButton5 = onView(
            allOf(
                withId(R.id.button_subscribe), withText(" ДАЛЕЕ следующий "),
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
        materialButton5.perform(click())

        val materialCheckBox2 = onView(
            allOf(
                withId(R.id.checkBox), withText("vc-graz"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.view_subscribe),
                        0
                    ),
                    0
                )
            )
        )
        materialCheckBox2.perform(scrollTo(), click())

        val materialButton6 = onView(
            allOf(
                withId(R.id.button_finish), withText("ФИНИШ"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout4),
                        childAtPosition(
                            withId(R.id.linearLayout2),
                            2
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton6.perform(click())

        val appCompatImageButton3 = onView(
            allOf(
                withId(R.id.button_show_profile),
                childAtPosition(
                    allOf(
                        withId(R.id.simple_spinner_dropdown_item),
                        childAtPosition(
                            withId(R.id.linearLayout2),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageButton3.perform(click())

        val textView = onView(
            allOf(
                withId(android.R.id.text1), withText("Русский"),
                withParent(
                    allOf(
                        withId(R.id.spinner_language),
                        withParent(withId(R.id.linearLayout6))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Русский")))

        val appCompatSpinner2 = onView(
            allOf(
                withId(R.id.spinner_language),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout6),
                        childAtPosition(
                            withId(R.id.linearLayout2),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatSpinner2.perform(click())

        val appCompatCheckedTextView = onView(allOf(withText("English")))
        appCompatCheckedTextView.perform(click())


        val materialButton7 = onView(
            allOf(
                withId(R.id.button_save_profile), withText("СПАСИБО"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout2),
                        childAtPosition(
                            withId(R.id.nav_host_fragment),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton7.perform(click())
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
