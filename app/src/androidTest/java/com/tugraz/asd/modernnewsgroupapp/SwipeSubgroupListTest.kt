package com.tugraz.asd.modernnewsgroupapp

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matcher
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SwipeSubgroupListTest {

    private var serverName: String? = null
    private var serverAlias: String? = null

    @Rule
    @JvmField
    var  rule: ActivityScenarioRule<ActivityAddNewsgroup> = ActivityScenarioRule<ActivityAddNewsgroup>(ActivityAddNewsgroup::class.java)

    private fun init() {
        val inputName = onView(ViewMatchers.withId(R.id.editText_name)).check(matches(isDisplayed()))
        val inputEmail = onView(ViewMatchers.withId(R.id.editText_email)).check(matches(isDisplayed()))

        val inputServer = onView(ViewMatchers.withId(R.id.editText_newsgroupServer)).check(matches(isDisplayed()))
        val inputAlias = onView(ViewMatchers.withId(R.id.editText_serverAlias)).check(matches(isDisplayed()))

        inputName.perform(ViewActions.replaceText("test"), ViewActions.closeSoftKeyboard())
        inputEmail.perform(ViewActions.replaceText("test@test.at"), ViewActions.closeSoftKeyboard())

        inputAlias.perform(ViewActions.replaceText("AliasTest"), ViewActions.closeSoftKeyboard())

        serverName = getText(inputServer)
        serverAlias = getText(inputAlias)

        onView(withText("NEXT")).perform(ViewActions.click())
        onView(withText("vc-graz")).perform(ViewActions.click())
        onView(withText("FINISH")).perform(ViewActions.click())
    }

    @Test
    fun checkLeftSwipeForDeletion()
    {
        init()
        onView(withText("vc-graz")).perform(swipeLeft())
        onView(withText("vc-graz")).check(matches(not(isDisplayed())))
    }

    @Test
    fun checkRightSwipeForEditing()
    {
        init()
        onView(withText("vc-graz")).perform(swipeRight())
        onView(withText("Edit Alias")).check(matches(isDisplayed()))
    }

    private fun getText(matcher: ViewInteraction): String {
        var text = String()
        matcher.perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isAssignableFrom(TextView::class.java)
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