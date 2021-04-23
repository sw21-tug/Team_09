package com.tugraz.asd.modernnewsgroupapp

import android.os.IBinder
import android.view.WindowManager
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Root
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


class ToastMatcher: TypeSafeMatcher<Root>() {

    override fun describeTo(description: Description?) {
        description?.appendText("is toast")
    }

    override fun matchesSafely(item: Root?): Boolean {
        val type: Int = item?.windowLayoutParams?.get()?.type!!
        if (type == WindowManager.LayoutParams.TYPE_TOAST) {
            val windowToken: IBinder = item.decorView.windowToken
            val appToken: IBinder = item.decorView.applicationWindowToken
            if (windowToken === appToken) {
                return true
            }
        }
        return false
    }
}


@RunWith(AndroidJUnit4::class)
class ToastTest {

    @Rule
    @JvmField
    var  rule: ActivityScenarioRule<ActivityAddNewsgroup> = ActivityScenarioRule<ActivityAddNewsgroup>(ActivityAddNewsgroup::class.java)

    @Test
    fun checkIfToastIsDisplayed()
    {
        onView(withText("NEXT")).perform(click())
        onView(withText("Hello! This is our custom Toast!"))
                .inRoot(ToastMatcher())
                .check(matches(isDisplayed()))
        //onView(withText("Hello! This is our custom Toast!")).check(matches(isDisplayed()))
    }

    @Test
    fun checkToastMessageText()
    {
        onView(withText("NEXT")).perform(click())
        onView(withText("Hello! This is our custom Toast!"))
                .inRoot(ToastMatcher())
                .check(matches(withText("Hello! This is our custom Toast!")))
    }
}