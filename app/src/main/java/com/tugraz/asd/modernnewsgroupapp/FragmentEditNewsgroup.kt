package com.tugraz.asd.modernnewsgroupapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FragmentEditNewsgroup : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_newsgroup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageButton>(R.id.button_close_newsgroup).setOnClickListener {
            findNavController().navigate(R.id.action_FragmentEditNewsgroup_to_FragmentShowSubgroups)
        }

        view.findViewById<Button>(R.id.button_save_newsgroup).setOnClickListener {
            findNavController().navigate(R.id.action_FragmentEditNewsgroup_to_FragmentShowSubgroups)
        }
    }
}