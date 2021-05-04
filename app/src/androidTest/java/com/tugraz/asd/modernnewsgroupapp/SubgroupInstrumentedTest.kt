package com.tugraz.asd.modernnewsgroupapp

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SubgroupInstrumentedTest {
    @Rule
    @JvmField

    var  rule: ActivityScenarioRule<ActivityAddNewsgroup> = ActivityScenarioRule<ActivityAddNewsgroup>(ActivityAddNewsgroup::class.java)

    @Test
    fun checkIfListCollapses() {
        onView(ViewMatchers.withText("control")).perform(click())
        onView(ViewMatchers.withText("control.checkgroups")).check(ViewAssertions.matches(not (ViewMatchers.isDisplayed())))

    }

    @Test
    fun checkIfListExpands() {
        onView(ViewMatchers.withText("control")).perform(click())
        onView(ViewMatchers.withText("control")).perform(click())
        onView(ViewMatchers.withText("control.checkgroups")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }
}