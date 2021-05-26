package com.tugraz.asd.modernnewsgroupapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentEditNewsgroupBinding
import com.tugraz.asd.modernnewsgroupapp.helper.Feedback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        binding.buttonSaveNewsgroup.setOnClickListener {
            onButtonSaveNewsgroupClick()
        }
        binding.buttonCloseProfile.setOnClickListener {
            findNavController().navigate(R.id.action_FragmentEditNewsgroup_to_FragmentShowSubgroups)
        }
        binding.buttonDeleteNewsgroup.setOnClickListener {
            deleteServer()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this).get(ServerObservable::class.java)
        } ?: throw Exception("Invalid Activity")
        controller = viewModel.controller.value!!

        binding.editTextNewsgroupAlias.setText(controller.currentServer!!.alias)
        binding.headerNewsgroupName.text = controller.currentServer!!.host
        binding.bodyNewsgroupName.text = controller.currentServer!!.host
    }

    private fun deleteServer()
    {
        lifecycleScope.launch {
            controller.removeCurrentServer()
            if (controller.servers.size > 0) {
                controller.currentServer = controller.servers.keys.first()
                controller.loadNewsgroupsFromDB()
                viewModel.controller.postValue(controller)
                withContext(Dispatchers.Main){
                    findNavController().navigate(R.id.action_FragmentEditNewsgroup_to_FragmentShowSubgroups)
                }
            } else {
                withContext(Dispatchers.Main){
                    findNavController().navigate(R.id.action_FragmentEditNewsgroup_to_FragmentAddNewsgroup)
                }
            }
        }
        Feedback.showSuccess(this.requireView(), "Newsgroup Server successfully deleted.")
    }

    private fun onButtonSaveNewsgroupClick()
    {
        val serverAlias = binding.editTextNewsgroupAlias.text
        controller.renameCurrentAlias(serverAlias.toString())
        findNavController().navigate(R.id.action_FragmentEditNewsgroup_to_FragmentShowSubgroups)
        Feedback.showSuccess(this.requireView(), getString(R.string.feedback_ng_server_alias_set))
    }
}