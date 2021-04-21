package com.tugraz.asd.modernnewsgroupapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.tugraz.asd.modernnewsgroupapp.databinding.ActivityAddNewsgroupBinding
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentAddNewsgroupBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FragmentAddNewsgroup : Fragment() {

    private lateinit var binding: FragmentAddNewsgroupBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddNewsgroupBinding.inflate(layoutInflater)
        val view = binding.root
        binding.buttonSubscribe.setOnClickListener() {
            onButtonSubscribeClick()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun onButtonSubscribeClick() {
        val email = binding.editTextEmail.text
        val name = binding.editTextName
        val server = binding.editTextNewsgroupServer
        val serverAlias = binding.editTextServerAlias
       // findNavController().navigate(R.id.action_AddNewsgroup_to_Subscribe)

    }
}