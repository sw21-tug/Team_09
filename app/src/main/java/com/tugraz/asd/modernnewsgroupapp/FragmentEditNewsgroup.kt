package com.tugraz.asd.modernnewsgroupapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentEditNewsgroupBinding
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentShowSubgroupsBinding
import com.tugraz.asd.modernnewsgroupapp.vo.NewsgroupServer

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FragmentEditNewsgroup : Fragment() {

    private lateinit var viewModel: ServerObservable
    private lateinit var ngs: NewsgroupController
    private lateinit var binding: FragmentEditNewsgroupBinding

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(ServerObservable::class.java)
        } ?: throw Exception("Invalid Activity")

        binding = FragmentEditNewsgroupBinding.inflate(layoutInflater)

        var newsgroupServer_ : NewsgroupServer? = null
        for ((key, value) in  viewModel.data.value!!.servers) {
            if(key.active == true)
            {
                newsgroupServer_= key
            }
        }

        binding.editTextNewsgroupAlias.setText(newsgroupServer_?.alias)
        binding.headerNewsgroupName.setText(newsgroupServer_?.host)
        binding.bodyNewsgroupName.setText(newsgroupServer_?.host)

        binding.buttonSaveNewsgroup.setOnClickListener() {
            onButtonSaveNewsgroupClick()
        }

        binding.buttonCloseProfile.setOnClickListener() {
            findNavController().navigate(R.id.action_FragmentEditNewsgroup_to_FragmentShowSubgroups)
        }

        return binding.root
    }

    fun onButtonSaveNewsgroupClick() {

        val serverAlias = binding.editTextNewsgroupAlias.text

        for ((key, value) in  viewModel.data.value!!.servers) {
            if(key.active == true)
            {
                key.alias = serverAlias.toString()
            }
        }

        findNavController().navigate(R.id.action_FragmentEditNewsgroup_to_FragmentShowSubgroups)

    }
}