package com.tugraz.asd.modernnewsgroupapp.helper

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

class Helper {

    companion object {

        @SuppressLint("SimpleDateFormat")
        fun formatDate(date: String?): String {

            var output = ""
            if (!date.isNullOrEmpty()) {
                val dateShort = date.substring(5, 25)
                val parser = SimpleDateFormat("dd MMM yyyy HH:mm:ss")
                val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
                output = formatter.format(parser.parse(dateShort))
            }

            return output
        }
    }
}