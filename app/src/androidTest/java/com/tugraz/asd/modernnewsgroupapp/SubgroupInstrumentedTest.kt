package com.tugraz.asd.modernnewsgroupapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SubgroupInstrumentedTest {
    @Rule
    @JvmField

    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    private fun clearDb(){
        InstrumentationRegistry.getInstrumentation().getTargetContext().deleteDatabase("newsgroup.db")
    }

    private fun init() {
        clearDb()
        Thread.sleep(3000)
        val inputName = onView(withId(R.id.editText_name)).check(matches(ViewMatchers.isDisplayed()))
        val inputEmail = onView(withId(R.id.editText_email)).check(matches(ViewMatchers.isDisplayed()))

        // fill text input
        inputName.perform(ViewActions.replaceText("Tamara"), ViewActions.closeSoftKeyboard())
        inputEmail.perform(ViewActions.replaceText("test@test.com"), ViewActions.closeSoftKeyboard())

        onView(ViewMatchers.withText("NEXT")).perform(click())
    }

    @Test
    fun checkIfListCollapses() {
        init()
        onView(ViewMatchers.withText("control")).perform(click())
        onView(ViewMatchers.withText("control.checkgroups")).check(matches(not(ViewMatchers.isDisplayed())))
    }

    @Test
    fun checkIfListExpands() {
        init()
        onView(ViewMatchers.withText("control")).perform(click())
        onView(ViewMatchers.withText("control")).perform(click())
        onView(ViewMatchers.withText("control.checkgroups")).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun checkNewsgroupFilter() {
        init()

        // filter by "vc"
        val appCompatEditText = onView(withId(R.id.editTextGroupFilter)).check(matches(ViewMatchers.isDisplayed()))
        appCompatEditText.perform(ViewActions.replaceText("vc"), ViewActions.closeSoftKeyboard())
        onView(ViewMatchers.withText("vc-graz")).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun checkNewsgroupFilter1() {
        init()

        // filter by "math"
        val appCompatEditText = onView(withId(R.id.editTextGroupFilter)).check(matches(ViewMatchers.isDisplayed()))
        appCompatEditText.perform(ViewActions.replaceText("math"), ViewActions.closeSoftKeyboard())
        onView(ViewMatchers.withText("vc-graz")).check(matches(not(ViewMatchers.isDisplayed())))
    }

    @Test
    fun checkHigherHierarchyExpands() {
        init()
        //onView(ViewMatchers.withText("tu-graz")).perform(click())
//        onView(ViewMatchers.withText("tu-graz")).perform(ViewActions.swipeUp())
        onView(ViewMatchers.withText("tu-graz.lv")).perform(scrollTo())
        onView(ViewMatchers.withText("tu-graz.lv")).perform(click())
        onView(ViewMatchers.withText("tu-graz.lv.prog0")).perform(scrollTo())
        onView(ViewMatchers.withText("tu-graz.lv.prog0")).perform(click())
        onView(ViewMatchers.withText("tu-graz.lv.prog0.dispens")).perform(scrollTo())
        onView(ViewMatchers.withText("tu-graz.lv.prog0.dispens")).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun noHigherHierarchyDisplayed() {
        init()
        onView(ViewMatchers.withText("tu-graz.lv.prog0.dispens")).check(matches (not (ViewMatchers.isDisplayed())))
    }
}