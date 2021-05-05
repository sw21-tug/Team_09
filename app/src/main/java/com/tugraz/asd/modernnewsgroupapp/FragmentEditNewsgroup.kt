package com.tugraz.asd.modernnewsgroupapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentAddNewsgroupBinding
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentEditNewsgroupBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FragmentEditNewsgroup : Fragment() {

    private lateinit var binding: FragmentEditNewsgroupBinding
    private lateinit var viewModel: ServerObservable
    private lateinit var controller: NewsgroupController

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditNewsgroupBinding.inflate(layoutInflater)
        val view = binding.root
        binding.buttonCloseProfile.setOnClickListener() {
            findNavController().navigate(R.id.action_FragmentEditNewsgroup_to_FragmentShowSubgroups)
        }
        binding.buttonSaveNewsgroup.setOnClickListener() {
            findNavController().navigate(R.id.action_FragmentEditNewsgroup_to_FragmentShowSubgroups)
        }
        binding.buttonDeleteNewsgroup.setOnClickListener() {
            deleteServer()
            if(controller.servers.size == 0)
            {
                findNavController().navigate(R.id.action_FragmentEditNewsgroup_to_FragmentAddNewsgroup)
            }
            else
            {
                controller.currentServer = controller.servers.keys.first()
                System.out.println(controller.currentServer)
                findNavController().navigate(R.id.action_FragmentEditNewsgroup_to_FragmentShowSubgroups)
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(ServerObservable::class.java)
        } ?: throw Exception("Invalid Activity")
        controller = viewModel.data.value!!
    }

    fun deleteServer()
    {
        controller.removeCurrentServer()
    }
}