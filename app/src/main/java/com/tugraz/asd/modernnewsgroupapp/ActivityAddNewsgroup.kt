package com.tugraz.asd.modernnewsgroupapp

import android.os.Bundle
import android.view.ContextMenu
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tugraz.asd.modernnewsgroupapp.helper.showCustomToast


class ActivityAddNewsgroup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_newsgroup)

        /*Toast(this).showCustomToast (
                "Hello! This is a custom Toast!",
                this
        )*/
    }


}
