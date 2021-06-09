package com.tugraz.asd.modernnewsgroupapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShowSubscribedNewsgroupTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    private fun init() {
        val inputName = onView(withId(R.id.editText_name)).check(matches(isDisplayed()))
        val inputEmail = onView(withId(R.id.editText_email)).check(matches(isDisplayed()))
        Thread.sleep(3000)

        inputName.perform(replaceText("test"), closeSoftKeyboard())
        inputEmail.perform(replaceText("test@test.at"), closeSoftKeyboard())

        onView(withText("NEXT")).perform(click())
        onView(withText("tu-graz.algorithmen")).perform(click())
        onView(withText("FINISH")).perform(click())
    }

    @Before
    fun clearDb(){
        InstrumentationRegistry.getInstrumentation().getTargetContext().deleteDatabase("newsgroup.db")
    }

    @Test
    fun showSubscribedNewsgroupTest() {
        init()
        onView(withText("tu-graz.algorithmen")).check(matches(isDisplayed()))
    }
}
