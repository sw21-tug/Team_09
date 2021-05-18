package com.tugraz.asd.modernnewsgroupapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SnackBarTests {

    @Rule
    @JvmField
    var  rule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule<MainActivity>(MainActivity::class.java)

    @Before
    fun clearDb(){
        InstrumentationRegistry.getInstrumentation().getTargetContext().deleteDatabase("newsgroup.db")
    }

    @Test
    fun checkIfSnackBarIsDisplayed()
    {
        onView(withText("NEXT")).perform(click())
        onView(withText("Newsgroup subscribed")).check(matches(isDisplayed()))
    }

    @Test(expected = NoMatchingViewException::class)
    fun checkIfSnackBarIsNotDisplayed()
    {
        onView(withText("Newsgroup subscribed")).check(matches(isDisplayed()))
    }


    
}