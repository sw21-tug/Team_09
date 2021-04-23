package com.tugraz.asd.modernnewsgroupapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.tugraz.asd.modernnewsgroupapp.helper.MessageType
import com.tugraz.asd.modernnewsgroupapp.helper.showCustomToast
import com.tugraz.asd.modernnewsgroupapp.helper.showSnackBar

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FragmentAddNewsgroup : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_newsgroup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_subscribe).setOnClickListener {

            showSnackBar("Newsgroup subscribed", this.requireView(), MessageType.SUCCESS)
            /*Toast(this.activity).showCustomToast (
                    "Hello! This is our custom Toast!",
                    this
            )*/
            findNavController().navigate(R.id.action_AddNewsgroup_to_Subscribe)
        }
    }
}