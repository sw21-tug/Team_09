package com.tugraz.asd.modernnewsgroupapp.helper

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.tugraz.asd.modernnewsgroupapp.R

enum class MessageType {
    WARNING, SUCCESS, INFO, ERROR
}
/*
    Custom toast for fragment context
 */
fun showSnackBar(message: String, view: View, messagetype: MessageType)
{
    val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    if (messagetype == MessageType.WARNING)
        snackbar.view.setBackgroundColor(Color.YELLOW)
    else if (messagetype == MessageType.INFO)
        snackbar.view.setBackgroundColor(Color.BLUE)
    else if (messagetype == MessageType.SUCCESS)
        snackbar.view.setBackgroundColor(Color.GREEN)
    else if (messagetype == MessageType.ERROR)
        snackbar.view.setBackgroundColor(Color.RED)

    snackbar.setActionTextColor(Color.WHITE)
    snackbar.setAction("X") {
        fun onClick(v: View) {
            //v.exit()
        }
    }.show()
}

fun Toast.showCustomToast(message: String, fragment: Fragment)
{
    val layout = fragment.layoutInflater.inflate (
            R.layout.feedback_msg_layout,
            fragment.activity?.findViewById(R.id.toast_container)
    )
    showToast(this, layout, message)
}

/*
    Custom toast for activity context
 */
fun Toast.showCustomToast(message: String, activity: Activity)
{
    val layout = activity.layoutInflater.inflate (
            R.layout.feedback_msg_layout,
            activity.findViewById(R.id.toast_container)
    )
    showToast(this, layout, message)
}

fun showToast(toast: Toast, layout: View, message: String)
{
    val textView = layout.findViewById<TextView>(R.id.toast_text)
    textView.text = message

    toast.apply {
        //setGravity(Gravity.BOTTOM, 0, 40)
        duration = Toast.LENGTH_LONG
        view = layout
        show()
    }
}