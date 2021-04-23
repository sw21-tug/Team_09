package com.tugraz.asd.modernnewsgroupapp

import android.os.IBinder
import android.view.WindowManager
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.Root
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matchers.not
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ToastTest {

    @Rule
    @JvmField
    var  rule: ActivityScenarioRule<ActivityAddNewsgroup> = ActivityScenarioRule<ActivityAddNewsgroup>(ActivityAddNewsgroup::class.java)

    @Test
    fun checkIfToastIsDisplayed()
    {
        onView(withText("NEXT")).perform(click())
        onView(withText("Newsgroup subscribed")).check(matches(isDisplayed()))
    }

    @Test(expected = NoMatchingViewException::class)
    fun checkIfToastIsNotDisplayed()
    {
        onView(withText("Newsgroup subscribed")).check(matches(isDisplayed()))

    }
}