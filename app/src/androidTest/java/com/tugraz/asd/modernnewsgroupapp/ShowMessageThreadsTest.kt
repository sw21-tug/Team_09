package com.tugraz.asd.modernnewsgroupapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShowMessageThreadsTest {

    @Rule
    @JvmField
    var rule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun clearDb() {
        InstrumentationRegistry.getInstrumentation().targetContext.deleteDatabase("newsgroup.db")
        //InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("pm clear PACKAGE_NAME").close()
    }

    private fun init() {
        val inputName = onView(withId(R.id.editText_name)).check(matches(isDisplayed()))
        val inputEmail = onView(withId(R.id.editText_email)).check(matches(isDisplayed()))

        inputName.perform(ViewActions.replaceText("test"), ViewActions.closeSoftKeyboard())
        inputEmail.perform(ViewActions.replaceText("test@test.at"), ViewActions.closeSoftKeyboard())

        onView(withText("NEXT")).perform(ViewActions.click())
    }

    @Test
    fun checkNewsgroupWithNoThreads()
    {
        init()
        onView(withText("control.checkgroups")).perform(ViewActions.click())
        onView(withText("FINISH")).perform(ViewActions.click())
        onView(withText("control.checkgroups")).perform(ViewActions.click())
        onView(withText(R.string.feedback_no_message_threads)).check(matches((isDisplayed())))
    }

    @Test
    fun checkNewsgroupWithThreads()
    {
        init()
        onView(withText("vc-graz")).perform(ViewActions.click())
        onView(withText("FINISH")).perform(ViewActions.click())
        onView(withText("vc-graz")).perform(ViewActions.click())
        onView(withId(R.layout.layout_thread)).check(matches((isDisplayed())))
    }
}