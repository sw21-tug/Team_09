package com.tugraz.asd.modernnewsgroupapp

import androidx.lifecycle.ViewModelProviders
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tugraz.asd.modernnewsgroupapp.vo.NewsgroupServer
import org.hamcrest.Matchers.containsString
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EditNewsgroupAliasTest {

    private var serverName: String? = null
    private var serverAlias: String? = null

    @Rule
    @JvmField
    var  rule: ActivityScenarioRule<ActivityAddNewsgroup> = ActivityScenarioRule<ActivityAddNewsgroup>(ActivityAddNewsgroup::class.java)

    private fun init() {
        val inputName = onView(withId(R.id.editText_name)).check(matches(ViewMatchers.isDisplayed()))
        val inputEmail = onView(withId(R.id.editText_email)).check(matches(ViewMatchers.isDisplayed()))

        val inputServer = onView(withId(R.id.editText_newsgroupServer)).check(matches(ViewMatchers.isDisplayed()))
        val inputAlias = onView(withId(R.id.editText_serverAlias)).check(matches(ViewMatchers.isDisplayed()))

        serverName = inputServer.toString()
        serverAlias = inputAlias.toString()

        inputName.perform(ViewActions.replaceText("test"), ViewActions.closeSoftKeyboard())
        inputEmail.perform(ViewActions.replaceText("test@test.at"), ViewActions.closeSoftKeyboard())

        onView(withText("NEXT")).perform(ViewActions.click())
        onView(withText("FINISH")).perform(ViewActions.click())
    }

    @Test
    fun checkIfNewsgroupAliasDisplayed()
    {
        init()
        onView(withId(R.id.button_edit_newsgroup)).perform(ViewActions.click())
        onView(withId(R.id.header_newsgroup_name)).check(matches(withText(containsString(serverName))))
    }

    @Test
    fun checkIfNewsgroupAliasNotDisplayed()
    {
        init()
        val matchString = "news.KFGraz.at"
        onView(withId(R.id.button_edit_newsgroup)).perform(ViewActions.click())
        onView(withId(R.id.header_newsgroup_name)).check(matches(not(withText(containsString(matchString)))))
    }
}