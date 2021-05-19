package com.tugraz.asd.modernnewsgroupapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShowSubscribedNewsgroupTest {

    @Rule
    @JvmField
    var  rule: ActivityScenarioRule<ActivityAddNewsgroup> = ActivityScenarioRule(ActivityAddNewsgroup::class.java)

    private fun init() {
        val inputName = onView(withId(R.id.editText_name)).check(matches(isDisplayed()))
        val inputEmail = onView(withId(R.id.editText_email)).check(matches(isDisplayed()))

        inputName.perform(replaceText("test"), closeSoftKeyboard())
        inputEmail.perform(replaceText("test@test.at"), closeSoftKeyboard())

        onView(withText("NEXT")).perform(click())
        onView(withText("tu-graz.algorithmen")).perform(click())
        onView(withText("FINISH")).perform(click())
    }

    @Test
    fun showSubscribedNewsgroupTest() {
        init()
        onView(withText("tu-graz.algorithmen")).check(matches(isDisplayed()))
    }
}
