package com.tugraz.asd.modernnewsgroupapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

const val splashScreenDuration: Long = 3000 // Milliseconds


class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen_new)
        Handler(Looper.getMainLooper()).postDelayed({
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }, splashScreenDuration)
    }
}
