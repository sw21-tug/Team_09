package com.tugraz.asd.modernnewsgroupapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SwipeSubgroupListTest {

    @Rule
    @JvmField
    var  rule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    private fun init() {
        val inputName = onView(ViewMatchers.withId(R.id.editText_name)).check(matches(isDisplayed()))
        val inputEmail = onView(ViewMatchers.withId(R.id.editText_email)).check(matches(isDisplayed()))

        inputName.perform(ViewActions.replaceText("test"), ViewActions.closeSoftKeyboard())
        inputEmail.perform(ViewActions.replaceText("test@test.at"), ViewActions.closeSoftKeyboard())

        onView(withText("NEXT")).perform(ViewActions.click())
        onView(withText("vc-graz")).perform(ViewActions.click())
        onView(withText("FINISH")).perform(ViewActions.click())
    }

    @Test (expected = NoMatchingViewException::class)
    fun checkLeftSwipeForDeletion()
    {
        init()
        onView(withText("vc-graz")).perform(swipeLeft())
        onView(withText("vc-graz")).check(matches((isDisplayed())))
    }

    @Test
    fun checkRightSwipeForEditing()
    {
        init()
        onView(withText("vc-graz")).perform(swipeRight())
        onView(withText("Edit Alias for vc-graz")).check(matches(isDisplayed()))
    }
}