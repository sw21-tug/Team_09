package com.tugraz.asd.modernnewsgroupapp

import android.os.Bundle
import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class SplashTest {
    @Test
    fun splashScreenDuration() {
        assertEquals(3000, splashScreenDuration) // Checking if the duration of the splash screen is 3 seconds.
    }


}