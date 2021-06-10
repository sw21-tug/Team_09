package com.tugraz.asd.modernnewsgroupapp.helper

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

class Helper {

    companion object {

        @SuppressLint("SimpleDateFormat")
        fun formatDate(date: String?): String {

            var output = ""
            if (!date.isNullOrEmpty()) {
                val dateShort = date.substring(5, 30)
                val parser = SimpleDateFormat("dd MMM yyyy HH:mm:ss")
                val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
                try
                {
                    output = formatter.format(parser.parse(dateShort))
                }catch (e:Exception)
                {
                    return output
                }
            }

            return output
        }
    }
}