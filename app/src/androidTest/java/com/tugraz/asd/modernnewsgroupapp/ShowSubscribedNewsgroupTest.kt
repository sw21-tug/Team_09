package com.tugraz.asd.modernnewsgroupapp

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShowSubscribedNewsgroupTest {

    private var serverName: String? = null
    private var serverAlias: String? = null

    @Rule
    @JvmField
    var  rule: ActivityScenarioRule<ActivityAddNewsgroup> = ActivityScenarioRule<ActivityAddNewsgroup>(ActivityAddNewsgroup::class.java)

    private fun init() {
        val inputName = onView(withId(R.id.editText_name)).check(matches(isDisplayed()))
        val inputEmail = onView(withId(R.id.editText_email)).check(matches(isDisplayed()))

        val inputServer = onView(withId(R.id.editText_newsgroupServer)).check(matches(isDisplayed()))
        val inputAlias = onView(withId(R.id.editText_serverAlias)).check(matches(isDisplayed()))

        inputName.perform(replaceText("test"), closeSoftKeyboard())
        inputEmail.perform(replaceText("test@test.at"), closeSoftKeyboard())

        inputAlias.perform(replaceText("AliasTest"), closeSoftKeyboard())

        serverName = getText(inputServer)
        serverAlias = getText(inputAlias)

        onView(withText("NEXT")).perform(click())
        onView(withText("tu-graz.algorithmen")).perform(click())
        onView(withText("FINISH")).perform(click())
    }

    @Test
    fun showSubscribedNewsgroupTest() {
        init()
        onView(withText("tu-graz.algorithmen")).check(matches(isDisplayed()))
    }

    private fun getText(matcher: ViewInteraction): String {
        var text = String()
        matcher.perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(TextView::class.java)
            }

            override fun getDescription(): String {
                return "Text of the view"
            }

            override fun perform(uiController: UiController, view: View) {
                val tv = view as TextView
                text = tv.text.toString()
            }
        })
        return text
    }
}
