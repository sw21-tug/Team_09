package com.tugraz.asd.modernnewsgroupapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.sql.Time


@RunWith(AndroidJUnit4::class)
class ToastTest {

    @Rule
    @JvmField
    var  rule: ActivityScenarioRule<ActivityAddNewsgroup>  = ActivityScenarioRule<ActivityAddNewsgroup>(ActivityAddNewsgroup::class.java)

    @Test
    fun showToast()
    {
        onView(withText("NEXT")).perform(click())
        onView(withText("Hello! This is our custom Toast!")).check(matches(isDisplayed()))
    }
}