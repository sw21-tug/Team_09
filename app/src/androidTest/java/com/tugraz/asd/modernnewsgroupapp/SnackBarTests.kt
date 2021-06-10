package com.tugraz.asd.modernnewsgroupapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SnackBarTests {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun clearDb(){
        InstrumentationRegistry.getInstrumentation().getTargetContext().deleteDatabase("newsgroup.db")
    }

    @Test
    fun checkIfSnackBarIsDisplayed()
    {
        onView(withText("NEXT")).perform(click())
        val textView = onView(
                Matchers.allOf(
                    ViewMatchers.withId(R.id.tv_snackText),
                    withText("Enter valid name and email address."),
                    ViewMatchers.withParent(ViewMatchers.withParent(ViewMatchers.withId(R.id.cvSnackBar))),
                    isDisplayed()
                )
                )
        textView.check(matches(withText("Enter valid name and email address.")))
    }

    @Test(expected = NoMatchingViewException::class)
    fun checkIfSnackBarIsNotDisplayed()
    {
        onView(withText("Newsgroup subscribed")).check(matches(isDisplayed()))
    }


    
}