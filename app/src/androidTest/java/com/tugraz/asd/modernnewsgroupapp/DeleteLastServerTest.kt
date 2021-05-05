package com.tugraz.asd.modernnewsgroupapp


import androidx.test.espresso.DataInteraction
import androidx.test.espresso.ViewInteraction
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent

import androidx.test.InstrumentationRegistry.getInstrumentation
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*

import com.tugraz.asd.modernnewsgroupapp.R

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anything
import org.hamcrest.Matchers.`is`

@LargeTest
@RunWith(AndroidJUnit4::class)
class DeleteLastServerTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashScreen::class.java)

    @Test
    fun splashScreenTest() {
        val appCompatEditText = onView(
allOf(withId(R.id.editText_name),
childAtPosition(
childAtPosition(
withId(R.id.linearLayout2),
1),
1),
isDisplayed()))
        appCompatEditText.perform(replaceText("Test"), closeSoftKeyboard())
        
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
        
        val appCompatEditText3 = onView(
allOf(withId(R.id.editTextGroupFilter),
childAtPosition(
allOf(withId(R.id.linearLayout3),
childAtPosition(
withId(R.id.linearLayout2),
0)),
2),
isDisplayed()))
        appCompatEditText3.perform(replaceText("ana"), closeSoftKeyboard())
        
        val checkBox = onView(
allOf(withText("tu-graz.lv.analysis"),
childAtPosition(
allOf(withId(R.id.view_subscribe),
childAtPosition(
withId(R.id.scrollView2),
0)),
28)))
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
        
        val appCompatImageButton = onView(
allOf(withId(R.id.button_edit_newsgroup),
childAtPosition(
allOf(withId(R.id.simple_spinner_dropdown_item),
childAtPosition(
withId(R.id.linearLayout2),
0)),
2),
isDisplayed()))
        appCompatImageButton.perform(click())
        
        val materialButton3 = onView(
allOf(withId(R.id.button_delete_newsgroup), withText("Delete newsgroup"),
childAtPosition(
allOf(withId(R.id.linearLayout2),
childAtPosition(
withId(R.id.nav_host_fragment),
0)),
3),
isDisplayed()))
        materialButton3.perform(click())
        
        val button = onView(
allOf(withId(R.id.button_subscribe), withText("NEXT"),
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
