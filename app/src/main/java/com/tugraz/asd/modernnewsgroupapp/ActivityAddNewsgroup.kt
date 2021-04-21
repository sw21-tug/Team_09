package com.tugraz.asd.modernnewsgroupapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tugraz.asd.modernnewsgroupapp.helper.showCustomToast


class ActivityAddNewsgroup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_newsgroup)

        Toast(this).showCustomToast (
                "Hello! This is our custom Toast!",
                this
        )
    }
}
