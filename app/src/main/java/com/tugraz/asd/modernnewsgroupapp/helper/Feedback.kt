package com.tugraz.asd.modernnewsgroupapp.helper

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.cardview.widget.CardView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import com.tugraz.asd.modernnewsgroupapp.*

/*
    USAGE: Feedback.showSuccess(this.requireView(), "Success")
 */

class Feedback
{
    companion object {

        fun showError(view: View, message: String)
        {
            val color = AppCompatResources.getColorStateList(view.context, R.color.ERROR_RED).defaultColor
            val icon = AppCompatResources.getDrawable(view.context, R.drawable.outline_error_24)
            createSnackBar(view, message, color, icon)
        }

        fun showWarning(view: View, message: String)
        {
            val color = AppCompatResources.getColorStateList(view.context, R.color.WARNING_YELLOW).defaultColor
            val icon = AppCompatResources.getDrawable(view.context, R.drawable.outline_warning_24)
            createSnackBar(view, message, color, icon)
        }

        fun showInfo(view: View, message: String)
        {
            val color = AppCompatResources.getColorStateList(view.context, R.color.INFO_BLUE).defaultColor
            val icon = AppCompatResources.getDrawable(view.context, R.drawable.outline_info_24)
            createSnackBar(view, message, color, icon)
        }

        fun showSuccess(view: View, message: String)
        {
            val color = AppCompatResources.getColorStateList(view.context, R.color.SUCCESS_GREEN).defaultColor
            val icon = AppCompatResources.getDrawable(view.context, R.drawable.outline_check_circle_24)
            createSnackBar(view, message, color, icon)
        }

        private fun createSnackBar(view: View, message: String, color: Int, icon: Drawable?)
        {
            val snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG)

            val inflater: LayoutInflater = view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val customSnackView = inflater.inflate(R.layout.feedback_msg_layout, null)

            snackbar.view.setBackgroundColor(Color.TRANSPARENT)
            val snackbarLayout = snackbar.view as SnackbarLayout
            snackbarLayout.setPadding(0, 0, 0, 0)

            // set color
            val snackView: CardView = customSnackView.findViewById(R.id.cvSnackBar)
            snackView.setCardBackgroundColor(color)

            // set icon
            val snackIcon: ImageView = customSnackView.findViewById(R.id.iv_snackIcon)
            snackIcon.setImageDrawable(icon)
            snackIcon.setColorFilter(Color.WHITE)

            // set message
            val snackText: TextView = customSnackView.findViewById(R.id.tv_snackText)
            snackText.text = message

            // set action (closing)
            val action: ImageButton = customSnackView.findViewById(R.id.ib_snackAction)
            action.setColorFilter(Color.WHITE)
            action.setOnClickListener {
                snackbar.dismiss()
            }
            snackbarLayout.addView(customSnackView, 0)
            snackbar.show()
        }
    }
}